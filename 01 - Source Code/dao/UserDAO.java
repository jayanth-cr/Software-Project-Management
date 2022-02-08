package dao;

import java.util.List;

import models.User;

public interface UserDAO {
	boolean save(User user);
	User validate(String email, String password);
	List<User> getAll();
	User get(int id);
	boolean changePassword(int userId, String password, String newPassword);
}
