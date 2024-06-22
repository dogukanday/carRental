import business.UserManager;
import core.Db;
import core.Helper;
import view.AdminView;
import view.LoginView;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        Connection conn = Db.getInstance();
        //LoginView loginView = new LoginView();
        UserManager userManager = new UserManager();
        AdminView adminView = new AdminView(userManager.findByLogin("admin", "1234"));

    }
}
