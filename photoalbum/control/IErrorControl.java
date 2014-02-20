package control;

/**
 * @author Mark Labrador
 *
 */
public interface IErrorControl {
	/**
	 * Receives a string a method passes and sets it to a variable.
	 * @param msg will contain the error message the method passed.
	 */
	public void setErrorMessage(String msg);
	/**
	 * showError prints the error that will warn the user if it 
	 * conflicts with an existing object or
	 * if it doesn't exist
	 */
	public void showError();
	/**
	 * hides the error if the user resolves the error he/she created.
	 */
	public void hideError();
}