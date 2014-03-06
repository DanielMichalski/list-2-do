package pl.sggw.preferences.settings;

import android.os.Bundle;
import android.preference.*;
import com.google.inject.Inject;
import pl.sggw.R;
import pl.sggw.database.dao.TaskDao;
import pl.sggw.google.account.PrefGoogleAccountChooser;
import pl.sggw.google.account.PrefGoogleAccountRepository;
import roboguice.activity.RoboPreferenceActivity;

/**
 * @author Daniel
 * @date 06.12.12
 */

public class SettingsPreferencesActivity extends RoboPreferenceActivity implements Preference.OnPreferenceChangeListener {

	private PrefGoogleAccountChooser prefGoogleAccountChooser;

	private PrefNotifyServiceManager prefNotifyServiceManager;

	private TaskDao taskDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		taskDao = new TaskDao(this);
		addPreferencesFromResource(R.xml.settings_preferences);

		prefGoogleAccountChooser = new PrefGoogleAccountChooser(this);
		PreferenceCategory accountCategory = (PreferenceCategory) findPreference(getString(R.string.preference_key_category_google_task));
		accountCategory.addPreference(prefGoogleAccountChooser);
		prefNotifyServiceManager = new PrefNotifyServiceManager(this);
		PreferenceCategory tasksCategory = (PreferenceCategory) findPreference(getString(R.string.preference_key_category_tasks));
		tasksCategory.addPreference(prefNotifyServiceManager);

		prefGoogleAccountChooser.setOnPreferenceChangeListener(this);
		prefNotifyServiceManager.setOnPreferenceChangeListener(this);

		PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, false);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object object) {
		if (preference.getKey().equals(PrefGoogleAccountRepository.PREF_ACCOUNT_NAME_KEY)) {
			PrefGoogleAccountRepository.flushAuthToken(this);
			prefGoogleAccountChooser.actualizeSummary(object.toString());
			taskDao.deleteAllTasks();
		} else if (preference.getKey().equals(PrefNotifyServiceRepository.PREF_NOTITY_SERVICE_STATE_KEY)) {
			prefNotifyServiceManager.changeOption(object.toString());
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		taskDao.destroy();
	}
}