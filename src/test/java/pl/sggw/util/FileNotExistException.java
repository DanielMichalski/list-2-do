package pl.sggw.util;

public class FileNotExistException extends RuntimeException {

	public FileNotExistException() {
	}

	public FileNotExistException(String detailMessage) {
		super(detailMessage);
	}
}
