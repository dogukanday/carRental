package dao;

import core.Db;
import entities.Brand;

import java.sql.*;
import java.util.ArrayList;

public class BrandDao {
    private static Connection conn;

    public BrandDao(){
        this.conn = Db.getInstance();
    }

    public ArrayList<Brand> findAll(){
        ArrayList<Brand> brands = new ArrayList<>();
        String query = "SELECT * FROM brand ORDER BY brand_id ASC";
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                brands.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    public boolean save (Brand obj){
        String query = "INSERT INTO brand (brand_name) VALUES (?)";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setString(1, obj.getName());
            return st.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Brand getById(int id){
        Brand obj = null;
        String query = "SELECT * FROM brand WHERE brand_id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                obj = match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Brand update(Brand obj){
        String query = "UPDATE brand SET brand_name = ? WHERE brand_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM brand WHERE brand_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, id);
            return st.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Brand match(ResultSet rs) throws SQLException{
        Brand obj = new Brand();

        obj.setId(rs.getInt("brand_id"));
        obj.setName(rs.getString("brand_name"));

        return obj;
    }
}
