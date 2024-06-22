package dao;

import core.Db;
import entities.User;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private Connection conn;

    public UserDao(){
        this.conn = Db.getInstance();
    }

    public ArrayList<User> findAll(){
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                users.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findByLogin(String username, String password){
        User obj = null;
        String query = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public User match(ResultSet rs) throws SQLException{
        User obj = new User();

        obj.setId(rs.getInt("user_id"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword(rs.getString("user_password"));
        obj.setRole(rs.getString("user_role"));

        return obj;
    }
}
