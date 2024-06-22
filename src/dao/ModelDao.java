package dao;

import core.Db;
import entities.Model;
import dao.BrandDao;

import java.sql.*;
import java.util.*;


public class ModelDao {
    private Connection conn;
    private final BrandDao brandDao = new BrandDao();

    public ModelDao(){
        this.conn = Db.getInstance();

    }

    public ArrayList<Model> findAll(){
        return this.selectByQuery("SELECT * FROM model ORDER BY model_id ASC");
    }

    public ArrayList<Model> getByBrandId(int id){
        return this.selectByQuery("SELECT * FROM model WHERE model_brand_id = " + id + " ORDER BY model_id ASC");
    }

    public ArrayList<Model> selectByQuery(String query){
        ArrayList<Model> models = new ArrayList<>();
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                models.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return models;
    }

public boolean save (Model obj) {
    String query = "INSERT INTO model (model_brand_id, model_name, model_type, model_year, model_fuel, model_gear) VALUES (?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement st = this.conn.prepareStatement(query);
        st.setInt(1, obj.getBrand_id());
        st.setString(2, obj.getName());
        st.setString(3, obj.getType().toString());
        st.setString(4, obj.getYear());
        st.setString(5, obj.getFuel().toString());
        st.setString(6, obj.getGear().toString());
        return st.executeUpdate() != -1;
    } catch (SQLException e) {
        e.printStackTrace();
        }
    return true;
    }

    public Model getById(int id){
        Model obj = null;
        String query = "SELECT * FROM model WHERE model_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Model update(Model obj){
        String query = "UPDATE model SET model_brand_id = ?, model_name = ?, model_type = ?, model_year = ?, model_fuel = ?, model_gear = ? WHERE model_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, obj.getBrand_id());
            st.setString(2, obj.getName());
            st.setString(3, obj.getType().toString());
            st.setString(4, obj.getYear());
            st.setString(5, obj.getFuel().toString());
            st.setString(6, obj.getGear().toString());
            st.setInt(7, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();}
        return obj;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM model WHERE model_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, id);
            return st.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public Model match(ResultSet rs) throws SQLException{
        Model obj = new Model();

        obj.setId(rs.getInt("model_id"));
        obj.setBrand(this.brandDao.getById(rs.getInt("model_brand_id")));
        obj.setBrand_id(rs.getInt("model_brand_id"));
        obj.setName(rs.getString("model_name"));
        obj.setType(Model.Type.valueOf(rs.getString("model_type")));
        obj.setYear(rs.getString("model_year"));
        obj.setFuel(Model.Fuel.valueOf(rs.getString("model_fuel")));
        obj.setGear(Model.Gear.valueOf(rs.getString("model_gear")));

        return obj;
    }

}
