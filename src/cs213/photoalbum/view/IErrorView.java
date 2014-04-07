package cs213.photoalbum.view;

public interface IErrorView {
	/**
	 * @return True, if the form is showing an error message. Otherwise, false.
	 */
	public abstract boolean isError();

	/**
	 * Sets and shows the error fields with error message.
	 * 
	 * @param errorMsg
	 *            Error message to be displayed to the user.
	 */
	public abstract void showError(String errorMsg);

	/**
	 * Clears current error message and hides the error fields.
	 */
	public abstract void hideError();
}
