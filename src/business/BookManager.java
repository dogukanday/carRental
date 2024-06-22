package business;

import dao.BookDao;
import entities.Book;
import java.util.ArrayList;
import core.Helper;

public class BookManager {
    private final BookDao bookDao;

    public BookManager() {
        this.bookDao = new BookDao();
    }

    public ArrayList<Book> findAll() {
        return bookDao.findAll();
    }

    public boolean save(Book obj) {
        if (obj.getId() != 0) {
            Helper.showMessage("error");
        }
        return bookDao.save(obj);
    }


    public boolean update(Book obj) {
        if (obj.getId() == 0) {
            Helper.showMessage("error");
        }

        return bookDao.update(obj);
    }

    public boolean delete(int id) {
        if (id == 0) {
            Helper.showMessage("error");
        }
        return bookDao.delete(id);
   }

   public ArrayList<Object[]> getForTable(int size, ArrayList<Book> books) {
       ArrayList<Object[]> bookRow = new ArrayList<>();
       for (Book book : books) {
           Object[] row = new Object[size];
           int i = 0;
           row[i++] = book.getId();
           row[i++] = book.getCar().getPlate();
           row[i++] = book.getCar().getModel().getBrand().getName();
           row[i++] = book.getCar().getModel().getName();
           row[i++] = book.getName();
           row[i++] = book.getMpno();
           row[i++] = book.getMail();
           row[i++] = book.getIdno();
           row[i++] = book.getStrt_date();
           row[i++] = book.getFnsh_date();
           row[i++] = book.getPrc();
           bookRow.add(row);
       }
         return bookRow;
   }

    public ArrayList<Book> searchForTable(int carId) {
        String query = "SELECT * FROM public.book";
        ArrayList<String> whereList = new ArrayList<>();

        if (carId != 0) {
            whereList.add("book_car_id = " + carId);
        }

        String whereStr = String.join(" AND ", whereList);
        if (!whereStr.isEmpty()) {
            query += " WHERE " + whereStr;
        }

        return this.bookDao.selectByQuery(query);
    }

    public ArrayList<Book> getBookByPlate(String plate){
        return bookDao.getBookByPlate(plate);
    }


}
