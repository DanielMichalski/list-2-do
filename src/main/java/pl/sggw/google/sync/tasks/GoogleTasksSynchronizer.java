package pl.sggw.google.sync.tasks;

import android.accounts.*;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.GoogleKeyInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.tasks.Tasks;
import pl.sggw.R;
import pl.sggw.google.account.NoGoogleAccountException;
import pl.sggw.google.sync.tasks.token.ActualizeAuthTokenListener;
import pl.sggw.google.sync.tasks.token.ActualizerAuthToken;
import pl.sggw.google.account.PrefGoogleAccountRepository;

import java.io.IOException;

/**
 * <p/>
 * Sample for Tasks API on Android. It shows how to authenticate using OAuth 2.0, and get the list
 * of tasks.
 * <p/>
 * <pre>
 * adb shell setprop log.tag.HttpTransport DEBUG
 * </pre>
 *
 * @author Johan Euphrosine
 */
public class GoogleTasksSynchronizer implements ActualizeAuthTokenListener {
	private static final String TAG = GoogleTasksSynchronizer.class.getSimpleName();

	private final HttpTransport transport = AndroidHttp.newCompatibleTransport();

	private final JsonFactory jsonFactory = new GsonFactory();

	private GoogleCredential credential;

	private ActualizerAuthToken actualizerAuthToken;

	private GoogleAccountManager googleAccountManager;

	private Tasks service;


	private Context ctx;

	private boolean received401;

	public GoogleTasksSynchronizer(Activity activity) {
		this.ctx = activity;
		this.service = createTasksService(activity);
	}

	private Tasks createTasksService(Activity activity) {
		ClientCredentials.errorIfNotSpecified();
		actualizerAuthToken = new ActualizerAuthToken(activity, this);
		googleAccountManager = new GoogleAccountManager(activity);
		credential = new GoogleCredential();
		return new Tasks.Builder(transport, jsonFactory, credential)
				.setApplicationName(activity.getString(R.string.app_name))
				.setJsonHttpRequestInitializer(new GoogleKeyInitializer(ClientCredentials.KEY))
				.build();
	}


	public void sync() throws NoGoogleAccountException{
		String accountName = PrefGoogleAccountRepository.getAccountName(ctx);
		Account account = googleAccountManager.getAccountByName(accountName);
		Log.i(TAG, "sync account" + accountName);
		if (account == null) {
			throw new NoGoogleAccountException();
		}

		runAsyncTask(account);
	}

	private void runAsyncTask(Account account){
		credential.setAccessToken(PrefGoogleAccountRepository.getAuthToken(ctx));
		Log.i(TAG, "sync token " + credential.getAccessToken());
		if (credential.getAccessToken() == null) {
			actualizerAuthToken.actualize(account);
			return;
		}

		new AsyncGoogleTasksLoader(ctx, this).execute();
	}

	@Override
	public void actualizedAuthToken(Account account, String token) {
		runAsyncTask(account);
	}

	public void onRequestCompleted() {
		received401 = false;
	}

	public void handleGoogleException(IOException e) {
		if (e instanceof GoogleJsonResponseException) {
			GoogleJsonResponseException exception = (GoogleJsonResponseException) e;
			if (exception.getStatusCode() == 401 && !received401) {
				received401 = true;
				googleAccountManager.invalidateAuthToken(credential.getAccessToken());
				credential.setAccessToken(null);
				PrefGoogleAccountRepository.flushAuthToken(ctx);
				try {
					sync();
				} catch (NoGoogleAccountException e1) {
					Log.e(TAG, e1.getMessage(), e1);
				}
				return;
			}
		}
		Log.e(TAG, e.getMessage(), e);
	}

	public Tasks getService(){
		return service;
	}

}