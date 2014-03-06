package pl.sggw.activities.home.ui.actionbar;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.View;
import pl.sggw.R;
import pl.sggw.activities.home.LaterTasksActivity;
import pl.sggw.activities.home.TomorrowTasksActivity;
import pl.sggw.receiver.CreateTaskReceiver;
import pl.sggw.task.model.Task;
import pl.sggw.util.time.DateUtil;
import pl.sggw.widget.actionbar.ActionBar;

/**
 * @author Daniel
 * @since 0.0.1
 */
public class AddTaskAction implements ActionBar.Action {
	private static String TAG = AddTaskAction.class.getName();

	private TabActivity tabActivity;

	public AddTaskAction(TabActivity tabActivity) {
		this.tabActivity = tabActivity;
	}

	@Override
	public void performAction(View view) {
		Activity currentTabActivity = tabActivity.getLocalActivityManager().getCurrentActivity();

		Intent intent = new Intent(CreateTaskReceiver.CREATE_TASK_BROADCAST);
		intent.putExtra(CreateTaskReceiver.EXTRA_NULL_OBJECT_TASK, getNullObjectBy(currentTabActivity));
		tabActivity.sendBroadcast(intent);
	}

	private Task getNullObjectBy(Activity activity) {
		Task nullObj = Task.nullObject(DateUtil.todayWithHour());
		if (activity instanceof TomorrowTasksActivity) {
			nullObj = Task.nullObject(DateUtil.tomorrowWithHour());
		} else if (activity instanceof LaterTasksActivity) {
			nullObj = Task.nullObject(DateUtil.thirdDayWithHour());
		}
		return nullObj;
	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_tab_add_selected;
	}

	@Override
	public int getId() {
		return R.id.task_add_action_item;
	}

	@Override
	public String getTitle() {
		return null;
	}

}
