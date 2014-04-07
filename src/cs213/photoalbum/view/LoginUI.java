package cs213.photoalbum.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

/**
 * @author Mark Labrador
 *         <p>
 *         One style of the LoginView to enter the program. Multiple styles can
 *         be used.
 *         </p>
 */
public class LoginUI extends LoginView {
	private static final long serialVersionUID = 1L;
	private JLabel titleLbl;
	private JLabel errorLbl;
	private JLabel errorMsg;
	private JLabel usernameLbl;
	private JLabel passwordLbl;
	private JButton loginBtn;
	private JTextField username;
	private JPasswordField password;

	private JPanel loginControls;

	public LoginUI() {
		setup();
	}

	private void setup() { //TODO Set window title.
		this.setLayout(new GridBagLayout());
		
		this.titleLbl = new JLabel("<html><body>"
				+ "<font size=\"10\">PhotoAlbum12</font></body></html>");
		this.errorLbl = new JLabel("Error");
		this.errorMsg = new JLabel();
		this.usernameLbl = new JLabel("Username");
		this.passwordLbl = new JLabel("Password");
		this.loginBtn = new JButton("Login");
		this.username = new JTextField();
		this.password = new JPasswordField();

		Dimension loginControlsDim = new Dimension(275, 130);
		this.loginControls = new JPanel();
		this.loginControls.setPreferredSize(loginControlsDim);
		this.loginControls.setMinimumSize(loginControlsDim);
		this.loginControls.setLayout(new GridBagLayout());

		int major = 1;
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		this.loginControls.add(errorLbl, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = major;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.loginControls.add(errorMsg, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		this.loginControls.add(usernameLbl, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = major;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.loginControls.add(username, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		this.loginControls.add(passwordLbl, c);

		c.gridx = 1;
		c.gridy = 2;
		c.weightx = major;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.loginControls.add(password, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.loginControls.add(loginBtn, c);

		GridBagConstraints rootc = new GridBagConstraints();
		rootc.gridx = 0;
		rootc.gridy = 0;
		this.add(titleLbl, rootc);

		rootc.gridx = 0;
		rootc.gridy = 1;
		this.add(loginControls, rootc);
		this.enableUsernameUI();
		this.disablePasswordUI();
		this.disableLoginUI();
		this.hideError();
		this.pack();
	}

	@Override
	public void registerLoginAction(ActionListener loginAL) {
		this.loginBtn.addActionListener(loginAL);
	}

	@Override
	public void registerUsernameDocument(DocumentListener usernameDL) {
		this.username.getDocument().addDocumentListener(usernameDL);
	}

	@Override
	public void registerPasswordDocument(DocumentListener passwordDL) {
		this.password.getDocument().addDocumentListener(passwordDL);
	}

	@Override
	public String getUsername() {
		return this.username.getText();
	}

	@Override
	public char[] getPassword() {
		return this.password.getPassword();
	}
	
	@Override
	public void setPassword(String password) {
		this.password.setText(password);
	}

	@Override
	public void enableUsernameUI() {
		this.username.setEnabled(true);
	}

	@Override
	public void disableUsernameUI() {
		this.username.setEnabled(false);
	}

	@Override
	public void enablePasswordUI() {
		this.password.setEnabled(true);
	}

	@Override
	public void disablePasswordUI() {
		this.password.setEnabled(false);
	}

	@Override
	public void enableLoginUI() {
		this.loginBtn.setEnabled(true);
	}

	@Override
	public void disableLoginUI() {
		this.loginBtn.setEnabled(false);
	}

	@Override
	public void showError(String errorMsg) {
		this.errorMsg.setText("<html><body><font size=\"2\" color=\"Red\">" + errorMsg + "</font></body></html>");
		//this.errorLbl.setVisible(true);
		this.errorMsg.setVisible(true);
	}

	@Override
	public void hideError() {
		this.errorMsg.setText("");
		this.errorMsg.setVisible(false);
		this.errorLbl.setVisible(false);
	}

	@Override
	public void setUsername(String username) {
		this.username.setText(username);
	}

	@Override
	public void clearPassword() {
		this.password.setText("");
	}

	@Override
	public boolean isError() {
		return this.errorLbl.isVisible();
	}
	
	@Override
	public void setDefaultState() {
		setUsername("");
		setPassword("");
		enableUsernameUI();
		disablePasswordUI();
		disableLoginUI();
	}
}
