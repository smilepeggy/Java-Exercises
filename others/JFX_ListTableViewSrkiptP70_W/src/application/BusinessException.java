package application;

public class BusinessException extends Exception { // Exception -> checked, RuntimeException-> unchecked (ex.
													// ArrayindexOutOfBounds) //

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

}
