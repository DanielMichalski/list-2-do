package pl.sggw.activities.editor.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import pl.sggw.R;
import pl.sggw.activities.editor.logic.TaskUpdater;
import pl.sggw.task.PriorityType;

/**
 * @author Daniel
 * @date 30.10.12
 */
public class PriorityDialogBuilder extends AlertDialog.Builder implements DialogInterface.OnClickListener {

	private TaskUpdater taskUpdater;

	private PriorityListAdapter priorityListAdapter;

	public PriorityDialogBuilder(Context context, PriorityType checkedPriority, TaskUpdater taskUpdater) {
		super(context);
		this.taskUpdater = taskUpdater;

		setTitle(R.string.dialog_title_priority);
		priorityListAdapter = new PriorityListAdapter(context);
		int checkedItemPosition =  priorityListAdapter.getPosition(checkedPriority);
		setSingleChoiceItems(priorityListAdapter, checkedItemPosition, this);
	}

	@Override
	public void onClick(DialogInterface dialogInterface, int index) {
		PriorityType priority = priorityListAdapter.getItem(index);
		taskUpdater.updatePriority(priority);
		dialogInterface.dismiss();
	}
}
