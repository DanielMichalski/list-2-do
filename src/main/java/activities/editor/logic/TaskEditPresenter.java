package pl.sggw.activities.editor.logic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import pl.sggw.R;
import pl.sggw.activities.calendar.CalendarActivity;
import pl.sggw.activities.editor.TaskEditorActivity;
import pl.sggw.activities.editor.ui.TaskEditorView;
import pl.sggw.activities.editor.ui.dialog.PriorityDialogBuilder;
import pl.sggw.activities.editor.ui.dialog.ReminderDialogBuilder;
import pl.sggw.activities.editor.ui.dialog.RepeatDialogBuilder;
import pl.sggw.database.dao.TaskDao;
import pl.sggw.task.PriorityType;
import pl.sggw.task.ReminderType;
import pl.sggw.task.RepeatType;
import pl.sggw.task.model.Task;
import pl.sggw.util.StringUtils;

import java.util.Date;

/**
 * @author Daniel
 * @date 30.10.12
 */

public class TaskEditPresenter implements View.OnClickListener, TaskUpdater {
	private static String TAG = TaskEditPresenter.class.getName();

	private Context ctx;

	private TaskEditorView view;

	private Task task;

	private TaskDao taskDao;

	public TaskEditPresenter(Context ctx, Task task) {
		this.ctx = ctx;
		this.task = task;
		this.taskDao = new TaskDao(ctx);
	}

	public void initializeWith(TaskEditorView view) {
		this.view = view;
		actualizeViews(task);

		view.setListeners();
	}

	public Task getTask() {
		return task;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.priority_button:
				view.showDialog(TaskEditorActivity.PRIORITY_DIALOG_ID);
				break;
			case R.id.reminder_button:
				view.showDialog(TaskEditorActivity.REMINDER_DIALOG_ID);
				break;
			case R.id.repeat_button:
				view.showDialog(TaskEditorActivity.REPEAT_DIALOG_ID);
				break;
			case R.id.calendar_button:
				intent = new Intent(ctx, CalendarActivity.class);
				intent.putExtra(CalendarActivity.EXTRA_DATE_FOR_EDIT, task.getDueDate());
				view.startActivityForResult(intent, TaskEditorActivity.REQUEST_DATE_SELECTOR);
				break;
			case R.id.button_micro:
				intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, ctx.getString(R.string.lbl_speak_slowly));
				view.startActivityForResult(intent, TaskEditorActivity.REQUEST_VOICE_RECOGNITION);
				break;
			case R.id.buttonSave:
				String name = view.getNameFromField();
				String note = view.getNoteFromField();
				try {
					validateTaskName(name);
					task.setTitle(name);
					task.setNotes(note);
					Intent finishIntent = new Intent();
					finishIntent.putExtra(TaskEditorActivity.RESULT_EDITED_TASK, task);
					view.finishActivityWith(finishIntent);
				} catch (MissingNameException e) {
					e.printStackTrace();
					view.showDialog(TaskEditorActivity.MISSING_NAME_DIALOG_ID);
				} catch (NameAlreadyExistsException e) {
					e.printStackTrace();
					view.showDialog(TaskEditorActivity.NAME_ALREADY_EXISTS_DIALOG_ID);
				}
				break;
			case R.id.buttonCancel:
				view.finishActivity();
				break;
		}
	}

	public void validateTaskName(String taskName) throws MissingNameException, NameAlreadyExistsException {
		if (StringUtils.isBlank(taskName)) {
			throw new MissingNameException();
		}
		if (!taskName.equals(task.getTitle()) && taskDao.titleIsExist(taskName)) {
			throw new NameAlreadyExistsException();
		}
	}

	@Override
	public void updatePriority(PriorityType priority) {
		task.setPriority(priority);
		view.actualizePriorityBtn(priority);
	}

	@Override
	public void updateReminder(ReminderType reminder) {
		Long differentTimeInMs = reminder.getDifferentTimeInMs();
		if (differentTimeInMs != null) {
			task.setAlarmDate(new Date(task.getDueDate().getTime() - differentTimeInMs));
		} else {
			task.setAlarmDate(null);
		}
		view.actualizeReminderBtn(reminder);
	}

	@Override
	public void updateRepeat(RepeatType repeat) {
		task.setRepeat(repeat);
		view.actualizeRepeatBtn(repeat);
	}

	@Override
	public void updateDueDate(Date dueDate) {
		task.setDueDate(dueDate);
		view.actualizeDueDateBtn(dueDate);
	}

	private void actualizeViews(Task task) {
		view.actualizeName(task.getTitle());
		view.actualizeNote(task.getNotes());
		view.actualizePriorityBtn(task.getPriority());
		view.actualizeDueDateBtn(task.getDueDate());
		view.actualizeReminderBtn(task.getReminderType());
		view.actualizeRepeatBtn(task.getRepeat());
	}

	public Dialog getDialogBy(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		switch (id) {
			case TaskEditorActivity.PRIORITY_DIALOG_ID:
				builder = new PriorityDialogBuilder(ctx, task.getPriority(), this);
				break;
			case TaskEditorActivity.REMINDER_DIALOG_ID:
				builder = new ReminderDialogBuilder(ctx, task.getReminderType(), this);
				break;
			case TaskEditorActivity.REPEAT_DIALOG_ID:
				builder = new RepeatDialogBuilder(ctx, task.getRepeat(), this);
				break;
			case TaskEditorActivity.MISSING_NAME_DIALOG_ID:
				builder.setTitle(R.string.dialog_title_missing_name);
				builder.setMessage(R.string.dialog_msg_missing_name);
				builder.setNeutralButton(R.string.btn_name_ok, null);
				break;
			case TaskEditorActivity.NAME_ALREADY_EXISTS_DIALOG_ID:
				builder.setTitle(R.string.dialog_title_name_is_exists);
				builder.setMessage(R.string.dialog_msg_name_is_not_available);
				builder.setNeutralButton(R.string.btn_name_ok, null);
				break;
			default:
				Log.e(TAG, "Brak specyfikacji dialogu o ID: " + id);
				break;
		}
		return builder.create();
	}

	public void destroy(){
		taskDao.destroy();
	}
}