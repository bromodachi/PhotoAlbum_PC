package cs213.photoalbum.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PhotoModel implements IPhotoModel {
	private String usersdbfile = "bin" + File.separator + "data"
			+ File.separator + "users" + File.separator + "user.data";
	private String photosdbbasepath = "bin" + File.separator + "data"
			+ File.separator + "app" + File.separator;
	private List<IUser> users;

	public PhotoModel() {
		this.users = new ArrayList<IUser>();
	}

	@Override
	public boolean userExists(String username) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, username);
		if (index >= 0) {
			IUser user = this.users.get(index);
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}

	@Override
	public IUser addUser(String username, String fullname, String password, String userimg) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, username);

		IUser newUser = null;
		if (index >= 0) {
			newUser = this.users.get(index);
			if (newUser.getUsername().equals(username)) {
				return newUser;
			}
		}

		newUser = new User(username, fullname, password);
		this.users.add(newUser);
		return newUser;
	}

	public boolean changeUsername(String oldUsername, String newUsername) {
		boolean result = false;
		if (this.userExists(oldUsername) && !this.userExists(newUsername)) {
			this.getUser(oldUsername).setUsername(newUsername);
			result = true;
		}
		return result;
	}

	@Override
	public IUser getUser(String username) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, username);

		if (index < 0)
			return null;
		IUser user = this.users.get(index);
		;
		if (user.getUsername().equals(username))
			return user;
		return null;
	}

	@Override
	public List<IUser> sortedUsers() {
		Collections.sort(this.users, new UserComparator());
		List<IUser> sortedUsersCopy = new ArrayList<IUser>(this.users);
		return sortedUsersCopy;
	}

	@Override
	public void deleteUser(String username) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, username);
		IUser user = this.users.get(index);
		if (user == null)
			return;
		if (user.getUsername().equals(username)) {
			this.users.remove(index);
		}
	}

	@Override
	public List<String> getUsernames() {
		ArrayList<String> userStrings = new ArrayList<String>();
		for (IUser u : this.users) {
			userStrings.add(u.getUsername());
		}
		return userStrings;
	}

	@Override
	public List<IUser> getCopyOfUsers() {
		List<IUser> usercopy = new ArrayList<IUser>(this.users);
		return usercopy;
	}

	@Override
	public void loadPreviousSession() {
		try {
			File f = new File(usersdbfile);
			if (f.exists()) {
				FileInputStream fileIn = new FileInputStream(usersdbfile);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				/*
				 * Generics causes a problem here because the type is not known
				 * until runtime. So checking instanceof only works against
				 * List<?> since it cannot verify object ? type until runtime.
				 */
				this.users = (List<IUser>) in.readObject();
				if (this.users != null)
					Collections.sort(this.users, new UserComparator());
				in.close();
				fileIn.close();
			}
		} catch (Exception e) {
			System.out.println("loadPreviousSession had an error.");//TODO LoadPreviousSession error handling to be done by the View.
			e.printStackTrace();
		}
	}

	@Override
	public void saveCurrentSession() {
		try {
			FileOutputStream f_out = new FileOutputStream(usersdbfile);
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			sortedUsers();
			obj_out.writeObject(this.users);
			obj_out.close();
			f_out.close();
		} catch (Exception i) {
			System.out.println("Could not create a user."); //TODO SaveCurrentSession error handling to be done by the view.
		}
	}

	@Override
	public boolean photoExists(String fileName) {
		File file = new File(photosdbbasepath + fileName);
		return file.exists();
	}

	@Override
	public Date photoFileDate(String fileName) {
		File file = new File(fileName);
		long dateRaw = file.lastModified();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateRaw);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private class UserComparator implements Comparator<IUser> {
		@Override
		public int compare(IUser o1, IUser o2) {
			return o1.getUsername().compareTo(o2.getUsername());
		}
	}

	@Override
	public String photoFileDateString(String fileName) {
		File file = new File(fileName);
		long dateRaw = file.lastModified();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateRaw);
		cal.set(Calendar.MILLISECOND, 0);
		Date pDate = cal.getTime();
		String sNewDate = new SimpleDateFormat("MM/dd/yyyy-HH:MM:SS")
				.format(pDate);
		return sNewDate;
	}

	@Override
	public String getDefaultUserImgPath() {
		return this.photosdbbasepath + "default.png";
	}
}
