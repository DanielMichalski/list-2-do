package pl.sggw.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import pl.sggw.R;
import pl.sggw.activities.home.ui.actionbar.AddTaskAction;
import pl.sggw.activities.home.ui.actionbar.SortTaskAction;
import pl.sggw.activities.home.ui.actionbar.SyncTasksAction;
import pl.sggw.activities.home.ui.dialog.AboutAppDialog;
import pl.sggw.preferences.settings.SettingsPreferencesActivity;
import pl.sggw.widget.actionbar.ActionBar;
import pl.sggw.widget.tabhost.TabHostPopulator;
import roboguice.activity.RoboTabActivity;
import roboguice.inject.InjectView;

/**
 * @author Daniel
 * @date 27.10.12
 */

public class HomeActivity extends RoboTabActivity {
	private static String TAG = HomeActivity.class.getName();

	@InjectView(R.id.actionbar)
	private ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		configureTabs();
		onCreateActionBar();
	}

	private void configureTabs() {
		TabHostPopulator tabHostPopulator = new TabHostPopulator(this, getTabHost());
		tabHostPopulator.newTab(
                R.string.lbl_today,
                new Intent().setClass(this, TodayTasksActivity.class));
		tabHostPopulator.newTab(
                R.string.lbl_tomorrow,
                new Intent().setClass(this, TomorrowTasksActivity.class));
		tabHostPopulator.newTab(
                R.string.lbl_later,
                new Intent().setClass(this, LaterTasksActivity.class));
		getTabHost().setCurrentTab(0);
	}

	private void onCreateActionBar() {
		actionBar.setTitleBase(getTitle());
		actionBar.addBackAction(R.drawable.ic_tab_task_selected, this);

		actionBar.addAction(new SyncTasksAction(this));
		actionBar.addActionWithSubmenu(new SortTaskAction(this));
		actionBar.addAction(new AddTaskAction(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_about:
				AboutAppDialog aboutDlg = new AboutAppDialog(this);
				aboutDlg.show();
				break;
			case R.id.menu_settings:
				startActivity(new Intent(this, SettingsPreferencesActivity.class));
				break;
		}
		return true;
	}

}