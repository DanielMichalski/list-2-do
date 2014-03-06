package pl.sggw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.inject.Singleton;
import pl.sggw.widget.Sortable;

/**
 * @author Daniel
 * @date 03.11.12
 */

@Singleton
public class SortTasksReceiver extends BroadcastReceiver {

	public static final String SORT_TASKS_BROADCAST = "pl.sggw.receiver.SortTasksReceiver.RESORT_TASKS_BROADCAST";

	private Sortable sortable;

	public SortTasksReceiver() {
	}

	public void initializeWith(Sortable sortable) {
		this.sortable = sortable;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		sortable.sortItems();
	}

	public IntentFilter specifyIntentFilter() {
		return new IntentFilter(SORT_TASKS_BROADCAST);
	}
}
