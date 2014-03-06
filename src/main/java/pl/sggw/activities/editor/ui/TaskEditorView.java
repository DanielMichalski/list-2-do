package pl.sggw.activities.editor.ui;

import android.content.Intent;
import pl.sggw.task.PriorityType;
import pl.sggw.task.ReminderType;
import pl.sggw.task.RepeatType;

import java.util.Date;

/**
 * @author Daniel
 * @date 30.10.12
 */
public interface TaskEditorView {

	String getNameFromField();

	String getNoteFromField();

	void actualizeName(String name);

	void actualizeNote(String note);

	void actualizePriorityBtn(PriorityType priorityType);

	void actualizeReminderBtn(ReminderType reminderType);

	void actualizeRepeatBtn(RepeatType repeatType);

	void actualizeDueDateBtn(Date dueDate);

	void setListeners();

	void showDialog(int id);

	void startActivityForResult(Intent intent, int reqCode);

	void finishActivityWith(Intent intent);

	void finishActivity();

}
