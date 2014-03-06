package pl.sggw.google.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * @author Daniel
 * @date 06.12.12
 */

public class PrefGoogleAccountRepository {
	private static final String TAG = PrefGoogleAccountRepository.class.getName();

	public static final String PREF_ACCOUNT_NAME_KEY = "accountNameKey";

	public static final String PREF_AUTH_TOKEN_KEY = "authTokenKey";


	public PrefGoogleAccountRepository() {
		Log.d(TAG, "rejestracja");
	}

	public static void saveAccountName(Context context, String accountName) {
		SharedPreferences preferences = getPreferences(context);
		preferences.edit()
				.putString(PREF_ACCOUNT_NAME_KEY, accountName)
				.commit();
		Log.d(TAG, "Zapisano nazwe konta " + accountName);
	}

	public static String getAccountName(Context ctx) {
		SharedPreferences preferences = getPreferences(ctx);
		String accountName = preferences.getString(PREF_ACCOUNT_NAME_KEY, null);
		Log.d(TAG, "Pobrano nazwe konta " + accountName);
		return accountName;
	}

	public static void flushAccountName(Context ctx) {
		SharedPreferences preferences = getPreferences(ctx);
		preferences.edit()
				.remove(PREF_ACCOUNT_NAME_KEY)
				.commit();
	}

	public static void saveAuthToken(Context context, String authToken) {
		SharedPreferences preferences = getPreferences(context);
		preferences.edit()
				.putString(PREF_AUTH_TOKEN_KEY, authToken)
				.commit();
		Log.d(TAG, "Zapisano token " + authToken);
	}

	public static String getAuthToken(Context context) {
		SharedPreferences preferences = getPreferences(context);
		String accessToken = preferences.getString(PREF_AUTH_TOKEN_KEY, null);
		Log.d(TAG, "Pobrano token" + accessToken);
		return accessToken;
	}

	public static void flushAuthToken(Context ctx) {
		SharedPreferences preferences = getPreferences(ctx);
		preferences.edit()
				.remove(PREF_AUTH_TOKEN_KEY)
				.commit();
	}


	private static SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

}