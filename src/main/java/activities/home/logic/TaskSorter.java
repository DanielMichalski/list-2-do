package pl.sggw.activities.home.logic;

import android.content.Context;
import pl.sggw.preferences.PreferencesTaskSortRepository;
import pl.sggw.task.StateType;
import pl.sggw.task.model.Task;

import java.util.Comparator;

/**
 * @author Daniel
 * @date 03.11.12
 */
public class TaskSorter implements Comparator<Task> {

	private Context context;

	public TaskSorter(Context context) {
		this.context = context;
	}

	@Override
	public int compare(Task task, Task compareTask) {
		if (task.getStatus().equals(StateType.DONE)) {
			return 0;
		} else if (compareTask.getStatus().equals(StateType.DONE)) {
			return -1;
		}

		TaskSortType sortType = PreferencesTaskSortRepository.getSortType(context);
		int compareValue = 0;
		switch (sortType) {
			case BY_DATE:
				compareValue = task.getDueDate().compareTo(compareTask.getDueDate());
				break;
			case BY_NAME:
				compareValue = task.getTitle().toLowerCase().compareTo(compareTask.getTitle().toLowerCase());
				break;
			case BY_PRIORITY:
				compareValue = compareTask.getPriority().getValue().compareTo(task.getPriority().getValue());
				break;
		}
		return compareValue;
	}
}
