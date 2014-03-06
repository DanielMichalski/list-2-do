package pl.sggw.util;

import com.google.api.client.util.DateTime;
import org.junit.Test;
import pl.sggw.google.task.GoogleTask;
import pl.sggw.task.PriorityType;
import pl.sggw.task.RepeatType;
import pl.sggw.task.StateType;
import pl.sggw.task.model.Task;
import pl.sggw.util.time.DateUtil;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class ConverterTasksTest {

	@Test
	public void shouldConvertGoogleTaskToTask() {
		//given
		DateTime dueDateWithoutTime = new DateTime(DateUtil.resetTime(new Date()).getTime());
		GoogleTask googleTask = buildGoogleTask("hsmauwnd", "tytul", "notka", dueDateWithoutTime, StateType.IN_QUEUE);

		//when
		Task convertedTask = ConverterTasks.fromGoogleTask(googleTask);

		//then
		assertTrue(convertedTask.getGoogleId().equals(googleTask.getId()));
		assertTrue(convertedTask.getTitle().equals(googleTask.getTitle()));
		assertTrue(convertedTask.getNotes().equals(googleTask.getNotes()));
		assertTrue(convertedTask.getDueDate().equals(googleTask.getDueDateWithoutTime()));
	}

	@Test
	public void shouldConvertTaskToGoogleTask() {
		//given
		Task taskToConvert = buildTask(20L, "cxzawegs", "tytul", null, new Date());

		//when
		GoogleTask convertedGoogleTask = ConverterTasks.toGoogleTask(taskToConvert);

		//then
		assertTrue(convertedGoogleTask.getId().equals(taskToConvert.getGoogleId()));
		assertTrue(convertedGoogleTask.getTitle().equals(taskToConvert.getTitle()));
		assertTrue(convertedGoogleTask.getNotes().equals(""));
		assertTrue(convertedGoogleTask.getDueDateWithoutTime().equals(DateUtil.resetTime(taskToConvert.getDueDate())));
	}

	private Task buildTask(Long id, String googleId, String title, String notes, Date dueDate) {
		Task taskDto = new Task(id, title, dueDate);
		taskDto.setGoogleId(googleId);
		taskDto.setNotes(notes);
		taskDto.setAlarmDate(null);
		taskDto.setPriority(PriorityType.HIGH);
		taskDto.setRepeat(RepeatType.ONLY_ONCE);
		taskDto.setStatus(StateType.DONE);

		return taskDto;
	}

	private GoogleTask buildGoogleTask(String id, String title, String notes, DateTime dueDate, StateType status) {
		GoogleTask googleTaskDto = new GoogleTask(id, title);
		googleTaskDto.setNotes(notes);
		googleTaskDto.setDue(dueDate);
		googleTaskDto.setStatus(status);

		return googleTaskDto;
	}

}