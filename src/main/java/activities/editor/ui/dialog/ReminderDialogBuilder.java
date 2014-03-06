package pl.sggw.activities.editor.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import pl.sggw.R;
import pl.sggw.activities.editor.logic.TaskUpdater;
import pl.sggw.task.ReminderType;

/**
 * @author Daniel
 * @date 30.10.12
 */
public class ReminderDialogBuilder extends AlertDialog.Builder implements DialogInterface.OnClickListener {

	private TaskUpdater taskUpdater;

	private ReminderListAdapter reminderListAdapter;

	public ReminderDialogBuilder(Context context, ReminderType checkedReminder, TaskUpdater taskUpdater) {
		super(context);
		this.taskUpdater = taskUpdater;

		setTitle(R.string.dialog_title_reminder);
		reminderListAdapter = new ReminderListAdapter(context);
		int checkedItemPosition = reminderListAdapter.getPosition(checkedReminder);
		setSingleChoiceItems(reminderListAdapter, checkedItemPosition, this);
	}

	@Override
	public void onClick(DialogInterface dialogInterface, int index) {
		ReminderType reminder = reminderListAdapter.getItem(index);
		taskUpdater.updateReminder(reminder);
		dialogInterface.dismiss();
	}
}
