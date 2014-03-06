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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class PhotoAdminModel implements IPhotoAdminModel {
	
	public static void main(String[] args) {
		String testUser = "TestUser";
		PhotoAdminModel model = new PhotoAdminModel("./data/");
		model.writeUser(testUser);
	}
	
	private String database;
	private String lastSessionLocation = "./data/lastSession.stat";
	private ArrayList<IUser> users;
	
	public PhotoAdminModel(String database) {
		this.database = database;
		this.users = new ArrayList<IUser>();
	}
	
	@Override
	public IUser addUser(String userId, String userName) {
		IUser create=new User(userId, userName);
		this.users.add(create);
		return null;
	}

	@Override
	public IUser readUser(String userId) {
		int index = Collections.binarySearch(this.users, userId);
		if(index < 0) return null;
		IUser user = this.users.get(index);
		if(user.getUserId().equals(userId)) return user;
		try{
			FileInputStream fileIn = new FileInputStream(database + user.getUserId() + ".ser");
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
		try {
			int index = Collections.binarySearch(this.users, userId);
			if(index < 0) return;
			IUser user = this.users.get(index);
			if(user.equals(userId)) {
				FileOutputStream fileOut = new FileOutputStream(database + user.getUserId() + ".ser");
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
				IUser user = (IUser)in.readObject();
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

}
