package pl.sggw.task.model;

import pl.sggw.task.PriorityType;
import pl.sggw.task.ReminderType;
import pl.sggw.task.RepeatType;
import pl.sggw.task.StateType;
import pl.sggw.util.sync.Synchronizable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Daniel
 * @date 30.10.12
 */
public class Task extends Synchronizable implements Serializable {

	private String title;

	private String notes;

	private Date dueDate;


	private Date alarmDate;

	private PriorityType priority = PriorityType.NONE;

	private RepeatType repeat = RepeatType.ONLY_ONCE;

	private StateType state = StateType.IN_QUEUE;


	public static Task nullObject(Date dueDate) {
		return new Task("", dueDate);
	}

	public Task(Long id, String title, Date dueDate) {
		this.id = id;
		this.title = title;
		this.dueDate = dueDate;
	}

	public Task(String title, Date dueDate) {
		this.title = title;
		this.dueDate = dueDate;
	}

	public boolean isPersist() {
		return id != null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public PriorityType getPriority() {
		return priority;
	}

	public void setPriority(PriorityType priority) {
		this.priority = priority;
	}

	public RepeatType getRepeat() {
		return repeat;
	}

	public void setRepeat(RepeatType repeat) {
		this.repeat = repeat;
	}

	public ReminderType getReminderType() {
		return ReminderType.valueOf(dueDate, alarmDate);
	}

	public Date getAlarmDate() {
		return alarmDate;
	}

	public Long getReminderTimeInMs() {
		Long reminderTimeInMs = null;
		if (alarmDate != null) {
			reminderTimeInMs = alarmDate.getTime();
		}
		return reminderTimeInMs;
	}

	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public StateType getStatus() {
		return state;
	}

	public void setStatus(StateType state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", title='" + title + '\'' +
				", notes='" + notes + '\'' +
				", dueDate=" + dueDate +
				", priority=" + priority +
				", repeat=" + repeat +
				", alarmDate=" + alarmDate +
				", state=" + state +
				'}';
	}
}
