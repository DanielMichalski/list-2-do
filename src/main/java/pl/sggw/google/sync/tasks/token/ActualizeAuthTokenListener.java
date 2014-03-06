package pl.sggw.google.sync.tasks.token;

import android.accounts.Account;

/**
 * @author Daniel
 * @date 03.01.13
 */

public interface ActualizeAuthTokenListener {

	void actualizedAuthToken(Account account, String token);

}