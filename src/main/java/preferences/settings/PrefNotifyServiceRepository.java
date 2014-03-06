package pl.sggw.preferences.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import pl.sggw.List2DoApp;
import pl.sggw.R;

/**
 * @author Daniel
 * @date 06.12.12
 */

public class PrefNotifyServiceRepository {
	private static final String TAG = PrefNotifyServiceRepository.class.getName();

	public static final String PREF_NOTITY_SERVICE_STATE_KEY = "systemServiceKey";

	public PrefNotifyServiceRepository() {
		Log.d(TAG, "rejestracja");
	}

	public static NotifyServiceOption getNotifyServiceOption(Context ctx) {
		SharedPreferences preferences = getPreferences(ctx);
		String serviceStateString = preferences.getString(PREF_NOTITY_SERVICE_STATE_KEY, NotifyServiceOption.FINISH.toString());
		NotifyServiceOption notifyServiceOption = NotifyServiceOption.valueOf(serviceStateString);
		Log.d(TAG, "Pobrano stan serwisu " + notifyServiceOption);
		return notifyServiceOption;
	}

	public static void flushServiceOption(Context ctx) {
		SharedPreferences preferences = getPreferences(ctx);
		preferences.edit()
				.remove(PREF_NOTITY_SERVICE_STATE_KEY)
				.commit();
	}

	private static SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public enum NotifyServiceOption {
		RUN {
			@Override
			public String getOptionLbl() {
				return List2DoApp.getStringByResId(R.string.lbl_notify_service_on);
			}
		},
		FINISH {
			@Override
			public String getOptionLbl() {
				return List2DoApp.getStringByResId(R.string.lbl_notify_service_off);
			}
		};

		public abstract String getOptionLbl();
	}

}