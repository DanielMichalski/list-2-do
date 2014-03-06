package pl.sggw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.inject.Singleton;
import pl.sggw.widget.Refreshable;

/**
 * @author Daniel
 * @date 03.11.12
 */

@Singleton
public class RefreshTasksReceiver extends BroadcastReceiver {

	public static final String REFRESH_TASKS_BROADCAST = "pl.sggw.receiver.RefreshTasksReceiver.REFRESH_TASKS_BROADCAST";

	private Refreshable refreshable;

	public RefreshTasksReceiver() {
	}

	public void initializeWith(Refreshable refreshable) {
		this.refreshable = refreshable;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		refreshable.refresh();
	}

	public IntentFilter specifyIntentFilter() {
		return new IntentFilter(REFRESH_TASKS_BROADCAST);
	}
}
