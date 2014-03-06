package pl.sggw.database.utils;

import android.database.Cursor;
import android.util.Log;
import pl.sggw.database.utils.tables_headers.TaskTableHeaders;
import pl.sggw.task.PriorityType;
import pl.sggw.task.RepeatType;
import pl.sggw.task.StateType;
import pl.sggw.task.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel Michalski
 * @date 06.11.12
 */

public class TaskUtilities {
	public static final String DEBUG_TAG = TaskUtilities.class.getSimpleName();

	public static List<Task> getAllTasksFromCursorObiect(Cursor c) {
		Log.i(DEBUG_TAG, "*** Początek kursora *** " + " Wyników:" + c.getCount() + " Kolumn: " + c.getColumnCount());
		List<Task> taskArray = new ArrayList<Task>();
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			Task task = createTaskBy(c);
			taskArray.add(task);
			c.moveToNext();
		}
		Log.i(DEBUG_TAG, "*** Koniec kursora ***");

		return taskArray;
	}


	public static Task getOneTaskFromCursorObiect(Cursor c) {
		Log.i(DEBUG_TAG, "*** Początek kursora *** " + " Wyników:" + c.getCount() + " Kolumn: " + c.getColumnCount());
		Task task = createTaskBy(c);
		Log.i(DEBUG_TAG, "*** Koniec kursora ***");

		return task;
	}

	private static Task createTaskBy(Cursor c) {
		Long taskId = c.getLong(
				c.getColumnIndex(TaskTableHeaders.ID)
		);

		String taskGoogleId = c.getString(
				c.getColumnIndex(TaskTableHeaders.GOOGLE_ID)
		);

		String taskTitle = c.getString(
				c.getColumnIndex(TaskTableHeaders.TITLE)
		);

		String taskNote = c.getString(
				c.getColumnIndex(TaskTableHeaders.NOTES)
		);

		Long dueDateInMS = c.getLong(
				c.getColumnIndex(TaskTableHeaders.DUE_DATE)
		);

		Date taskDueDate = new Date(dueDateInMS);

		Long alarmDateInMS = c.getLong(
				c.getColumnIndex(TaskTableHeaders.ALARM_DATE)
		);
		Date taskAlarmDate = new Date(alarmDateInMS);

		Long updatedDateInMS = c.getLong(
				c.getColumnIndex(TaskTableHeaders.UPDATED_DATE)
		);
		Date taskUpdatedDate = new Date(updatedDateInMS);

		String priorityType = c.getString(
				c.getColumnIndex(TaskTableHeaders.PRIORITY_TYPE)
		);
		PriorityType taskPriorityType = PriorityType.valueOf(priorityType);

		String repeatType = c.getString(
				c.getColumnIndex(TaskTableHeaders.REPEAT_TYPE)
		);
		RepeatType taskRepeatType = RepeatType.valueOf(repeatType);

		String statusType = c.getString(
				c.getColumnIndex(TaskTableHeaders.STATUS_TYPE)
		);
		StateType taskStatusType = StateType.valueOf(statusType);

		Task task = new Task(
				taskId,
				taskTitle,
				taskDueDate
		);
		task.setGoogleId(taskGoogleId);
		task.setNotes(taskNote);
		task.setAlarmDate(taskAlarmDate);
		task.setUpdatedDate(taskUpdatedDate);
		task.setPriority(taskPriorityType);
		task.setRepeat(taskRepeatType);
		task.setStatus(taskStatusType);

		return task;
	}
}