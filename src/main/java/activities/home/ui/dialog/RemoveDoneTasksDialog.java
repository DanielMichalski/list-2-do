package pl.sggw.activities.home.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import pl.sggw.List2DoApp;
import pl.sggw.R;
import pl.sggw.activities.home.logic.TabType;
import pl.sggw.activities.home.logic.TaskUpdater;

/**
 * Author: Daniel Michalski
 * Date: 27.11.12
 */

public class RemoveDoneTasksDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener {

	private Context context;

	private TaskUpdater taskUpdater;

	public RemoveDoneTasksDialog(Context context, TaskUpdater taskUpdater, TabType tabType) {
		super(context);
		this.context = context;
		this.taskUpdater = taskUpdater;

		String msg = List2DoApp.getStringByResId(R.string.dialog_msg_remove_completed_tasks) +
				" \"" + tabType.getTabName() +"\"";
		setTitle(context.getString(R.string.dialog_title_remove_completed_tasks));
		setMessage(msg);
		setPositiveButton(context.getString(R.string.btn_name_yes), this);
		setNegativeButton(context.getString(R.string.btn_name_no), null);
	}

	@Override
	public void onClick(DialogInterface dialogInterface, int which) {
		switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				taskUpdater.removeDoneTasksFromDB();
				Toast.makeText(context, context.getString(R.string.lbl_removed_completed_tasks), Toast.LENGTH_LONG).show();
				break;
		}
	}
}
