package pl.sggw.database.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: mattie
 * Date: 08.01.13
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
public class OperationFailedException extends RuntimeException {

	public OperationFailedException() {
	}

	public OperationFailedException(String detailMessage) {
		super(detailMessage);
	}

}
