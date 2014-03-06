package pl.sggw.activities.editor.logic;

/**
 * @author Daniel
 * @date 31.10.12
 */
public class NameAlreadyExistsException extends Exception {

	public NameAlreadyExistsException() {
		super("Uzytkownik podal nazwe ktora juz istnieje");
	}
}
