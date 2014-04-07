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
			+ File.separator + "photos" + File.separator;
	private List<IUser> users;

	public PhotoModel() {
		this.users = new ArrayList<IUser>();
	}

	@Override
	public IUser addUser(String username, String fullname, String password) {
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

	@Override
	public IUser getUser(String username) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, username);
		
		if(index < 0) return null;
		IUser user = this.users.get(index);;
		if (user.getUsername().equals(username))
			return user;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.IPhotoAdminModel#writeUser(java.lang.String) Requires that the
	 * user be serialized and saved to storage (Flat-File)
	 */
	@Override
	public void writeUser(String username) {//TODO Remove this after testing since all session persistence is done by saveCurrentSession() by saving the entire list.
		try {
			FileOutputStream f_out = new FileOutputStream(usersdbfile);
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			for (IUser u : users) {
				obj_out.writeObject(u);
			}
			obj_out.close();
			f_out.close();
		} catch (IOException i) {
			System.out.println("Failed to write user to storage from memory."); //TODO Failed to read user from memory to be handled by the View.
		}

		//		int index = Collections.binarySearch(this.users, username);
		//		if (index < 0)
		//			return;
		//		IUser user = this.users.get(index);
		//		try {
		//			if (user.getUserId().equals(username)) {
		//				FileOutputStream fileOut = new FileOutputStream(
		//						"data" + File.pathSeparator + "users" + File.pathSeparator + "user.data");
		//				ObjectOutputStream out = new ObjectOutputStream(fileOut);
		//				out.writeObject(users);
		//				out.close();
		//				fileOut.close();
		//			}
		//		} catch (IOException i) {
		//			System.out.println("Failed to write user to storage from memory.");
		//		}
	}

	@Override
	public void sortUsers() {
		Collections.sort(this.users, new UserComparator());
	}

	@Override
	public void deleteUser(String username) {
		Collections.sort(this.users, new UserComparator());
		int index = Collections.binarySearch(this.users, username);
		IUser user = this.users.get(index);
		//TODO Change this to delete a user from the users list.  SaveSession will take care of the persistence.
		if (user.getUsername().equals(username)) {
			this.users.remove(index);
			File file = new File(usersdbfile + username + ".ser");
			file.delete();
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
			FileInputStream fileIn = new FileInputStream(usersdbfile);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			/*
			 * Generics causes a problem here because the type is not known
			 * until runtime. So checking instanceof only works against List<?>
			 * since it cannot verify object ? type until runtime.
			 */
			this.users = (List<IUser>) in.readObject();
			Collections.sort(this.users, new UserComparator());
			in.close();
			fileIn.close();
		} catch (Exception e) {
			System.out.println(e);//TODO LoadPreviousSession error handling to be done by the View.
		}
	}

	@Override
	public void saveCurrentSession() {
		try {
			//			for (int i = 0; i < this.users.size(); i++) {
			//				IUser u = this.users.get(i);
			//				this.writeUser(u.getUserId());
			//			}
			FileOutputStream f_out = new FileOutputStream(usersdbfile);
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			sortUsers();
			obj_out.writeObject(this.users);
			obj_out.close();
			f_out.close();
		} catch (Exception i) {
			System.out.println("Could not create a user."); //TODO SaveCurrentSession error handling to be done by the view.
		}
	}

	//	@Override
	//	public IUser loadPreviousUserSession(String username) {
	//		File dir = new File(usersdbpath);
	//		try {
	//			FileInputStream fileIn = new FileInputStream("data/users/user.data");
	//			ObjectInputStream in = new ObjectInputStream(fileIn);
	//			this.users = (List<IUser>) in.readObject();
	//			in.close();
	//			fileIn.close();
	//			//		return user;
	//		} catch (Exception e) {
	//			System.out.println();
	//		}
	//		return null;
	//	}

	//	@Override
	//	public void saveCurrentUserSession(IUser user) {
	//		try {
	//			File file = new File(user.getUserId());
	//			if (!file.exists())
	//				file.createNewFile();
	//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
	//			BufferedWriter bw = new BufferedWriter(fw);
	//			bw.write(user.getUserId());
	//			this.writeUser(user.getUserId());
	//		} catch (Exception e) {
	//			System.out.println();
	//		}
	//	}

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
