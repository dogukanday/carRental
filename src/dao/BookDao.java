package dao;

import core.Db;
import entities.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDao {
    private static Connection conn;
    private final CarDao carDao = new CarDao();

    public BookDao(){
        this.conn = Db.getInstance();
    }



    public ArrayList<Book> findAll(){
        String query = "SELECT * FROM book ORDER BY book_id ASC";
        return this.selectByQuery(query);
    }


    public ArrayList<Book> selectByQuery(String query){
        ArrayList<Book> books = new ArrayList<>();
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                books.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book match(ResultSet rs) throws SQLException {
        Book obj = new Book();
        obj.setId(rs.getInt("book_id"));
        obj.setCar_id(rs.getInt("book_car_id"));
        obj.setName(rs.getString("book_name"));
        obj.setIdno(rs.getString("book_idno"));
        obj.setMpno(rs.getString("book_mpno"));
        obj.setMail(rs.getString("book_mail"));
        obj.setCar(carDao.getById(rs.getInt("book_car_id")));
        obj.setStrt_date(rs.getDate("book_strt_date").toLocalDate());
        obj.setFnsh_date(rs.getDate("book_fnsh_date").toLocalDate());
        obj.setbCase(rs.getString("book_case"));
        obj.setNote(rs.getString("book_note"));
        obj.setPrc(rs.getInt("book_prc"));
        return obj;
    }

    public boolean save (Book obj) {
        String query = "INSERT INTO book (book_car_id, book_name, book_idno, book_mpno, book_mail, book_strt_date, book_fnsh_date, book_case, book_note, book_prc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, obj.getCar_id());
            st.setString(2, obj.getName());
            st.setString(3, obj.getIdno());
            st.setString(4, obj.getMpno());
            st.setString(5, obj.getMail());
            st.setDate(6, Date.valueOf(obj.getStrt_date()));
            st.setDate(7, Date.valueOf(obj.getFnsh_date()));
            st.setString(8, obj.getbCase());
            st.setString(9, obj.getNote());
            st.setInt(10, obj.getPrc());
            return st.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update (Book obj) {
        String query = "UPDATE book SET book_car_id = ?, book_name = ?, book_idno = ?, book_mpno = ?, book_mail = ?, book_strt_date = ?, book_fnsh_date = ?, book_case = ?, book_note = ?, book_prc = ? WHERE book_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, obj.getCar_id());
            st.setString(2, obj.getName());
            st.setString(3, obj.getIdno());
            st.setString(4, obj.getMpno());
            st.setString(5, obj.getMail());
            st.setDate(6, Date.valueOf(obj.getStrt_date()));
            st.setDate(7, Date.valueOf(obj.getFnsh_date()));
            st.setString(8, obj.getbCase());
            st.setString(9, obj.getNote());
            st.setInt(10, obj.getPrc());
            st.setInt(11, obj.getId());
            return st.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete (int id) {
        String query = "DELETE FROM book WHERE book_id = ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, id);
            return st.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<Book> getBookByPlate(String plate){
        String query = "SELECT * FROM book WHERE book_car_id = (SELECT car_id FROM car WHERE car_plate = ?)";
        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setString(1, plate);
            ResultSet rs = st.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()){
                books.add(this.match(rs));
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
