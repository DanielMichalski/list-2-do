package pl.sggw.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import pl.sggw.database.dao.TaskDao;
import pl.sggw.task.model.Task;

import java.util.Date;

/**
 * @author Daniel
 * @date 27.10.12
 */

public class NotifyService extends Service {
	private static final String DEBUG_TAG = NotifyService.class.getSimpleName();

	public static final String TASK_SERVICE = "pl.sggw.service.NotifyService.SERVICE";

	private static boolean serviceAlive = false;

	private TaskDao taskDao;

	private AlarmManager alarmManager;

	private PendingIntent pendingIntent;

	private Thread scheduleNotifyThread;

	@Override
	public void onCreate() {
		super.onCreate();
		taskDao = new TaskDao(getApplicationContext());
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(getBaseContext().ALARM_SERVICE);
		scheduleNotifyThread = new Thread(new ServiceWorker(), "BackgroundNotificationService");
		serviceAlive = true;
		Log.d(DEBUG_TAG, "OnCreate usluga notyfikacja uruchomiona");
		showToast("Usługa została utworzona");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		scheduleNotifyThread.run();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		//TODO sprawdzic czy interrupt() na pewno przerywa wątek scheduleNotifyThred
		scheduleNotifyThread.interrupt();
		alarmManager.cancel(pendingIntent);
		serviceAlive = false;
		Log.d(DEBUG_TAG, "onDestroy usluga notyfikacja zatrzymana");
		showToast("Usługa została zatrzymana");

		super.onDestroy();
	}

	private void showToast(String msg) {
		Toast myToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
		myToast.show();
	}

	public static boolean isServiceAlive() {
		return serviceAlive;
	}

	private class ServiceWorker implements Runnable {
		@Override
		public void run() {
			Task task = taskDao.getTaskForAlarm();

			if (task != null) {
				Log.d(DEBUG_TAG, "Task z alarmem do notyfikacji: " + task.getAlarmDate());
				createScheduledNotification(task.getAlarmDate(), task);
			} else {
				Log.d(DEBUG_TAG, "brak Tasku za 5 godzinny ponowne uruchomienie serwisu");
				createScheduledService(1000 * 60 * 60 * 5);
			}
		}

		private void createScheduledNotification(Date date, Task notifyTask) {
			Intent toLaunch = new Intent(getApplicationContext(), NotifyReceiver.class);
			toLaunch.putExtra(NotifyReceiver.EXTRA_TASK_FOR_NOTIFY, notifyTask);
			pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, toLaunch, 0);
			alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
		}

		private void createScheduledService(long timeInMs) {
			Intent toLaunch = new Intent(getApplicationContext(), ScheduleServiceReceiver.class);
			pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, toLaunch, 0);
			alarmManager.set(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + timeInMs), pendingIntent);
		}

	}

	//Bindera nie wykorzystujemy z racji na to ze inne aplikacje nie beda korzystac z naszego serwisu
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}