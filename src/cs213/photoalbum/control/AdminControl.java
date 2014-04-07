package cs213.photoalbum.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.IUser;
import cs213.photoalbum.view.AdminView;

/**
 * @author Mark Labrador
 * 
 */
public class AdminControl implements IAdminControl {
	private IPhotoModel model;
	private AdminView view;
	private String currUserImgPath = IPhotoModel.defaultUserImgPath;
	private boolean isEdit = false;

	public AdminControl(IPhotoModel model, AdminView view) {
		this.model = model;
		this.view = view;
		setup();
	}

	private void setup() {
		this.model.loadPreviousSession();
		this.view.setCurrUserImg(this.model.getDefaultUserImgPath());
		this.view.setUserList(this.model.getCopyOfUsers());
		/*
		 * TODO [DONE] New user action. 
		 * TODO Delete user action. 
		 * TODO [DONE] Selected user action. 
		 * TODO Apply user information action. 
		 * TODO [DONE] Cancel user information action. 
		 * TODO Change user image action. 
		 * TODO Update user information after information and image change.
		 * TODO [DONE] Save user session before closing admin window.
		 */
		this.view.registerNewUserAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isEdit = false;
				currUserImgPath = IPhotoModel.defaultUserImgPath;
				view.setDefaultState();
				view.setUserList(model.getCopyOfUsers());
				view.disableNewUserControl();
				view.disableUserList();
				view.enableCurrUsername();
				view.enableCurrFullname();
				view.enableCurrUserImgControl();
				view.enableChangeControl();
				view.enableCancel();
			}
		});

		this.view.registerDelUserAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isEdit = false;
				currUserImgPath = IPhotoModel.defaultUserImgPath;
				model.deleteUser(view.getSelectedUsername());
				view.deleteSelectedUser();
				view.setDefaultState();
				view.setUserList(model.getCopyOfUsers());
			}
		});

		this.view.registerUserListSelection(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				isEdit = true;
				IUser curr = model.getUser(view.getSelectedUsername());
				if (curr != null) {
					currUserImgPath = curr.getUserImgPath();
					
					view.setCurrUsername(curr.getUsername());
					view.setCurrFullname(curr.getFullname());
					view.setCurrPassword(curr.getPassword());
					view.setRepeatCurrPassword(curr.getPassword());
					view.setCurrUserImg(curr.getUserImgPath());
					
					view.enableDelUserControl();
					view.enableCancel();
					view.enableCurrUserImgControl();
					view.enableChangeControl();
				}
			}
		});

		/* User Information */
		UserInfoState uiState = new UserInfoState();
		UserInfoAcceptance uiAcceptState = new UserInfoAcceptance();
		this.view.registerCurrUsernameDocument(uiState);
		this.view.registerCurrFullnameDocument(uiState);
		this.view.registerCurrPasswordDocument(uiState);
		this.view.registerRepeatPasswordDocument(uiState);
		this.view.registerApplyAction(uiAcceptState);

		this.view.registerUserCancelAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currUserImgPath = IPhotoModel.defaultUserImgPath;
				view.setDefaultState();
				view.setUserList(model.getCopyOfUsers());
			}
		});

		this.view.registerCurrUserImgAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setFileHidingEnabled(true);
				fc.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return false;
						}

						String ext = f.getName().split(".").length > 1 ? f
								.getName().split(".")[1] : "";
						if (!ext.isEmpty()) {
							if (ext.equals("tiff") || ext.equals("tif")
									|| ext.equals("gif") || ext.equals("jpeg")
									|| ext.equals("jpg") || ext.equals("png")) {
								return true;
							} else {
								return false;
							}
						}
						return false;
					}

					@Override
					public String getDescription() {
						//Do nothing
						return null;
					}
				});
				int rVal = fc.showOpenDialog(view);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//view.setSelectedUserImg(file.getAbsolutePath()); //TODO Reconsider whether you need the setSelectedUserImg() method.
					currUserImgPath = file.getAbsolutePath();
					view.setCurrUserImg(currUserImgPath);
				}
			}
		});
		
		this.view.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowClosing(WindowEvent e) {
				model.saveCurrentSession();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				model.saveCurrentSession();
			}

			@Override
			public void windowIconified(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowActivated(WindowEvent e) {
				//Do nothing.
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				//Do nothing.
			}
			
		});
	}

	/**
	 * @author Mark Labrador
	 *         <p>
	 *         Startups the administrative program.
	 *         </p>
	 */
	@Override
	public void run() {
		this.view.setDefaultState();
		this.view.setUserList(this.model.getCopyOfUsers());
		this.view.setVisible(true);
	}

	@Override
	public void setAdminModel(IPhotoModel model) {
		this.model = model;
	}

	@Override
	public void listUsers() {
		String success = "";
		for (int i = 0; i < model.getUsernames().size(); i++) {
			success = success + model.getUsernames().get(i) + "\n";
		}
		//		setErrorMessage(success);
		//		showError();
	}

	@Override
	public void addUser(String username, String fullname, String password, String userimg) {
		if (verifyUser(username)) {
			String error = "user " + username + " already exists with name "
					+ fullname;
			//			setErrorMessage(error);
			//			showError();
			return;
		}
		this.model.addUser(username, fullname, password, userimg);
		String msg = "created user " + username + " with name " + fullname;
		//		setErrorMessage(msg);
		//		showError();
		this.listUsers();
	}

	@Override
	public void deleteUser(String username) {
		if (!verifyUser(username)) {
			String error = "user " + username + " does not exist";
			//			setErrorMessage(error);
			//			showError();
		}
		model.deleteUser(username);
		String msg = "deleted user " + username;
		//		setErrorMessage(msg);
		//		showError();
	}

	public boolean verifyUser(String id) {
		return model.getUsernames().contains(id);
	}

	private class UserInfoState implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			validate();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			validate();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			validate();
		}

		private void validate() {
			if (!view.getCurrUsername().isEmpty()
					&& !view.getCurrFullname().isEmpty()) {
				view.enableCurrUsername();
				view.enableCurrFullname();
				view.enableCurrPassword();
				if (view.getCurrPassword().length > 0) {
					view.enableRepeatCurrPassword();
					if (view.getRepeatCurrPassword().length > 0) {
						String currPass = new String(view.getCurrPassword());
						String repeatPass = new String(
								view.getRepeatCurrPassword());
						if (currPass.equals(repeatPass)) {
							view.enableApply();
						} else {
							view.disableApply();
						}
					} else {
						view.disableApply();
					}
				} else {
					view.disableRepeatCurrPassword();
					view.disableApply();
				}
			} else {
				view.disableCurrPassword();
				view.disableRepeatCurrPassword();
				view.disableApply();
			}
		}
	}

	private class UserInfoAcceptance implements ActionListener {
		//TODO Save old username information in case it's an edit of the username.
		//TODO User cannot be admin.
		@Override
		public void actionPerformed(ActionEvent e) {
			view.hideError();
			view.showError("<html><body><font size=\"2\" color=\"red\">Please see highlighted areas for errors.</font></body></html>");
			if(model.userExists(view.getCurrUsername())){
				//TODO If it is an edit, ensure the username currently in the field does not exist already.
				if(!isEdit) {
					view.showError("<html><body><font size=\"2\" color=\"red\">User already exists.</font></body></html>");
				} else {
					IUser curr = model.getUser(view.getCurrUsername());
					curr.setUsername(view.getCurrUsername());
					curr.setFullname(view.getCurrFullname());
					curr.setPassword(new String(view.getCurrPassword()));
					curr.setUserImgPath(currUserImgPath);
					view.setDefaultState();
					view.setUserList(model.getCopyOfUsers());
				}
			} else {
				model.addUser(view.getCurrUsername(), view.getCurrFullname(), new String(view.getCurrPassword()), currUserImgPath);
				view.setDefaultState();
				view.setUserList(model.getCopyOfUsers());
			}
		}
	}
}