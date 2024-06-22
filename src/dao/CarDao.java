package dao;

import core.Db;
import entities.Car;
import entities.Model;

import java.sql.*;
import java.util.ArrayList;

public class CarDao {
    Connection conn;
    private final BrandDao brandDao;
    private final ModelDao modelDao;

    public CarDao(){
        this.conn = Db.getInstance();
        this.brandDao = new BrandDao();
        this.modelDao = new ModelDao();
    }

    public Car getById(int id){
        Car obj = null;
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM car WHERE car_id = " + id);
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }



    public ArrayList<Car> findAll(){
        return this.selectByQuery("SELECT * FROM car ORDER BY car_id ASC");
    }

    public ArrayList<Car> selectByQuery(String query){
        ArrayList<Car> cars = new ArrayList<>();
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                cars.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car match(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("car_id"));
        car.setModel_id(rs.getInt("car_model_id"));
        car.setColor(Car.Color.valueOf(rs.getString("car_color")));
        car.setKm(rs.getInt("car_km"));
        car.setPlate(rs.getString("car_plate"));

        Model model = this.modelDao.getById(car.getModel_id());
        car.setModel(model);

        return car;
    }

    public Car update(Car obj){
        String query = "UPDATE car SET car_model_id = ?, car_color = ?, car_km = ?, car_plate = ? WHERE car_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, obj.getModel_id());
            st.setString(2, obj.getColor().toString());
            st.setInt(3, obj.getKm());
            st.setString(4, obj.getPlate());
            st.setInt(5, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean save (Car obj) {
        String query = "INSERT INTO car (car_model_id, car_color, car_km, car_plate) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, obj.getModel_id());
            st.setString(2, obj.getColor().toString());
            st.setInt(3, obj.getKm());
            st.setString(4, obj.getPlate());
            return st.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id){
        try {
            Statement st = this.conn.createStatement();
            return st.executeUpdate("DELETE FROM car WHERE car_id = " + id) != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Car> getByModelId(int id){
        return this.selectByQuery("SELECT * FROM car WHERE car_model_id = " + id);
    }

}
