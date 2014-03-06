package pl.sggw.activities.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import pl.sggw.R;
import pl.sggw.activities.editor.TaskEditorActivity;
import pl.sggw.activities.home.logic.TabType;
import pl.sggw.activities.home.logic.TasksPresenter;
import pl.sggw.activities.home.ui.TaskListAdapter;
import pl.sggw.activities.home.ui.TasksView;
import pl.sggw.receiver.CreateTaskReceiver;
import pl.sggw.receiver.RefreshTasksReceiver;
import pl.sggw.receiver.SortTasksReceiver;
import pl.sggw.task.model.Task;
import pl.sggw.widget.Refreshable;
import pl.sggw.widget.shake.ShakeDetector;
import pl.sggw.widget.swipe.HorizontalSwipeListItemDetector;
import roboguice.activity.RoboListActivity;

import javax.inject.Inject;

/**
 * @author Daniel Michalski
 * @author Daniel
 * @date 05.11.12
 */

public abstract class AbstractTaskActivity extends RoboListActivity implements TasksView, Refreshable {

	private static String TAG = AbstractTaskActivity.class.getName();

	protected TaskListAdapter adapter;

	public static final int REQUEST_TASK_EDITOR = 1;

	@Inject
	protected TasksPresenter presenter;

	@Inject
	private SortTasksReceiver sortTasksReceiver;

	@Inject
	private RefreshTasksReceiver refreshTasksReceiver;

	@Inject
	private CreateTaskReceiver createTaskReceiver;

	protected abstract TabType getTabType();

	private Vibrator vibe;

	private ShakeDetector shakeDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new TaskListAdapter(this);
		setListAdapter(adapter);

		presenter.initializeWith(this, this, getTabType());
		sortTasksReceiver.initializeWith(adapter);
		refreshTasksReceiver.initializeWith(this);
		createTaskReceiver.initializeWith(presenter);
		getListView().setSelector(R.drawable.list_blue_selector);
		vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		shakeDetector = new ShakeDetector(this);
		shakeDetector.setOnShakeListener(presenter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.refreshItems(presenter.getTasksFromDB());
		registerForContextMenu(getListView());
		registerReceiver(sortTasksReceiver, sortTasksReceiver.specifyIntentFilter());
		registerReceiver(refreshTasksReceiver, refreshTasksReceiver.specifyIntentFilter());
		registerReceiver(createTaskReceiver, createTaskReceiver.specifyIntentFilter());
		registerShakeListener();
	}

	@Override
	public void removeTaskFromList(Task task) {
		adapter.removeItem(task);
	}

	@Override
	public void vibrate(long durationInMs) {
		vibe.vibrate(durationInMs);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_TASK_EDITOR && resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			Task task = (Task) bundle.getSerializable(TaskEditorActivity.RESULT_EDITED_TASK);
			presenter.saveTaskInDB(task);
			adapter.refreshItems(presenter.getTasksFromDB());
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.items_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info =
				(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		Task clickedTask = (Task) getListAdapter().getItem((int) info.id);
		Log.d(TAG, "task z intencji w menuItem " + clickedTask);
		return presenter.contextMenuAction(item.getItemId(), clickedTask);
	}

	@Override
	public void setListeners() {
		ListView listView = getListView();
		HorizontalSwipeListItemDetector touchListener = new HorizontalSwipeListItemDetector(listView, presenter);
		listView.setOnTouchListener(touchListener);
		listView.setOnScrollListener(touchListener.makeScrollListener());
		listView.setOnItemClickListener(presenter);
	}

	@Override
	public void registerShakeListener() {
		shakeDetector.resume();
	}

	@Override
	public void unregisterShakeListener() {
		shakeDetector.pause();
	}

	@Override
	public void refresh() {
		adapter.refreshItems(presenter.getTasksFromDB());
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		return presenter.getDialogBy(id, bundle);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		return presenter.getDialogBy(id, null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterForContextMenu(getListView());
		unregisterReceiver(sortTasksReceiver);
		unregisterReceiver(refreshTasksReceiver);
		unregisterReceiver(createTaskReceiver);
		unregisterShakeListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.destroy();
	}
}
