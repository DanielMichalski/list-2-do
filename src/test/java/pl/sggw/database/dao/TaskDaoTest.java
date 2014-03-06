package pl.sggw.database.dao;

import android.app.Activity;
import android.content.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sggw.database.SQLiteTestRunner;
import pl.sggw.database.DBHelper;
import pl.sggw.task.PriorityType;
import pl.sggw.task.RepeatType;
import pl.sggw.task.StateType;
import pl.sggw.task.model.Task;
import pl.sggw.util.time.DateUtil;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SQLiteTestRunner.class)
public class TaskDaoTest {

	private TaskDao dao;

	@Before
	public void setup() {
		Context ctx = new Activity();
		DBHelper dbh = new DBHelper(ctx, "testdb", 1);
		dao = new TaskDao(dbh);
	}

	@Test
	public void shouldInsertTask() {
		//given
		Task insertTask = new Task("Nowe zadanie", new Date());

		//when
		Task insertedTask = dao.save(insertTask);

		//then
		assertTrue(insertedTask != null);
		assertTrue(insertedTask.getId() > 0);
	}

	@Test
	public void shouldRemoveTask() {
		//given
		Task newTask = new Task("Nowe zadanie", new Date());
		Task taskForRemove = dao.save(newTask);

		//when
		int result = dao.deleteTask(taskForRemove);

		//then
		assertTrue(result == 1);
	}

	@Test
	public void shouldUpdateTask() {
		//given
		Task newTask = new Task("Nowe zadanie", new Date());
		Task taskForUpdate = dao.save(newTask);

		//when
		taskForUpdate.setGoogleId("GoogleID");
		taskForUpdate.setTitle("Zmiana tytulu");
		taskForUpdate.setNotes("Notatka");
		taskForUpdate.setAlarmDate(taskForUpdate.getDueDate());
		taskForUpdate.setRepeat(RepeatType.ONCE_A_WEEK);
		taskForUpdate.setPriority(PriorityType.LOW);
		taskForUpdate.setStatus(StateType.REMOVED);
		Task updatedTask = dao.save(taskForUpdate);

		//then
		assertTrue(updatedTask != null);
		assertTrue(updatedTask.getId() == taskForUpdate.getId());
		assertTrue(updatedTask.getGoogleId().equals("GoogleID"));
		assertTrue(updatedTask.getTitle().equals("Zmiana tytulu"));
		assertTrue(updatedTask.getNotes().equals("Notatka"));
		assertTrue(updatedTask.getAlarmDate().equals(taskForUpdate.getDueDate()));
		assertTrue(updatedTask.getRepeat().equals(RepeatType.ONCE_A_WEEK));
		assertTrue(updatedTask.getPriority().equals(PriorityType.LOW));
		assertTrue(updatedTask.getStatus().equals(StateType.REMOVED));
	}

	@Test
	public void shouldGetAllTasks() {
		//given
		insertFiveDifferentTasks();

		//when
		List<Task> tasks = dao.getAllTasks();

		//then
		assertTrue(tasks.size() == 5);
	}

	@Test
	public void shouldGetTasksForToday() {
		//given
		insertFiveDifferentTasks();

		//when
		List<Task> tasks = dao.getTasksForToday();

		//then
		assertTrue(tasks.size() == 2);
	}

	@Test
	public void shouldGetTasksForTomorrow() {
		//given
		insertFiveDifferentTasks();

		//when
		List<Task> tasks = dao.getTasksForTomorrow();

		//then
		assertTrue(tasks.size() == 1);
	}

	@Test
	public void shouldGetTasksLaterTomorrow() {
		//given
		insertFiveDifferentTasks();

		//when
		List<Task> tasks = dao.getTasksForLater();

		//then
		assertTrue(tasks.size() == 1);
	}

	@Test
	public void shouldGetSynchronizedTasks() {
		//given
		insertFiveDifferentTasks();

		//when
		List<Task> tasks = dao.getSynchronizedTasks();

		//then
		assertTrue(tasks.size() == 2);
	}

	@Test
	public void shouldGetNotSynchronizedTasks() {
		//given
		insertFiveDifferentTasks();

		//when
		List<Task> tasks = dao.getNotSynchronizedTasks();

		//then
		assertTrue(tasks.size() == 3);
	}

	@After
	public void teardown() {
		if (dao != null) {
			dao.deleteAllTasks();
			dao.destroy();
		}
	}

	private void insertFiveDifferentTasks() {
		Task task1 = new Task("Nowe zadanie1", DateUtil.todayWithHour());
		task1.setStatus(StateType.DONE);
		task1.setGoogleId("googleid01");
		dao.save(task1);

		Task task2 = new Task("Nowe zadanie2", DateUtil.todayWithHour());
		task2.setStatus(StateType.IN_QUEUE);
		dao.save(task2);

		Task task3 = new Task("Nowe zadanie3", DateUtil.tomorrowWithHour());
		task3.setGoogleId("googleid02");
		task3.setStatus(StateType.DONE);
		dao.save(task3);

		Task task4 = new Task("Nowe zadanie4", DateUtil.thirdDayWithHour());
		task4.setStatus(StateType.IN_QUEUE);
		dao.save(task4);

		Task task5 = new Task("Nowe zadanie5", DateUtil.todayWithHour());
		task5.setStatus(StateType.REMOVED);
		dao.save(task5);
	}

}