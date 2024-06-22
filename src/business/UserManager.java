package business;

import dao.UserDao;
import entities.User;

public class UserManager {
    private final UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String username, String password) {
        return userDao.findByLogin(username, password);
    }
}
