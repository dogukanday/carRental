package business;

import dao.BookDao;
import dao.CarDao;
import entities.Book;
import entities.Car;
import entities.Model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CarManager {
    private final CarDao carDao;
    private final BookDao bookDao = new BookDao();

    public CarManager() {
        this.carDao = new CarDao();
    }


    public Car getById(int id) {
        return carDao.getById(id);
    }

    public ArrayList<Car> findAll() {
        return carDao.findAll();
    }

    public boolean save(Car obj) {
        return carDao.save(obj);
    }

    public Car update(Car obj) {
        return carDao.update(obj);
    }

    public boolean delete(int id) {
        return carDao.delete(id);
    }

    public ArrayList<Car> getByModelId(int id) {
        return carDao.getByModelId(id);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Car> cars) {
        ArrayList<Object[]> carRow = new ArrayList<>();
        for (Car car : cars) {
            Object[] row = new Object[size];
            int i = 0;
            row[i++] = car.getId();
            row[i++] = car.getModel().getBrand().getName();
            row[i++] = car.getModel().getName();
            row[i++] = car.getColor();
            row[i++] = car.getKm();
            row[i++] = car.getPlate();
            row[i++] = car.getModel().getYear();
            row[i++] = car.getModel().getType();
            row[i++] = car.getModel().getFuel();
            row[i++] = car.getModel().getGear();
            carRow.add(row);
        }
        return carRow;
    }

    public ArrayList<Car> searchForBooking(String strt_date, String fnsh_date, Model.Type type, Model.Gear gear, Model.Fuel fuel) {
        String query = "SELECT * FROM public.car as c LEFT JOIN public.model as m ON c.car_model_id = m.model_id";

        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> bookOrWhere = new ArrayList<>();


        strt_date = LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        fnsh_date = LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

        if (type != null) {where.add("m.model_type = '" + type.toString() + "'");}
        if (fuel != null) {where.add("m.model_fuel = '" + fuel.toString() + "'");}
        if (gear != null) {where.add("m.model_gear = '" + gear.toString() + "'");}

        String whereStr = String.join(" AND ", where);


        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }


        ArrayList<Car> searchedCarList = this.carDao.selectByQuery(query);


        bookOrWhere.add("'" + strt_date + "' BETWEEN book_strt_date AND book_fnsh_date");
        bookOrWhere.add("'" + fnsh_date + "' BETWEEN book_strt_date AND book_fnsh_date");
        bookOrWhere.add("book_strt_date BETWEEN '" + strt_date + "' AND '" + fnsh_date + "'");
        bookOrWhere.add("book_fnsh_date BETWEEN '" + strt_date + "' AND '" + fnsh_date + "'");

        String bookOrWhereStr = String.join(" AND ", bookOrWhere);
        String bookQuery = "SELECT * FROM public.book WHERE " + bookOrWhereStr;

        ArrayList<Book> bookList = this.bookDao.selectByQuery(bookQuery);
        ArrayList<Integer> busyCarId = new ArrayList<>();
        for (Book book : bookList) {
            busyCarId.add(book.getCar_id());
        }

        searchedCarList.removeIf(car -> busyCarId.contains(car.getId()));


        return searchedCarList;
    }

}
