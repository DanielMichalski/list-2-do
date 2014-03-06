package pl.sggw.database.exceptions;

/**
 * @author Daniel Michalski
 * @date 06.11.12
 */

public class NoSuchEnumValueException extends Exception {

	public NoSuchEnumValueException() {

	}

    public NoSuchEnumValueException(String message) {
        super(message);
    }

}
