package pl.sggw.google.sync.tasks.token;

import android.accounts.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import pl.sggw.google.account.PrefGoogleAccountRepository;

import java.io.IOException;

/**
 * @author Daniel
 * @date 03.01.13
 */

public class ActualizerAuthToken {

	private static final String TAG = ActualizerAuthToken.class.getSimpleName();

	// This must be the exact string, and is a special for alias OAuth 2 scope
	// "https://www.googleapis.com/auth/tasks"
	private static final String AUTH_TOKEN_TYPE = "Manage your tasks";

	private Activity activity;

	private AccountManager accountManager;

	private ActualizeAuthTokenListener listener;

	public ActualizerAuthToken(Activity activity, ActualizeAuthTokenListener listener) {
		this.activity = activity;
		accountManager = AccountManager.get(activity);
		this.listener = listener;
	}

	public void actualize(final Account account) {
		accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, activity, new AccountManagerCallback<Bundle>() {
			public void run(AccountManagerFuture<Bundle> future) {
				try {
					Bundle bundle = future.getResult();
					String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
					PrefGoogleAccountRepository.saveAuthToken(activity, authToken);
					listener.actualizedAuthToken(account, authToken);
				} catch (OperationCanceledException e) {
					// uzytkownik anulowal autoryzacje
				} catch (AuthenticatorException e) {
					Log.e(TAG, e.getMessage(), e);
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}, null);
	}

}