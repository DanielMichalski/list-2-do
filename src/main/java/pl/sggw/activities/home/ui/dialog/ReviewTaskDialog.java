package pl.sggw.activities.home.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import pl.sggw.R;
import pl.sggw.task.model.Task;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Author: Daniel Michalski
 * Date: 27.11.12
 */

public class ReviewTaskDialog extends AlertDialog.Builder {

	private Format formatter = new SimpleDateFormat("dd-MM-yyyy  HH:mm");

	public ReviewTaskDialog(Context context, Task task) {
		super(context);

		setTitle(task.getTitle());
		String data = formatter.format(task.getDueDate());
		setMessage(task.getNotes()
				+ "\n\n"
				+ data);
		setNeutralButton(R.string.btn_name_ok, null);
	}
}