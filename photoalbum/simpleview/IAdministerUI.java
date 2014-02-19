package simpleview;


public interface IAdministerUI extends IErrorUI{
	public void listUsers();
	public void addUser(String id, String name);
	public void deleteUser(String id);
	public void login(String id);
}
