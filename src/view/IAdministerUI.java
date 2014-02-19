package view;

public interface IAdministerUI {
	public void listUsers();
	public void addUser(String id, String name);
	public void deleteUser(String id);
	public void login(String id);
}
