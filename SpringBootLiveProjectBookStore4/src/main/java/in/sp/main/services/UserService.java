package in.sp.main.services;

import java.util.List;

import in.sp.main.entities.User;

public interface UserService {
	public boolean AddStd(User u);
	public User LoginVerify(String role, String email, String password);
	public User GetUser(int id);
	public boolean UpdateUser(User u);
	public long CountUser(String status);
	List<User> GetUsers(String s);

}
