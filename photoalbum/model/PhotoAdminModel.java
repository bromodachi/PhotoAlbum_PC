package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import java.util.UUID;

public class PhotoAdminModel implements IPhotoAdminModel {
	
	public static void main(String[] args) {
		System.out.println("Running...");
		String testUser = "TestUser";
		String testUser2 = "TestUser2";
		PhotoAdminModel model = new PhotoAdminModel();
//		model.addUser(testUser, "testUser");
//		model.addUser(testUser2, "testUser2");
		model.loadPreviousSession();
		//model.saveCurrentSession();
		List<String> users = model.getUserIDs();
		for(String s : users) {
			System.out.println("UserId: " + s);
		}
	}
	
	private String userdatabase;
	private List<IUser> users;
	
	public PhotoAdminModel() {
		this.userdatabase = "data/users/";
		this.users = new ArrayList<IUser>();
	}
	
	@Override
	public IUser addUser(String userId, String username) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, userId);
		IUser newUser = null;
		if(index >= 0) {
			if(this.users.get(index).getUserId().equals(userId)) newUser = this.users.get(index);
		} else {
			newUser = new User(userId, username);
			this.users.add(newUser);
		}
		return newUser;
	}

	@Override
	public IUser getUser(String userId) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, userId);
		if(index < 0)	return null;
		IUser user = this.users.get(index);
		if(user.getUserId().equals(userId)) return user;
		try{
			FileInputStream fileIn = new FileInputStream(userdatabase + user.getUserId());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			user = (IUser)in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			System.out.println("Failed to read user from storage to memory.");
		} catch(ClassNotFoundException c) {
			System.out.println("Failed to read user due to a non-existence of class definition.");
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see model.IPhotoAdminModel#writeUser(java.lang.String)
	 * Requires that the user be serialized and saved to storage (Flat-File)
	 */
	@Override
	public void writeUser(String userId) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, userId);
		if(index < 0) return;
		IUser user = this.users.get(index);
		try {
			if(user.getUserId().equals(userId)) {
				FileOutputStream fileOut = new FileOutputStream(userdatabase + user.getUserId());
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(user);
				out.close();
				fileOut.close();
			}
		} catch(IOException i) {
			System.out.println("Failed to write user to storage from memory.");
		}
	}

	@Override
	public void deleteUser(String userId) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, userId);
		IUser user = this.users.get(index);
		if(user.equals(userId)) {
			this.users.remove(index);
			File file = new File(userdatabase + userId + ".ser");
			file.delete();
		}
	}

	@Override
	public List<String> getUserIDs() {
		ArrayList<String> userStrings = new ArrayList<String>();
		for(IUser u : this.users) {
			userStrings.add(u.getUserId());
		}
		return userStrings;
	}

	@Override
	public void loadPreviousSession() {
		try {
			File dir = new File(userdatabase);
			for(File child : dir.listFiles()) {
					FileInputStream fileIn = new FileInputStream(child.getAbsoluteFile());
					ObjectInputStream in = new ObjectInputStream(fileIn);
					User user = (User)in.readObject();
					this.users.add(user);
					in.close();
					fileIn.close();
			}
		} catch (Exception e) {
			System.out.println();
		}
	}

	@Override
	public void saveCurrentSession() {
		try{
			for(IUser u : this.users) {
				this.writeUser(u.getUserId());
			}
		} catch (Exception i) {
			System.out.println();
		}
	}

	@Override
	public IUser loadPreviousUserSession(String userid) {
		File dir = new File(userdatabase);
		for(File child : dir.listFiles()) {
			try{
				if(child.getName().equals(userid)) {
					FileInputStream fileIn = new FileInputStream(child.getAbsoluteFile());
					ObjectInputStream in = new ObjectInputStream(fileIn);
					User user = (User)in.readObject();
					this.users.add(user);
					in.close();
					fileIn.close();
					return user;
				}
			} catch(Exception e) {
				System.out.println();
			}
		}
		return null;
	}

	@Override
	public void saveCurrentUserSession(IUser user) {
		try{
			File file = new File(user.getUserId());
			if(!file.exists()) file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(user.getUserId());
			this.writeUser(user.getUserId());
		} catch (Exception e) {
			System.out.println();
		}
	}

	@Override
	public boolean photoExists(String fileName) {
		File file = new File(fileName);
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
	

	@Override
	public boolean userExists(String userId) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, userId);
		if(index >= 0) {
			IUser user = this.users.get(index);
			if(user.getUserId().equals(userId)) return true;
		}
		return false;
	}
	
	private class UserComparator implements Comparator<IUser> {
		@Override
		public int compare(IUser o1, IUser o2) {
			return o1.getUserId().compareTo(o2.getUserId());
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
		String sNewDate = new SimpleDateFormat("MM/DD/YYYY-HH:MM:SS").format(pDate);
		return sNewDate;
	}
}
