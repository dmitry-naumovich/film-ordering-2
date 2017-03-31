package by.epam.naumovich.film_ordering.command.util;

public class FileNotUploadedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNotUploadedException(String message) {
		super(message);
	}


	public FileNotUploadedException(String message, Throwable cause) {
		super(message, cause);
	}

}
