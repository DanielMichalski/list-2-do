package pl.sggw.activities.home.ui.actionbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import pl.sggw.R;
import pl.sggw.google.account.NoGoogleAccountException;
import pl.sggw.google.sync.tasks.GoogleTasksSynchronizer;
import pl.sggw.widget.actionbar.ActionBar;

/**
 * @author Daniel
 * @date 03.01.13
 */

public class SyncTasksAction implements ActionBar.Action {
	private static String TAG = SyncTasksAction.class.getName();

	private GoogleTasksSynchronizer googleTasksSynchronizer;

	private AlertDialog dialogNoAccount;

	public SyncTasksAction(Activity activity) {
		googleTasksSynchronizer = new GoogleTasksSynchronizer(activity);

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(activity.getString(R.string.dialog_title_no_google_account));
		builder.setMessage(activity.getString(R.string.dialog_msg_select_account));
		builder.setNeutralButton(activity.getString(R.string.btn_name_ok), null);
		dialogNoAccount = builder.create();
	}

	@Override
	public void performAction(View view) {
		try {
			googleTasksSynchronizer.sync();
		} catch (NoGoogleAccountException e) {
			dialogNoAccount.show();
		}
	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_tab_sync_selected;
	}

	@Override
	public int getId() {
		return R.id.task_sync_action_item;
	}

	@Override
	public String getTitle() {
		return null;
	}
}
