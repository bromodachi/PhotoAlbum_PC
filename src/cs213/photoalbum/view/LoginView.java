package cs213.photoalbum.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.DocumentListener;

/**
 * @author Mark Labrador
 * 
 *         First view the user see upon starting the application.
 */
public abstract class LoginView extends JFrame implements IErrorView {
	private static final long serialVersionUID = 1L;

	public LoginView() {
		this.setMinimumSize(new Dimension(400, 230));
		this.setResizable(false);
	}

	/**
	 * Triggers the login process. Depending on the object injected into the
	 * calling method, this will start up either the AdminUI or the UserUI.
	 * 
	 * This object selection is made based on the username entered into the
	 * LoginUI.
	 */
	public abstract void registerLoginAction(ActionListener loginAL);

	/**
	 * Registers an ActionListener with the username field. Form behaviors can
	 * be trigerred by the control as the username changes. Intended to enable
	 * the password field after the initial username entry.
	 * 
	 * @param usernameDL
	 *            ActionListener to be registered by the control.
	 */
	public abstract void registerUsernameDocument(DocumentListener usernameDL);

	/**
	 * @param passwordDL
	 *            ActionListener to be registered by the control.
	 */
	public abstract void registerPasswordDocument(DocumentListener passwordDL);

	/**
	 * @return Current username in the Username field.
	 */
	public abstract String getUsername();

	/**
	 * @param username
	 *            Username to be displayed in the Username field.
	 */
	public abstract void setUsername(String username);

	/**
	 * @param password
	 *            Password associated with the user. This class is used
	 *            primarily to clear the password field.
	 */
	public abstract void setPassword(String password);

	/**
	 * @return Current password in the password field.
	 */
	public abstract char[] getPassword();

	/**
	 * Clears the Password field.
	 */
	public abstract void clearPassword();

	/**
	 * Enables the Username field for username entry.
	 */
	public abstract void enableUsernameUI();

	/**
	 * Disables the Password field preventing username entry or changes.
	 */
	public abstract void disableUsernameUI();

	/**
	 * Enables the Password field for password entry.
	 */
	public abstract void enablePasswordUI();

	/**
	 * Disables the Password field preventing password entry or changes.
	 */
	public abstract void disablePasswordUI();

	/**
	 * Enables the Login interface for logins to occur.
	 */
	public abstract void enableLoginUI();

	/**
	 * Disables the Login interface preventing any logins from taking place.
	 */
	public abstract void disableLoginUI();
}
