package pl.sggw.database.utils;

import android.content.ContentValues;
import pl.sggw.database.utils.tables_headers.TaskTableHeaders;
import pl.sggw.task.model.Task;

/**
 * @author Daniel Michalski
 * @date 06.11.12
 */

public class TaskContentValuesBuilder {

	public static ContentValues createContentValuesByTask(Task task) {
		ContentValues newTaskValues = new ContentValues();

		newTaskValues.put(TaskTableHeaders.TITLE, task.getTitle());
		newTaskValues.put(TaskTableHeaders.DUE_DATE, task.getDueDate().getTime());
		newTaskValues.put(TaskTableHeaders.UPDATED_DATE, task.getUpdatedTimeInMs());
		newTaskValues.put(TaskTableHeaders.PRIORITY_TYPE, task.getPriority().toString());
		newTaskValues.put(TaskTableHeaders.REPEAT_TYPE, task.getRepeat().toString());
		newTaskValues.put(TaskTableHeaders.STATUS_TYPE, task.getStatus().toString());

		newTaskValues.put(TaskTableHeaders.GOOGLE_ID, task.getGoogleId());
		newTaskValues.put(TaskTableHeaders.NOTES, task.getNotes());
		newTaskValues.put(TaskTableHeaders.ALARM_DATE, task.getReminderTimeInMs());

		return newTaskValues;
	}

}