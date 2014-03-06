package pl.sggw.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.sggw.database.DBHelper;
import pl.sggw.database.adapters.DatabaseAdapter;
import pl.sggw.database.exceptions.OperationFailedException;
import pl.sggw.database.exceptions.RecordNotFoundException;
import pl.sggw.database.utils.TaskContentValuesBuilder;
import pl.sggw.database.utils.TaskUtilities;
import pl.sggw.database.utils.tables_headers.TaskTableHeaders;
import pl.sggw.task.StateType;
import pl.sggw.task.model.Task;
import pl.sggw.util.time.CalendarUtil;
import pl.sggw.util.time.DateUtil;

import java.util.*;

/**
 * @author Daniel Michalski
 * @date 06.11.12
 */

@Singleton
public class TaskDao {
	public static final String DEBUG_TAG = TaskDao.class.getName();

	private final String tableName = TaskTableHeaders.TABLE_NAME;

	private DatabaseAdapter databaseAdapter;

	private SQLiteDatabase database;


	@Inject
	public TaskDao(Context context) {
		databaseAdapter = DatabaseAdapter.getInstance();
		database = databaseAdapter.open(context);
	}


	public TaskDao(DBHelper dbHelper) {
		databaseAdapter = DatabaseAdapter.getInstance();
		database = databaseAdapter.open(dbHelper);
	}


	public Task save(Task task) {
		task.setUpdatedDate(new Date());
		if (task.isPersist()) {
			return update(task);
		} else {
			return insert(task);
		}
	}


	private Task insert(Task task) {
		ContentValues newTaskValues = TaskContentValuesBuilder.createContentValuesByTask(task);
		long res = database.insert(tableName, null, newTaskValues);
		validDBOperation(res);
		task.setId(res);
		return task;
	}


	private Task update(Task task) {
		String where = TaskTableHeaders.ID + "=" + task.getId();
		ContentValues updateTaskValues = TaskContentValuesBuilder.createContentValuesByTask(task);
		long res = database.update(tableName, updateTaskValues, where, null);
		validDBOperation(res);
		return task;
	}


	public int deleteTask(Task task) {
		String where = TaskTableHeaders.ID + "=" + task.getId();
		int res = database.delete(tableName, where, null);
		validDBOperation(res);
		return res;
	}


	public int deleteTasks(List<Task> tasks) {
		int ret = 0;
		for (Task task : tasks) {
			int res = deleteTask(task);
			ret += res;
		}
		return ret;
	}


	public int deleteAllTasks() {
		int res = database.delete(tableName, null, null);
		if (res == -1) {
			throw new OperationFailedException("");
		}
		return res;
	}


	public List<Task> getAllTasks() {
		Cursor cursor = database.query(tableName, null, null, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}


	public List<Task> getRemovedTasks() {
		String where = TaskTableHeaders.STATUS_TYPE + " = '" + StateType.REMOVED + "'";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}


	public List<Task> getNotSynchronizedTasks() {
		String where = TaskTableHeaders.GOOGLE_ID + " IS NULL";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = new ArrayList<Task>();
		while (cursor.moveToNext()) {
			Task task = TaskUtilities.getOneTaskFromCursorObiect(cursor);
			tasks.add(task);
		}
		cursor.close();
		return tasks;
	}

	public List<Task> getSynchronizedTasks() {
		String where = TaskTableHeaders.GOOGLE_ID + " IS NOT NULL";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}


	public List<Task> getDoneTasksForToday() {
		GregorianCalendar cal = CalendarUtil.getCalendar(DateUtil.today());
		long minTime = cal.getTime().getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		long maxTime = cal.getTime().getTime();
		String where = TaskTableHeaders.STATUS_TYPE + " = '" + StateType.DONE + "' AND " +
				TaskTableHeaders.DUE_DATE + " >= " + minTime
						+ " AND " +
						TaskTableHeaders.DUE_DATE + " < " + maxTime
						+ " AND NOT " + TaskTableHeaders.STATUS_TYPE + " = '" + StateType.REMOVED + "'";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}

	public List<Task> getDoneTasksForTomorrow() {
		GregorianCalendar cal = CalendarUtil.getCalendar(DateUtil.today());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		long minTime = cal.getTime().getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		long maxTime = cal.getTime().getTime();
		String where = TaskTableHeaders.STATUS_TYPE + " = '" + StateType.DONE + "' AND " +
				TaskTableHeaders.DUE_DATE + " >= " + minTime
						+ " AND " +
						TaskTableHeaders.DUE_DATE + " < " + maxTime
						+ " AND NOT " + TaskTableHeaders.STATUS_TYPE + " = '" + StateType.REMOVED + "'";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}

	public List<Task> getDoneTasksForLater() {
		GregorianCalendar cal = CalendarUtil.getCalendar(DateUtil.today());
		cal.add(Calendar.DAY_OF_MONTH, 2);
		long minTime = cal.getTime().getTime();
		String where = TaskTableHeaders.STATUS_TYPE + " = '" + StateType.DONE + "' AND " +
				TaskTableHeaders.DUE_DATE + " >= " + minTime
						+ " AND NOT " + TaskTableHeaders.STATUS_TYPE + " = '" + StateType.REMOVED + "'";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}


	public List<Task> getTasksForToday() {
		GregorianCalendar cal = CalendarUtil.getCalendar(DateUtil.today());
		long minTime = cal.getTime().getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		long maxTime = cal.getTime().getTime();
		String where =
				TaskTableHeaders.DUE_DATE + " >= " + minTime
						+ " AND " +
						TaskTableHeaders.DUE_DATE + " < " + maxTime
						+ " AND NOT " + TaskTableHeaders.STATUS_TYPE + " = '" + StateType.REMOVED + "'";

		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}


	public List<Task> getTestTasksForToday() {
		List<Task> tasks = new ArrayList<Task>();
		Date date = new Date();
		tasks.add(new Task("Shake do usuwania tasków", date));
		tasks.add(new Task("Włączenie serwisu", date));
		tasks.add(new Task("Walidacja Tasku (podana nazwa już istnieje)", date));
		tasks.add(new Task("Próba podłączenia Google Account", date));
		tasks.add(new Task("Zmiana wyglądu WheelView (na bardziej prosty odpowiadający naszej App)", date));
		tasks.add(new Task("Odsortowanie Tasków DONE na sam dół listy", date));
		tasks.add(new Task("Dodać Acre ", date));
		tasks.add(new Task("Brak logiki dla powtarzalności / jak nie starczy czasu usunąć opcje", date));
		tasks.add(new Task("Dodać godzine w liscie po lewej / na dole", date));
		tasks.add(new Task("Dodać akcję wibracji przy usuwaniu taskow potrząśnięciem", date));
		tasks.add(new Task("Przebudowac layout editTaskActivity na ciekawszy (z nowszego API)", date));
		tasks.add(new Task("Pousuwać niepotrzebne dane z layotów i poprzerzucać wspólne cechy do styli", date));
		return tasks;
	}


	public List<Task> getTasksForTomorrow() {
		GregorianCalendar cal = CalendarUtil.getCalendar(DateUtil.today());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		long minTime = cal.getTime().getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		long maxTime = cal.getTime().getTime();
		String where =
				TaskTableHeaders.DUE_DATE + " >= " + minTime
						+ " AND " +
						TaskTableHeaders.DUE_DATE + " < " + maxTime
						+ " AND NOT " + TaskTableHeaders.STATUS_TYPE + " = '" + StateType.REMOVED + "'";

		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}


	public List<Task> getTasksForLater() {
		GregorianCalendar cal = CalendarUtil.getCalendar(DateUtil.today());
		cal.add(Calendar.DAY_OF_MONTH, 2);
		long minTime = cal.getTime().getTime();
		String where =
				TaskTableHeaders.DUE_DATE + " >= " + minTime
						+ " AND NOT " + TaskTableHeaders.STATUS_TYPE + " = '" + StateType.REMOVED + "'";

		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		List<Task> tasks = TaskUtilities.getAllTasksFromCursorObiect(cursor);
		cursor.close();
		return tasks;
	}


	public Task getTaskForAlarm() {
		Date currentDate = new Date();
		String where =
				TaskTableHeaders.ALARM_DATE + " > " + currentDate.getTime()
						+ " AND " + TaskTableHeaders.STATUS_TYPE + " = '" + StateType.IN_QUEUE + "'";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		Task task = null;
		if (cursor != null && cursor.moveToFirst()) {
			task = TaskUtilities.getOneTaskFromCursorObiect(cursor);
		}
		cursor.close();
		return task;
	}


	public Task getTaskById(long id) {
		String where = TaskTableHeaders.ID + "=" + id;
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		Task task = null;
		if (cursor != null && cursor.moveToFirst()) {
			task = TaskUtilities.getOneTaskFromCursorObiect(cursor);
		}
		cursor.close();
		return task;
	}


	public boolean titleIsExist(String compareTitle) {
		int countRows = 0;
		String where = TaskTableHeaders.TITLE + " = '" + compareTitle + "'";
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);
		countRows = cursor.getCount();
		cursor.close();
		return countRows >= 1;
	}


	private void validDBOperation(long res) {
		if (res == -1) {
			throw new OperationFailedException();
		}
		if (res == 0) {
			throw new RecordNotFoundException();
		}
	}


	public void destroy() {
		databaseAdapter.close();
	}

}