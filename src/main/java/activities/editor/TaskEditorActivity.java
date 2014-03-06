package pl.sggw.activities.editor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import pl.sggw.R;
import pl.sggw.activities.calendar.CalendarActivity;
import pl.sggw.activities.editor.logic.TaskEditPresenter;
import pl.sggw.activities.editor.ui.TaskEditorView;
import pl.sggw.task.PriorityType;
import pl.sggw.task.ReminderType;
import pl.sggw.task.RepeatType;
import pl.sggw.task.model.Task;
import roboguice.activity.RoboActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Daniel
 * @date 30.10.12
 */
public class TaskEditorActivity extends Activity implements TaskEditorView {
	private static String TAG = TaskEditorActivity.class.getName();

	private static final String RESTORE_TASK_KEY = "saved_task_key";

	private static final Format DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");

	public static final int REQUEST_DATE_SELECTOR = 1;

	public static final int REQUEST_VOICE_RECOGNITION = 2;

	public static final String EXTRA_TASK_FOR_EDIT = "extra_task_for_edit";

	public static final String RESULT_EDITED_TASK = "result_edited_task";

	public static final int PRIORITY_DIALOG_ID = 1;

	public static final int REMINDER_DIALOG_ID = 2;

	public static final int REPEAT_DIALOG_ID = 3;

	public static final int MISSING_NAME_DIALOG_ID = 4;

	public static final int NAME_ALREADY_EXISTS_DIALOG_ID = 5;

	private EditText etName;
	private EditText etNote;
	private Button btnDueDate;
	private Button btnReminderTypes;
	private Button btnPriorityTypes;
	private Button btnRepeatTypes;
	private ImageButton btnMicro;
	private Button btnSave;
	private Button btnCancel;

	private TaskEditPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Task task;
		if (savedInstanceState != null) {
			task = (Task) savedInstanceState.get(RESTORE_TASK_KEY);
		} else {
			task = getTaskFromIntent(EXTRA_TASK_FOR_EDIT);
		}

        requestWindowFeature(Window.FEATURE_NO_TITLE);

		presenter = new TaskEditPresenter(this, task);
		setContentView(R.layout.task_editor_layout);

		etName = (EditText) findViewById(R.id.task_name_edit_text);
		etNote = (EditText) findViewById(R.id.edit_note_text);
		btnDueDate = (Button) findViewById(R.id.calendar_button);
		btnPriorityTypes = (Button) findViewById(R.id.priority_button);
		btnReminderTypes = (Button) findViewById(R.id.reminder_button);
		btnRepeatTypes = (Button) findViewById(R.id.repeat_button);
		btnMicro = (ImageButton) findViewById(R.id.button_micro);
		btnSave = (Button) findViewById(R.id.buttonSave);
		btnCancel = (Button) findViewById(R.id.buttonCancel);

		presenter.initializeWith(this);
	}

	private Task getTaskFromIntent(String key) {
		Bundle bundle = getIntent().getExtras();
		return (Task) bundle.getSerializable(key);
	}

	@Override
	public void startActivityForResult(Intent intent, int reqCode) {
		super.startActivityForResult(intent, reqCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_DATE_SELECTOR && resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			Date editedDueDate = (Date) bundle.getSerializable(CalendarActivity.RESULT_DATE);
			presenter.updateDueDate(editedDueDate);
		} else if (requestCode == REQUEST_VOICE_RECOGNITION && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if (!matches.isEmpty()) {
				etName.setText(matches.get(0));
			}
		}
	}

	@Override
	public void finishActivityWith(Intent intent) {
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	public void finishActivity() {
		finish();
	}

	@Override
	public String getNameFromField() {
		return etName.getText().toString();
	}

	@Override
	public String getNoteFromField() {
		return etNote.getText().toString();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		return presenter.getDialogBy(id);
	}

	@Override
	public void actualizeName(String name) {
		etName.setText(name);
	}

	@Override
	public void actualizeNote(String note) {
		etNote.setText(note);
	}

	@Override
	public void actualizePriorityBtn(PriorityType priorityType) {
		btnPriorityTypes.setText(priorityType.getLabel(this));
	}

	@Override
	public void actualizeReminderBtn(ReminderType reminderType) {
		btnReminderTypes.setText(reminderType.getLabel(this));
	}

	@Override
	public void actualizeRepeatBtn(RepeatType repeatType) {
		btnRepeatTypes.setText(repeatType.getLabel(this));
	}

	@Override
	public void actualizeDueDateBtn(Date dueDate) {
		btnDueDate.setText(DATE_FORMAT.format(dueDate));
	}

	@Override
	public void setListeners() {
		btnPriorityTypes.setOnClickListener(presenter);
		btnReminderTypes.setOnClickListener(presenter);
		btnRepeatTypes.setOnClickListener(presenter);
		btnDueDate.setOnClickListener(presenter);
		btnMicro.setOnClickListener(presenter);
		btnSave.setOnClickListener(presenter);
		btnCancel.setOnClickListener(presenter);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(RESTORE_TASK_KEY, presenter.getTask());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.destroy();
	}
}