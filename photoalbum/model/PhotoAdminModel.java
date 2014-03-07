package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PhotoAdminModel implements IPhotoAdminModel {
	
//	public static void main(String[] args) {
//		String testUser = "TestUser";
//		PhotoAdminModel model = new PhotoAdminModel();
//		model.writeUser(testUser);
//		System.out.println("TestUser created");
//	}
	
	private String database;
	private String lastSessionLocation = "./data/lastSession.stat";
	private List<IUser> users;
	
	public PhotoAdminModel() {
		this.database = "../data/";
		this.users = new ArrayList<IUser>();
	}
	
	@Override
	public IUser addUser(String userId, String username) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, userId);
		IUser newUser = null;
		if(index >= 0 && newUser.getUserId().equals(userId)) {
			newUser = this.users.get(index);
		} else {
			newUser = new User(userId, username);
			this.users.add(newUser);
		}
		return newUser;
	}

	@Override
	public IUser getUser(String userId) {
//		Collections.sort(this.users, new UserComparator());
//		int index = Collections.binarySearch(this.users, userId);
//		if(index < 0)	return null;
//		IUser user = this.users.get(index);
//		if(user.getUserId().equals(userId)) return user;
		
		IUser user = null;
		for(IUser u : this.users) {
			if(u.getUserId().equals(userId)) return u;
		}
		
		try{
			FileInputStream fileIn = new FileInputStream(database + user.getUserId());
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
			if(user.equals(userId)) {
				FileOutputStream fileOut = new FileOutputStream(database + user.getUserId());
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(user);
				out.close();
				fileOut.close();
			} else {
				System.out.println("There is no user to write to storage.");
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
			File file = new File(database + userId + ".ser");
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
		File dir = new File(database);
		for(File child : dir.listFiles()) {
			try{
				FileInputStream fileIn = new FileInputStream(child.getAbsoluteFile());
				ObjectInputStream in = new ObjectInputStream(fileIn);
				User user = (User)in.readObject();
				this.users.add(user);
				in.close();
				fileIn.close();
			} catch (IOException i) {
				System.out.println("Failed to read user from storage to memory.");
			} catch(ClassNotFoundException c) {
				System.out.println("Failed to read user due to a non-existence of class definition.");
			}
		}
	}

	@Override
	public void saveCurrentSession() {
		try{
			File file = new File(lastSessionLocation);
			if(!file.exists()) file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(IUser u : this.users) {
				bw.write(u.getUserId());
				this.writeUser(u.getUserId());
			}
		} catch (IOException i) {
			System.out.println("Could not save current session.");
		}
	}

	@Override
	public IUser loadPreviousUserSession(String userid) {
		File dir = new File(database);
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
			} catch (IOException i) {
				System.out.println("Failed to read user from storage to memory.");
			} catch(ClassNotFoundException c) {
				System.out.println("Failed to read user due to a non-existence of class definition.");
			} catch(Exception e) {
				System.out.println("Something went horribly wrong when I tried to load the previous session.");
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
		} catch (IOException i) {
			System.out.println("Could not save current session.");
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
}
