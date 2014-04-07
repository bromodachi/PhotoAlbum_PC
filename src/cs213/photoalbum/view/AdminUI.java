package cs213.photoalbum.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import cs213.photoalbum.model.IUser;

public class AdminUI extends AdminView {
	private static final long serialVersionUID = 1L;
	private JPanel userManagement;
	private JPanel userInformation;
	private JPanel userImgInformation;
	private JPanel userManagementControls;
	private JPanel userInformationControls;

	private JLabel userlistLbl;
	private JLabel userInfoLbl;
	private JLabel usernameInfoLbl;
	private JLabel fullnameInfoLbl;
	private JLabel passwordInfoLbl;
	private JLabel repeatPasswordInfoLbl;
	private JLabel imageInfo;

	private DefaultListModel<IUser> userlistModel;
	private JList<IUser> userlist;

	private JTextField usernameInfo;
	private JTextField fullnameInfo;
	private JPasswordField passwordInfo;
	private JPasswordField repeatPasswordInfo;

	private JButton newUserBtn;
	private JButton delUserBtn;
	private JButton applyBtn;
	private JButton cancelBtn;
	private JButton changeImgBtn;

	//TODO Add error controls.

	private int iis;
	private String defaultUserImgPath;

	public AdminUI(String defaultUserImgPath) {
		this.defaultUserImgPath = defaultUserImgPath;
		setup();
		defaultState();
	}

	private void setup() {
		this.iis = 180;
		this.setLayout(new GridBagLayout());

		this.userManagement = new JPanel();
		this.userInformation = new JPanel();
		this.userImgInformation = new JPanel();
		this.userManagementControls = new JPanel();
		this.userInformationControls = new JPanel();

		this.userlistLbl = new JLabel(
				"<html><body><font size=\"5\">Users</font></body></html>");
		this.userInfoLbl = new JLabel(
				"<html><body><font size=\"5\">User Information</font></body></html>");
		this.usernameInfoLbl = new JLabel("Username");
		this.fullnameInfoLbl = new JLabel("Full Name");
		this.passwordInfoLbl = new JLabel("Password");
		this.repeatPasswordInfoLbl = new JLabel(
				"<html><body>Repeat Pass.</body></html>");
		this.imageInfo = new JLabel();

		this.userlistModel = new DefaultListModel<IUser>();
		this.userlist = new JList<IUser>(this.userlistModel);
		this.usernameInfo = new JTextField();
		this.fullnameInfo = new JTextField();
		this.passwordInfo = new JPasswordField();
		this.repeatPasswordInfo = new JPasswordField();
		this.newUserBtn = new JButton("New");
		this.delUserBtn = new JButton("Delete");
		this.applyBtn = new JButton("Apply");
		this.cancelBtn = new JButton("Cancel");
		this.changeImgBtn = new JButton("Change Image...");

		/* User Management Panel */
		this.userManagement.setLayout(new GridBagLayout());
		this.userManagementControls.add(newUserBtn);
		this.userManagementControls.add(delUserBtn);
		GridBagConstraints umc = new GridBagConstraints();
		umc.gridx = 0;
		umc.gridy = 0;
		umc.weightx = 1;
		umc.weighty = 0;
		umc.gridwidth = 1;
		umc.insets = new Insets(5, 20, 5, 20);
		umc.fill = GridBagConstraints.NONE;
		umc.anchor = GridBagConstraints.WEST;
		this.userManagement.add(userlistLbl, umc);

		/* Users List */
		JScrollPane userlistScroll = new JScrollPane();
		userlistScroll.setAlignmentX(LEFT_ALIGNMENT);
		userlistScroll.setViewportView(this.userlist);
		this.userlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.userlist.setLayoutOrientation(JList.VERTICAL);
		this.userlist
				.setCellRenderer(new UserItem(this.userlist.getWidth(), 40));

		umc.gridx = 0;
		umc.gridy = 1;
		umc.weightx = 1;
		umc.weighty = 1;
		umc.gridwidth = 1;
		umc.insets = new Insets(5, 20, 5, 20);
		umc.fill = GridBagConstraints.BOTH;
		umc.anchor = GridBagConstraints.CENTER;
		this.userManagement.add(userlistScroll, umc);

		/* User Management Section Controls Panel */
		umc.gridx = 0;
		umc.gridy = 2;
		umc.weightx = 0;
		umc.weighty = 0;
		umc.gridwidth = 1;
		umc.insets = new Insets(5, 5, 5, 5);
		umc.fill = GridBagConstraints.NONE;
		umc.anchor = GridBagConstraints.CENTER;
		this.userManagement.add(this.userManagementControls, umc);

		/* User Information Panel */
		this.userInformation.setLayout(new GridBagLayout());
		this.userInformationControls.add(this.applyBtn);
		this.userInformationControls.add(this.cancelBtn);
		GridBagConstraints uic = new GridBagConstraints();

		/* User Information Label for Section */
		uic.gridx = 0;
		uic.gridy = 0;
		uic.weightx = 1;
		uic.weighty = 0;
		uic.gridwidth = 2;
		uic.insets = new Insets(5, 5, 5, 5);
		uic.fill = GridBagConstraints.NONE;
		uic.anchor = GridBagConstraints.WEST;
		this.userInformation.add(userInfoLbl, uic);

		/* Username Field Label */
		uic.gridx = 0;
		uic.gridy = 1;
		uic.weightx = 0;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.NONE;
		uic.anchor = GridBagConstraints.EAST;
		this.userInformation.add(this.usernameInfoLbl, uic);

		/* Username Field */
		uic.gridx = 1;
		uic.gridy = 1;
		uic.weightx = 1;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.HORIZONTAL;
		uic.anchor = GridBagConstraints.CENTER;
		this.userInformation.add(this.usernameInfo, uic);

		/* Full Name Field Label */
		uic.gridx = 0;
		uic.gridy = 2;
		uic.weightx = 0;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.NONE;
		uic.anchor = GridBagConstraints.EAST;
		this.userInformation.add(this.fullnameInfoLbl, uic);

		/* Full Name Field */
		uic.gridx = 1;
		uic.gridy = 2;
		uic.weightx = 1;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.HORIZONTAL;
		uic.anchor = GridBagConstraints.CENTER;
		this.userInformation.add(this.fullnameInfo, uic);

		/* Password Field Label */
		uic.gridx = 0;
		uic.gridy = 3;
		uic.weightx = 0;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.NONE;
		uic.anchor = GridBagConstraints.EAST;
		this.userInformation.add(this.passwordInfoLbl, uic);

		/* Password Field */
		uic.gridx = 1;
		uic.gridy = 3;
		uic.weightx = 1;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.HORIZONTAL;
		uic.anchor = GridBagConstraints.CENTER;
		this.userInformation.add(this.passwordInfo, uic);

		/* Repeat Password Field Label */
		uic.gridx = 0;
		uic.gridy = 4;
		uic.weightx = 0;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.NONE;
		uic.anchor = GridBagConstraints.EAST;
		this.userInformation.add(this.repeatPasswordInfoLbl, uic);

		/* Repeat Password Field */
		uic.gridx = 1;
		uic.gridy = 4;
		uic.weightx = 1;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.HORIZONTAL;
		uic.anchor = GridBagConstraints.CENTER;
		this.userInformation.add(this.repeatPasswordInfo, uic);

		/* User Information Section Controls Panel */
		uic.gridx = 1;
		uic.gridy = 5;
		uic.weightx = 0;
		uic.weighty = 0;
		uic.gridwidth = 1;
		uic.fill = GridBagConstraints.NONE;
		uic.anchor = GridBagConstraints.EAST;
		this.userInformation.add(this.userInformationControls, uic);

		/* User Image Information Panel */
		this.userImgInformation.setLayout(new GridBagLayout());
		this.imageInfo.setPreferredSize(new Dimension(iis, iis));
		this.imageInfo.setMaximumSize(new Dimension(iis, iis));
		this.imageInfo.setMinimumSize(new Dimension(iis, iis));
		GridBagConstraints uiic = new GridBagConstraints();
		/* User Image */
		uiic.gridx = 0;
		uiic.gridy = 0;
		uiic.weightx = 1;
		uiic.weighty = 1;
		uiic.gridwidth = 1;
		uiic.insets = new Insets(30, 5, 5, 5);
		uiic.fill = GridBagConstraints.NONE;
		uiic.anchor = GridBagConstraints.WEST;
		this.userImgInformation.add(this.imageInfo, uiic);

		/* Change user image button. */
		uiic.gridx = 0;
		uiic.gridy = 1;
		uiic.weightx = 0;
		uiic.weighty = 0;
		uiic.gridwidth = 1;
		uiic.insets = new Insets(5, 5, 5, 5);
		uiic.fill = GridBagConstraints.NONE;
		uiic.anchor = GridBagConstraints.WEST;
		this.userImgInformation.add(this.changeImgBtn, uiic);

		GridBagConstraints rootc = new GridBagConstraints();
		/* User Management Panel */
		rootc.gridx = 0;
		rootc.gridy = 0;
		rootc.weightx = 1;
		rootc.weighty = 1;
		rootc.fill = GridBagConstraints.BOTH;
		rootc.anchor = GridBagConstraints.CENTER;
		this.add(this.userManagement, rootc);

		/* User Information Panel */
		rootc.gridx = 1;
		rootc.fill = GridBagConstraints.HORIZONTAL;
		rootc.anchor = GridBagConstraints.NORTH;
		this.add(this.userInformation, rootc);

		/* User Image Panel */
		rootc.gridx = 2;
		this.add(this.userImgInformation, rootc);
	}
	
	private void defaultState() {
		this.hideError();
		this.enableNewUserControl();
		this.disableDelUserControl();
		this.disableCurrUsername();
		this.disableCurrFullname();
		this.disableCurrPassword();
		this.disableRepeatCurrPassword();
		this.disableApply();
		this.disableCancel();
		this.disableCurrUserImgControl();
		this.disableChangeControl();
	}

	@Override
	public boolean isError() {
		// TODO Check for an error in the administrator's UI.
		return false;
	}

	@Override
	public void showError(String errorMsg) {
		// TODO Show the error in the administrator's UI.

	}

	@Override
	public void hideError() {
		// TODO Hide the error in the administrator's UI.

	}

	@Override
	public void setUserList(List<IUser> users) {
		for (IUser u : users) {
			this.userlistModel.addElement(u);
		}
	}

	@Override
	public String getSelectedUsername() {
		//TODO Retrieve the currently selected user.  Returns null, if no user is selected.
		return null;
	}

	@Override
	public void setSelectedUsername(String username) {
		//TODO Select the user in the list with the current username.
	}

	@Override
	public String getSelectedUserImg() {
		// TODO Retrieve the current user's image.
		return null;
	}

	@Override
	public void setSelectedUserImg(String img_path) {
		// TODO Set the image of the currently selected user.

	}
	
	@Override
	public void enableNewUserControl() {
		this.newUserBtn.setEnabled(true);
	}
	
	@Override
	public void disableNewUserControl() {
		this.newUserBtn.setEnabled(false);
	}
	
	@Override
	public void enableDelUserControl() {
		this.delUserBtn.setEnabled(true);
	}
	
	@Override
	public void disableDelUserControl() {
		this.delUserBtn.setEnabled(false);
	}

	@Override
	public void registerUserListSelection(ListSelectionListener userListSL) {
		// TODO Event registration for selecting users.
	}

	@Override
	public void registerNewUserAction(ActionListener newUserAL) {
		this.newUserBtn.addActionListener(newUserAL);
	}

	@Override
	public void registerDelUserAction(ActionListener delUserAL) {
		this.delUserBtn.addActionListener(delUserAL);
	}

	@Override
	public String getCurrUsername() {
		return this.usernameInfo.getText();
	}

	@Override
	public void setCurrUsername(String username) {
		this.usernameInfo.setText(username);
	}

	@Override
	public void enableCurrUsername() {
		this.usernameInfo.setEnabled(true);
	}

	@Override
	public void disableCurrUsername() {
		this.usernameInfo.setEnabled(false);
	}

	@Override
	public String getCurrFullname() {
		return this.fullnameInfo.getText();
	}

	@Override
	public void setCurrFullname(String fullname) {
		this.fullnameInfo.setText(fullname);
	}

	@Override
	public void enableCurrFullname() {
		this.fullnameInfo.setEnabled(true);
	}

	@Override
	public void disableCurrFullname() {
		this.fullnameInfo.setEnabled(false);
	}

	@Override
	public char[] getCurrPassword() {
		return this.passwordInfo.getPassword();
	}

	@Override
	public void setCurrPassword(String password) {
		this.passwordInfo.setText(password);
	}

	@Override
	public void clearCurrPassword() {
		this.passwordInfo.setText("");
	}

	@Override
	public void enableCurrPassword() {
		this.passwordInfo.setEnabled(true);
	}

	@Override
	public void disableCurrPassword() {
		this.passwordInfo.setEnabled(false);
	}

	@Override
	public char[] getRepeatCurrPassword() {
		return this.repeatPasswordInfo.getPassword();
	}

	@Override
	public void setRepeatCurrPassword(String password) {
		this.repeatPasswordInfo.setText(password);
	}

	@Override
	public void clearRepeatCurrPassword() {
		this.repeatPasswordInfo.setText("");
	}

	@Override
	public void enableRepeatCurrPassword() {
		this.repeatPasswordInfo.setEnabled(true);
	}

	@Override
	public void disableRepeatCurrPassword() {
		this.repeatPasswordInfo.setEnabled(false);
	}

	@Override
	public void registerApplyAction(ActionListener applyAL) {
		this.applyBtn.addActionListener(applyAL);
	}

	@Override
	public void enableApply() {
		this.applyBtn.setEnabled(true);
	}

	@Override
	public void disableApply() {
		this.applyBtn.setEnabled(false);
	}

	@Override
	public void registerUserCancelAction(ActionListener cancelAL) {
		this.cancelBtn.addActionListener(cancelAL);
	}

	@Override
	public void enableCancel() {
		this.cancelBtn.setEnabled(true);
	}

	@Override
	public void disableCancel() {
		this.cancelBtn.setEnabled(false);
	}

	@Override
	public String getCurrUserImg() {
		// TODO Retrieve the image path of the user from the User Image section.
		return null;
	}

	@Override
	public void setCurrUserImg(String img_path) {
		try {
			this.imageInfo.setIcon(createIcon(img_path, iis, iis));
		} catch (IOException e) {
			//TODO Allow View to handle the exception.
		}
	}
	
	@Override
	public void enableChangeControl() {
		this.changeImgBtn.setEnabled(true);
	}

	@Override
	public void disableChangeControl() {
		this.changeImgBtn.setEnabled(false);
	}
	
	@Override
	public void enableCurrUserImgControl() {
		this.userImgInformation.setEnabled(true);
	}

	@Override
	public void disableCurrUserImgControl() {
		this.userImgInformation.setEnabled(false);
	}

	@Override
	public void registerCurrUserImgAction(ActionListener userImgAL) {
		this.changeImgBtn.addActionListener(userImgAL);
	}

	private ImageIcon createIcon(String img_path, int width, int height)
			throws IOException {
		BufferedImage img = ImageIO.read(new File(img_path));
		ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height,
				Image.SCALE_DEFAULT));
		return icon;
	}

	/**
	 * @author Mark Labrador
	 *         <p>
	 *         Displays the user's current image and username in a custom-made
	 *         cell.
	 *         </p>
	 */
	private class UserItem extends JPanel implements ListCellRenderer<IUser> {
		private JLabel userImage;
		private JLabel username;
		private int width;
		private int height;
		private int offset = 10;

		public UserItem(int width, int height) {
			this.width = width;
			this.height = height;
			setup();
		}

		private void setup() {
			this.userImage = new JLabel();
			this.username = new JLabel();
			this.setLayout(new GridBagLayout());
			this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
					Color.black));
			this.setBackground(Color.white);
			this.setPreferredSize(new Dimension(width, height));
			this.setMinimumSize(new Dimension(width, height));

			GridBagConstraints uic = new GridBagConstraints();
			uic.gridx = 0;
			uic.gridy = 0;
			uic.weightx = 0;
			uic.weighty = 0;
			uic.insets = new Insets(5, 5, 5, 5);
			uic.fill = GridBagConstraints.NONE;
			uic.anchor = GridBagConstraints.CENTER;
			this.add(userImage, uic);

			uic.gridx = 1;
			uic.gridy = 0;
			uic.weightx = 1;
			uic.weighty = 0;
			uic.insets = new Insets(0, 0, 0, 10);
			uic.fill = GridBagConstraints.HORIZONTAL;
			uic.anchor = GridBagConstraints.WEST;
			this.add(username, uic);
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends IUser> list, IUser value, int index,
				boolean isSelected, boolean cellHasFocus) {

			if (isSelected)
				this.setBackground(Color.gray);
			else
				this.setBackground(Color.white);

			try {
				this.username.setText(value.getUsername());
				this.userImage.setIcon(createIcon(value.getUserImg(),
						this.height - offset, this.height - offset));
			} catch (IOException e1) {
				try {
					//Try to load the default image, if the user's image does not exist.
					this.userImage.setIcon(createIcon(defaultUserImgPath,
							this.height - offset, this.height - offset));
				} catch (IOException e2) {
					//Do nothing.
				}
			}
			return this;
		}
	}
}
