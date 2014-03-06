package pl.sggw.google.account;

/**
 * @author Daniel
 * @date 03.01.13
 */

public class NoGoogleAccountException extends Exception {

	public NoGoogleAccountException() {
		super("brak nazwy konta w preferencjach");
	}

}
