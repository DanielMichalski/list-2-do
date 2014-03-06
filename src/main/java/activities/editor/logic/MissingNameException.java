package pl.sggw.activities.editor.logic;

/**
 * @author Daniel
 * @date 31.10.12
 */
public class MissingNameException extends Exception{

	public MissingNameException() {
		super("Uzytkownik nie wpisal nazwy zadania");
	}
}
