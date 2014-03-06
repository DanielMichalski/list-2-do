package pl.sggw.activities.editor.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import pl.sggw.R;
import pl.sggw.activities.editor.logic.TaskUpdater;
import pl.sggw.task.RepeatType;

/**
 * @author Daniel
 * @date 30.10.12
 */
public class RepeatDialogBuilder extends AlertDialog.Builder implements DialogInterface.OnClickListener {

	private TaskUpdater taskUpdater;

	private RepeatListAdapter repeatListAdapter;

	public RepeatDialogBuilder(Context context, RepeatType checkedRepeat, TaskUpdater taskUpdater) {
		super(context);
		this.taskUpdater = taskUpdater;

		setTitle(R.string.dialog_title_repeat);
		repeatListAdapter = new RepeatListAdapter(context);
		int checkedItemPosition =  repeatListAdapter.getPosition(checkedRepeat);
		setSingleChoiceItems(repeatListAdapter, checkedItemPosition, this);
	}

	@Override
	public void onClick(DialogInterface dialogInterface, int index) {
		RepeatType repeat = repeatListAdapter.getItem(index);
		taskUpdater.updateRepeat(repeat);
		dialogInterface.dismiss();
	}
}
