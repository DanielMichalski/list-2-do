package pl.sggw.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import pl.sggw.R;
import pl.sggw.activities.home.HomeActivity;
import pl.sggw.task.model.Task;

/**
 * @author Daniel
 * @date 27.10.12
 */

public class NotifyReceiver extends BroadcastReceiver {
	private static final String DEBUG_TAG = NotifyReceiver.class.getSimpleName();

	public static final String EXTRA_TASK_FOR_NOTIFY = "extraTaskForNotify";

	private static final int NOTIFY_ID = 1;

	private static final long[] VIBRATE_PATTERN = {0, 200, 100, 300};

	@Override
	public void onReceive(Context context, Intent intent) {
		//TODO nie wiem dlaczego ale trzyma w pamieci pierwszego bundla
		Bundle bundle = intent.getExtras();
		Task notifyTask = (Task) bundle.get(EXTRA_TASK_FOR_NOTIFY);

		Log.d(DEBUG_TAG, "onReceive - przyslany Task dla notyfikacji " + notifyTask);
		if (notifyTask != null) {
			Notification notification = createNotificationWith(context, "List2Do", "Przypomnienie o zadaniach");
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(NOTIFY_ID, notification);
		}

		context.startService(new Intent(NotifyService.TASK_SERVICE));
	}

	private Notification createNotificationWith(Context context, String notifyTitle, String notifyMsg) {
		int iconResId = android.R.drawable.stat_notify_more;
		String tickerText = "List 2 Do - przypomnienie";

		Notification notify = new Notification(iconResId, tickerText, System.currentTimeMillis());
		notify.flags |= Notification.FLAG_AUTO_CANCEL;
		notify.defaults = Notification.DEFAULT_VIBRATE;
		notify.vibrate = VIBRATE_PATTERN;
		notify.icon = R.drawable.icon;

		Intent toLaunch = new Intent(context, HomeActivity.class);
		PendingIntent intentBack = PendingIntent.getActivity(context, 0, toLaunch, PendingIntent.FLAG_CANCEL_CURRENT);
		notify.setLatestEventInfo(context, notifyTitle, notifyMsg, intentBack);

		return notify;
	}

}