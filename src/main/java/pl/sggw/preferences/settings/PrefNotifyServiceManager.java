package pl.sggw.preferences.settings;

import android.content.Context;
import android.content.Intent;
import android.preference.ListPreference;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.sggw.R;
import pl.sggw.service.NotifyService;

/**
 * @author Daniel
 * @date 10.01.13
 */

@Singleton
public class PrefNotifyServiceManager extends ListPreference {

	@Inject
	public PrefNotifyServiceManager(Context ctx) {
		super(ctx);
		setKey(PrefNotifyServiceRepository.PREF_NOTITY_SERVICE_STATE_KEY);
		setTitle(R.string.preference_title_notify_service_tasks);

		populateNotifyServiceOptionsList();
		settleCurrentOption();
	}

	private void populateNotifyServiceOptionsList() {
		String[] optionsServiceState = {PrefNotifyServiceRepository.NotifyServiceOption.FINISH.getOptionLbl(),
				PrefNotifyServiceRepository.NotifyServiceOption.RUN.getOptionLbl()};
		setEntries(optionsServiceState);

		String[] valuesServiceState = {PrefNotifyServiceRepository.NotifyServiceOption.FINISH.toString(),
				PrefNotifyServiceRepository.NotifyServiceOption.RUN.toString()};
		setEntryValues(valuesServiceState);

		setDefaultValue(valuesServiceState[0]);
	}

	private void settleCurrentOption() {
		PrefNotifyServiceRepository.NotifyServiceOption optionService = PrefNotifyServiceRepository.getNotifyServiceOption(getContext());
		actualizeSummary(optionService.getOptionLbl());
	}

	public void actualizeSummary(String optionServiceLbl) {
		if (optionServiceLbl != null) {
			setSummary(optionServiceLbl);
		}
	}

	public void changeOption(String optionServiceLbl) {
		PrefNotifyServiceRepository.NotifyServiceOption optionService = PrefNotifyServiceRepository.NotifyServiceOption.valueOf(optionServiceLbl);
		actualizeSummary(optionService.getOptionLbl());
		switch (optionService) {
			case FINISH:
				getContext().stopService(new Intent(NotifyService.TASK_SERVICE));
				break;
			case RUN:
				getContext().startService(new Intent(NotifyService.TASK_SERVICE));
				break;
		}
	}

}