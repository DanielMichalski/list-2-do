package pl.sggw.activities.home.logic;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.google.inject.Singleton;
import pl.sggw.R;
import pl.sggw.activities.editor.TaskEditorActivity;
import pl.sggw.activities.home.AbstractTaskActivity;
import pl.sggw.activities.home.ui.TaskListItem;
import pl.sggw.activities.home.ui.TasksView;
import pl.sggw.activities.home.ui.dialog.RemoveDoneTasksDialog;
import pl.sggw.activities.home.ui.dialog.ReviewTaskDialog;
import pl.sggw.database.dao.TaskDao;
import pl.sggw.task.StateType;
import pl.sggw.task.model.Task;
import pl.sggw.widget.shake.OnShakeListener;
import pl.sggw.widget.shake.ShakeDetector;
import pl.sggw.widget.swipe.SwipeDirection;
import pl.sggw.widget.swipe.SwipeListItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @date 10.11.12
 */

@Singleton
public class TasksPresenter implements
		TaskUpdater, AdapterView.OnItemClickListener, SwipeListItemListener<Task>, OnShakeListener {
	private static String TAG = TasksPresenter.class.getSimpleName();

	public static final int SHOW_TASK_DIALOG_ID = 1;

	public static final int REMOVE_DONE_TASKS_DIALOG_ID = 2;

	public static String EXTRA_SHOW_TASK = "extraTaskForDialog";


	private Context context;

	private TasksView view;

	private TabType tabType;

	private TaskDao taskDao;


	public void initializeWith(Context context, TasksView view, TabType tabType) {
		this.context = context;
		this.view = view;
		this.tabType = tabType;
		this.taskDao = new TaskDao(context);

		view.setListeners();
	}

	public List<Task> getTasksFromDB() {
		List<Task> tasks = new ArrayList<Task>();
		switch (tabType) {
			case WITH_TODAY_TASKS:
				tasks = taskDao.getTasksForToday();
				break;
			case WITH_TOMORROW_TASKS:
				tasks = taskDao.getTasksForTomorrow();
				break;
			case WITH_LATER_TASKS:
				tasks = taskDao.getTasksForLater();
				break;
		}
		return tasks;
	}

	public List<Task> getDoneTasksFromDB() {
		List<Task> tasks = new ArrayList<Task>();
		switch (tabType) {
			case WITH_TODAY_TASKS:
				tasks = taskDao.getDoneTasksForToday();
				break;
			case WITH_TOMORROW_TASKS:
				tasks = taskDao.getDoneTasksForTomorrow();
				break;
			case WITH_LATER_TASKS:
				tasks = taskDao.getDoneTasksForLater();
				break;
		}
		return tasks;
	}

	@Override
	public void saveTaskInDB(Task task) {
		taskDao.save(task);
	}

	@Override
	public void removeTaskFromDB(Task task) {
		task.setStatus(StateType.REMOVED);
		taskDao.save(task);
	}

	@Override
	public void removeDoneTasksFromDB() {
		for (Task task : getDoneTasksFromDB()) {
			removeTaskFromDB(task);
			view.removeTaskFromList(task);
		}
	}

	@Override
	public void editTask(Task taskForEdit) {
		Intent intentEdit = new Intent(context, TaskEditorActivity.class);
		intentEdit.putExtra(TaskEditorActivity.EXTRA_TASK_FOR_EDIT, taskForEdit);
		view.startActivityForResult(intentEdit, AbstractTaskActivity.REQUEST_TASK_EDITOR);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Task task = (Task) adapterView.getItemAtPosition(position);
		if (task.getStatus().equals(StateType.IN_QUEUE)) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(EXTRA_SHOW_TASK, task);
			this.view.showDialog(SHOW_TASK_DIALOG_ID, bundle);
		}
	}

	@Override
	public void onSwipe(SwipeDirection direction, View view, Task task) {
		TaskListItem listItem = (TaskListItem) view;
		switch (direction) {
			case LEFT:
				if (listItem.canRunAnimation() && !task.getStatus().equals(StateType.IN_QUEUE)) {
					task.setStatus(StateType.IN_QUEUE);
					saveTaskInDB(task);
					listItem.startAnimationByState(task.getStatus());
				}
				break;
			case RIGHT:
				if (listItem.canRunAnimation() && !task.getStatus().equals(StateType.DONE)) {
					task.setStatus(StateType.DONE);
					saveTaskInDB(task);
					listItem.startAnimationByState(task.getStatus());
				}
				break;
		}
	}

	@Override
	public void onShake(ShakeDetector detector) {
		view.vibrate(100);
		view.showDialog(REMOVE_DONE_TASKS_DIALOG_ID);
		detector.pause();
	}

	public boolean contextMenuAction(int itemId, Task task) {
		switch (itemId) {
			case R.id.pokaz:
				Bundle bundle = new Bundle();
				bundle.putSerializable(EXTRA_SHOW_TASK, task);
				view.showDialog(SHOW_TASK_DIALOG_ID, bundle);
				return true;
			case R.id.edytuj:
				editTask(task);
				return true;
			case R.id.usun:
				removeTaskFromDB(task);
				view.removeTaskFromList(task);
				view.vibrate(300);
				return true;
			default:
				return false;
		}
	}

	public Dialog getDialogBy(int id, Bundle bundle) {
		Dialog dialog = null;
		switch (id) {
			case SHOW_TASK_DIALOG_ID:
				Task task = (Task) bundle.get(EXTRA_SHOW_TASK);
				dialog = new ReviewTaskDialog(context, task).create();
				break;
			case REMOVE_DONE_TASKS_DIALOG_ID:
				dialog = new RemoveDoneTasksDialog(context, this, tabType).create();
				dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialogInterface) {
						view.registerShakeListener();
					}
				});
				break;
			default:
				Log.e(TAG, "Brak specyfikacji dialogu o ID: " + id);
				break;
		}
		return dialog;
	}

	public void destroy() {
		taskDao.destroy();
	}
}