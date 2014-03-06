package pl.sggw.activities.home.ui.submenu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import pl.sggw.R;
import pl.sggw.activities.home.logic.TaskSortType;
import pl.sggw.preferences.PreferencesTaskSortRepository;
import pl.sggw.receiver.SortTasksReceiver;
import pl.sggw.widget.actionbar.ActionBar;

/**
 * @author Daniel
 * @since 0.0.1
 */
public class SortByPriorityAction implements ActionBar.Action {
	private static String TAG = SortByPriorityAction.class.getName();

	private Context ctx;

	public SortByPriorityAction(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void performAction(View view) {
		Log.i(TAG, "kliknieto opcje w submenu: SortByPriority");
		PreferencesTaskSortRepository.saveSortType(ctx, TaskSortType.BY_PRIORITY);
		ctx.sendBroadcast(new Intent(SortTasksReceiver.SORT_TASKS_BROADCAST));
	}

	@Override
	public String getTitle() {
		return ctx.getString(R.string.lbl_sort_by_priority);
	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_tab_sort_selected;
	}

	@Override
	public int getId() {
		return R.id.task_sort_by_priority_submenu;
	}
}
