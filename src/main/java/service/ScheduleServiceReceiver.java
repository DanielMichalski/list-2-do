package pl.sggw.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * @author Daniel
 * @date 27.10.12
 */

public class ScheduleServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(NotifyService.TASK_SERVICE));
	}

}