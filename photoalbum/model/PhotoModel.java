package model;

public class PhotoModel implements IPhotoModel {
	private IPhotoAdminModel adminModel;
	public PhotoModel(IPhotoAdminModel adminModel) {
		this.adminModel = adminModel;
	}
	
	/* (non-Javadoc)
	 * @see model.IPhotoModel#getUser(java.lang.String)
	 * 
	 * Deserializes a user object.
	 */
	@Override
	public IUser getUser(String id) {
		return this.adminModel.readUser(id);
	}
}
