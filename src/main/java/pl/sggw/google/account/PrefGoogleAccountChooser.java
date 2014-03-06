package pl.sggw.google.account;

import android.accounts.Account;
import android.content.Context;
import android.preference.ListPreference;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.sggw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @date 06.12.12
 */

@Singleton
public class PrefGoogleAccountChooser extends ListPreference {

	@Inject
	public PrefGoogleAccountChooser(Context ctx) {
		super(ctx);
		setKey(PrefGoogleAccountRepository.PREF_ACCOUNT_NAME_KEY);
		setTitle(R.string.preference_title_google_account);

		populateAccountsList();
	}

	private void populateAccountsList() {
		List<String> accountNames = getGoogleAccountNames();
		String[] arrayAccountNames = accountNames.toArray(new String[accountNames.size()]);
		setEntries(arrayAccountNames);
		setEntryValues(arrayAccountNames);
		actualizeSummary(PrefGoogleAccountRepository.getAccountName(getContext()));
	}

	public void actualizeSummary(String accountName) {
		if (accountName != null && !accountName.equals("")) {
			setSummary(accountName);
		} else {
			setSummary(R.string.preference_summary_no_choose_account);
		}
	}

	private List<String> getGoogleAccountNames() {
		GoogleAccountManager accountManager = new GoogleAccountManager(getContext());
		Account[] accounts = accountManager.getAccounts();

		List<String> accountNames = new ArrayList<String>();
		for (Account account : accounts) {
			accountNames.add(account.name);
		}
		return accountNames;
	}
}