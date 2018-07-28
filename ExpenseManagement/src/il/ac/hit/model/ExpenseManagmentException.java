package il.ac.hit.model;
/**
 * represent an Exception in ExpenseManagment application
 * @author ortal
 *
 */
public class ExpenseManagmentException extends Exception {

	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message the detail message. the detail message is saved for later
	 * @param cause the cause (which is saved for later retrieval
	 */
	public ExpenseManagmentException(String message,Throwable cause) {
		super(message, cause);
	}
	
	/**
     * Constructs a new exception with the specified detail message.
	 *
	 * @param message the detail message. the detail message is saved for later
	 */
	public ExpenseManagmentException(String message){
		super(message);
	}
}
