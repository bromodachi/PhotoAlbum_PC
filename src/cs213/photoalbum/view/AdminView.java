package cs213.photoalbum.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionListener;

import cs213.photoalbum.model.IUser;

public abstract class AdminView extends JFrame implements IErrorView {

	public AdminView() {
		this.setMinimumSize(new Dimension(700, 300));
	}

	public abstract void setUserList(List<IUser> users);

	/* Existing and new user management control. */
	public abstract String getSelectedUsername();

	public abstract void setSelectedUsername(String username);

	public abstract String getSelectedUserImg();

	public abstract void setSelectedUserImg(String img_path);

	public abstract void enableNewUserControl();

	public abstract void disableNewUserControl();

	public abstract void enableDelUserControl();

	public abstract void disableDelUserControl();

	public abstract void registerUserListSelection(
			ListSelectionListener userListSL);

	public abstract void registerNewUserAction(ActionListener newUserAL);

	public abstract void registerDelUserAction(ActionListener delUserAL);

	/* User Information. */
	//Current Username Field
	public abstract String getCurrUsername();

	public abstract void setCurrUsername(String username);

	public abstract void enableCurrUsername();

	public abstract void disableCurrUsername();

	//Current Fullname Field
	public abstract String getCurrFullname();

	public abstract void setCurrFullname(String fullname);

	public abstract void enableCurrFullname();

	public abstract void disableCurrFullname();

	//Current Password Field
	public abstract char[] getCurrPassword();

	public abstract void setCurrPassword(String password);

	public abstract void clearCurrPassword();

	public abstract void enableCurrPassword();

	public abstract void disableCurrPassword();

	//Repeat Current Password Field
	public abstract char[] getRepeatCurrPassword();

	public abstract void setRepeatCurrPassword(String password);

	public abstract void clearRepeatCurrPassword();

	public abstract void enableRepeatCurrPassword();

	public abstract void disableRepeatCurrPassword();

	//Apply Control
	public abstract void registerApplyAction(ActionListener applyAL);

	public abstract void enableApply();

	public abstract void disableApply();

	//Cancel Control
	public abstract void registerUserCancelAction(ActionListener cancelAL);

	public abstract void enableCancel();

	public abstract void disableCancel();

	/* User Pic */
	public abstract String getCurrUserImg();

	public abstract void setCurrUserImg(String img_path);
	
	public abstract void enableCurrUserImgControl();
	
	public abstract void disableCurrUserImgControl();
	
	public abstract void enableChangeControl();
	
	public abstract void disableChangeControl();

	public abstract void registerCurrUserImgAction(ActionListener userImgAL);
}
