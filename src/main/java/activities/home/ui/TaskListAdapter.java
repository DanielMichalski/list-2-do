package pl.sggw.activities.home.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.sggw.activities.home.logic.TaskSorter;
import pl.sggw.task.model.Task;
import pl.sggw.widget.RefreshableAdapter;
import pl.sggw.widget.Sortable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel
 * @date 05.11.12
 */
public class TaskListAdapter extends RefreshableAdapter<Task> implements Sortable {

	private int visibilityCalendarPage;

	private TaskSorter sorter;

	public TaskListAdapter(Context ctx) {
		super(ctx, new ArrayList<Task>());
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		visibilityCalendarPage = View.GONE;
		sorter = new TaskSorter(ctx);
	}

	public void setVisibilityCalendarPage(int visibilityCalendarPage) {
		this.visibilityCalendarPage = visibilityCalendarPage;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = new TaskListItem(context);
			holder.taskListItem = (TaskListItem) convertView;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Task task = getItem(position);
		holder.taskListItem.setupView(task, visibilityCalendarPage);
		holder.taskListItem.fill(task);
		return convertView;
	}

	public static class ViewHolder {
		public TaskListItem taskListItem;
	}

	@Override
	public void refreshItems(List<Task> newItems) {
		super.refreshItems(newItems);
		sortItems();
	}

	@Override
	public void sortItems() {
		Collections.sort(items, sorter);
		notifyDataSetChanged();
	}

	public void removeItem(Task task){
		items.remove(task);
		notifyDataSetChanged();
	}

}