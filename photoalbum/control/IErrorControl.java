package control;

/**
 * @author Mark Labrador
 *
 */
public interface IErrorControl {
	public void setErrorMessage(String msg);
	public void showError();
	public void hideError();
}
