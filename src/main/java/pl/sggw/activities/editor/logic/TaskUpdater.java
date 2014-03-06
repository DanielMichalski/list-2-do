package pl.sggw.activities.editor.logic;

import pl.sggw.task.PriorityType;
import pl.sggw.task.ReminderType;
import pl.sggw.task.RepeatType;

import java.util.Date;

/**
 * @author Daniel
 * @date 30.10.12
 */
public interface TaskUpdater {

	void updatePriority(PriorityType priorityType);

	void updateReminder(ReminderType reminder);

	void updateRepeat(RepeatType repeat);

	void updateDueDate(Date dueDate);

}
