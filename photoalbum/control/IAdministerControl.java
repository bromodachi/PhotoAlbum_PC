package control;


/**
 * @author Mark Labrador
 *
 */
public interface IAdministerControl extends IErrorControl{
	public void listUsers();
	public void addUser(String id, String name);
	public void deleteUser(String id);
	public void login(String id);
}
