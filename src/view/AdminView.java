package view;

import business.BookManager;
import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entities.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;

public class AdminView extends Layout {

    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scl_model;
    private JTable tbl_model;
    private JComboBox cmb_s_brand;
    private JComboBox cmb_s_type;
    private JComboBox cmb_s_fuel;
    private JComboBox cmb_s_gear;
    private JPanel pnl_search;
    private JLabel fld_brand_search;
    private JButton btn_search;
    private JButton btn_clear;
    private JPanel pnl_car;
    private JScrollPane scrl_car;
    private JTable tbl_car;
    private JPanel pnl_book;
    private JScrollPane scrl_book;
    private JPanel pnl_booking_search;
    private JComboBox cmb_booking_type;
    private JComboBox cmb_booking_fuel;
    private JComboBox cmb_booking_gear;
    private JFormattedTextField fld_fnsh_date;
    private JFormattedTextField fld_start_date;
    private JButton btn_s_book;
    private JLabel lbl_strt_date;
    private JLabel lbl_fns_date;
    private JLabel lbl_gear;
    private JLabel lbl_fuel;
    private JLabel lbl_type;
    private JTable tbl_book;
    private JButton btn_bs_clear;
    private JPanel pnl_books;
    private JScrollPane scrl_books;
    private JTable tbl_books;
    private JComboBox cmb_s_plate;
    private JLabel lbl_s_plate;
    private JButton btn_s_plate;
    private User user;
    private DefaultTableModel tmdl_brand;
    private DefaultTableModel tmdl_model;
    private DefaultTableModel tmdl_car;
    private DefaultTableModel tmdl_book;
    private DefaultTableModel tmdl_books;
    private BrandManager brandManager;
    private ModelManager modelManager;
    private BookManager bookManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;
    private JPopupMenu carMenu;
    private JPopupMenu bookMenu;
    private JPopupMenu booksMenu;
    private Object[] col_model;
    private Object[] col_car;
    private Object[] col_book;
    private CarManager carManager;

    public AdminView(User user) {
        this.add(container);
        this.brandManager = new BrandManager();
        this.tmdl_brand = new DefaultTableModel();
        this.tmdl_model = new DefaultTableModel();
        this.tmdl_car = new DefaultTableModel();
        this.tmdl_book = new DefaultTableModel();
        this.tmdl_books = new DefaultTableModel();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.bookManager = new BookManager();
        this.col_model = new Object[7];
        this.col_car = new Object[10];
        this.col_book = new Object[11];
        this.guiInit(1000, 500, "Admin Panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.user = user;
        if(user==null){
            dispose();
        }

        lbl_welcome.setText("Hosgeldiniz : "+user.getUsername());

        setBrandTable();
        selectBrand();
        setCarTable();
        selectCar();
        setModelTable(null);
        selectModel();
        setBookTable(null);
        setBooksTable(null);
        selectBooking();
        selectBooks();
        loadModelFilter();
        loadBookingFilter();
        loadBookFilter();


    }

    public void setBrandTable(){
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brands = brandManager.getForTable(col_brand.length);
        createTable(tmdl_brand, tbl_brand, col_brand, brands);
    }

    public void setModelTable(ArrayList<Object[]> models){
        Object[] col_model = {"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt", "Vites"};
        if(models == null){
            models = modelManager.getForTable(col_model.length, this.modelManager.findAll());
        }
        createTable(tmdl_model, tbl_model, col_model, models);
    }

    public void setBookTable(ArrayList<Object[]> books){
        Object[] col_book = {"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl", "Tip", "Yakıt Türü", "Vites"};
        createTable(tmdl_book, tbl_book, col_book, books);
    }

    public void setCarTable(){
        Object[] col_car = {"ID" ,"Marka", "Model", "Plaka", "Renk", "Km", "Yıl", "Tip", "Yakıt Türü", "Vites"};
        ArrayList<Object[]> cars = this.carManager.getForTable(col_car.length, this.carManager.findAll());
        createTable(tmdl_car, tbl_car, col_car, cars);
    }

    public void setBooksTable(ArrayList<Object[]> books){
        Object[] col_books = {"ID", "Plaka", "Marka", "Model", "İsim Soyisim", "Telefon No", "Mail", "Kimlik No", "Başlangıç Tarihi", "Bitiş Tarihi", "Fiyat"};
        if(books == null){
            books = bookManager.getForTable(col_books.length, bookManager.findAll());
        }
        createTable(tmdl_books, tbl_books, col_books, books);
    }

    public void selectBooks(){
        tableRowSelected(tbl_books);

        this.booksMenu = new JPopupMenu();

        booksMenu.add("Sil").addActionListener(e->{
            int selectedRow = Helper.getTableSelectedRow(tbl_books,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                if (Helper.confirm("sure")) {
                    if (bookManager.delete(selectedRow)){
                        Helper.showMessage("success");
                    } else {
                        Helper.showMessage("error");
                    }
                    setBooksTable(null);
                }
            }
        });
        this.tbl_books.setComponentPopupMenu(booksMenu);

        btn_s_plate.addActionListener(e->{
            ArrayList<Book> books = bookManager.getBookByPlate(
                    String.valueOf(((ComboItem) cmb_s_plate.getSelectedItem()))
            );
            ArrayList<Object[]> bookRows = bookManager.getForTable(col_book.length, books);
            setBooksTable(bookRows);
        });


    }

    public void selectBooking(){

        tableRowSelected(tbl_book);

        this.bookMenu = new JPopupMenu();

        bookMenu.add("Rezervasyon Yap").addActionListener(e ->{
            int selectedRow = Helper.getTableSelectedRow(tbl_book,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                BookView bookView = new BookView(carManager.getById(selectedRow),fld_start_date.getText(),fld_fnsh_date.getText());
                bookView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setBookTable(null);
                        setBooksTable(null);
                    }
                });
            }
        });



        btn_s_book.addActionListener(e->{
            ArrayList<Car> cars = carManager.searchForBooking(
                    fld_start_date.getText(),
                    fld_fnsh_date.getText(),
                    (Model.Type) cmb_booking_type.getSelectedItem(),
                    (Model.Gear) cmb_booking_gear.getSelectedItem(),
                    (Model.Fuel) cmb_booking_fuel.getSelectedItem()
            );

            ArrayList<Object[]> carRows = carManager.getForTable(col_car.length, cars);
            setBookTable(carRows);
        });

        btn_bs_clear.addActionListener(e->{
            loadBookingFilter();
        });

        this.tbl_book.setComponentPopupMenu(bookMenu);
    }

    public void selectCar(){
        tableRowSelected(tbl_car);

        this.carMenu = new JPopupMenu();

        carMenu.add("Yeni").addActionListener(e ->{
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setCarTable();
                    setBooksTable(null);
                }
            });
        });

        carMenu.add("Güncelle").addActionListener(e->{
            int selectedRow = Helper.getTableSelectedRow(tbl_car,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                CarView carView = new CarView(carManager.getById(selectedRow));
                carView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setCarTable();
                        setBooksTable(null);
                    }
                });
            }

        });

        carMenu.add("Sil").addActionListener(e->{
            int selectedRow = Helper.getTableSelectedRow(tbl_car,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                if (Helper.confirm("sure")) {
                    if (carManager.delete(selectedRow)){
                        Helper.showMessage("success");
                    } else {
                        Helper.showMessage("error");
                    }
                    setCarTable();
                    setBooksTable(null);
                }
            }
        });

        this.tbl_car.setComponentPopupMenu(carMenu);
    }

    public void selectBrand(){

        tableRowSelected(tbl_brand);

        this.brandMenu = new JPopupMenu();
        brandMenu.add("Yeni").addActionListener(e ->{
            BranView branView = new BranView(null);
            branView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setBrandTable();
                    loadModelFilterBrand();
                    setCarTable();
                    setBooksTable(null);
                }
            });
        });


        brandMenu.add("Güncelle").addActionListener(e->{
            int selectedRow = Helper.getTableSelectedRow(tbl_brand,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                Brand brand = brandManager.getById(selectedRow);
                BranView branView = new BranView(brand);
                branView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setBrandTable();
                        setModelTable(null);
                        loadModelFilterBrand();
                        setCarTable();
                        setBooksTable(null);
                    }
                });
            }

        });

        brandMenu.add("Sil").addActionListener(e->{
            int selectedRow = Helper.getTableSelectedRow(tbl_brand,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                if (Helper.confirm("sure")) {
                    if (brandManager.delete(selectedRow)){
                        Helper.showMessage("success");
                    } else {
                        Helper.showMessage("error");
                    }
                    setBrandTable();
                    setModelTable(null);
                    loadModelFilterBrand();
                    setCarTable();
                    setBooksTable(null);
                }
            }
        });

        this.tbl_brand.setComponentPopupMenu(brandMenu);

    }

    public void selectModel(){

        tableRowSelected(tbl_model);

        this.modelMenu = new JPopupMenu();

        modelMenu.add("Yeni").addActionListener(e ->{
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setModelTable(null);
                    setBooksTable(null);
                }
            });
        });

        modelMenu.add("Güncelle").addActionListener(e->{
            int selectedRow = Helper.getTableSelectedRow(tbl_model,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                ModelView modelView = new ModelView(modelManager.getById(selectedRow));
                modelView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setModelTable(null);
                        setCarTable();
                        setBooksTable(null);
                    }
                });
            }

        });

        modelMenu.add("Sil").addActionListener(e->{
            int selectedRow = Helper.getTableSelectedRow(tbl_model,0);
            if(selectedRow == -1){
                Helper.showMessage("error");
            }else{
                if (Helper.confirm("sure")) {
                    if (modelManager.delete(selectedRow)){
                        Helper.showMessage("success");
                    } else {
                        Helper.showMessage("error");
                    }
                    setModelTable(null);
                    setCarTable();
                    setBooksTable(null);
                }
            }
        });

        this.tbl_model.setComponentPopupMenu(modelMenu);

        btn_search.addActionListener(e->{
            ComboItem selectedBrand = (ComboItem) cmb_s_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null){
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> models = modelManager.getByFilter(
                    brandId,
                    (Model.Type) cmb_s_type.getSelectedItem(),
                    (Model.Fuel) cmb_s_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_gear.getSelectedItem()
            );
            ArrayList<Object[]> modelRows = modelManager.getForTable(col_model.length, models);
            setModelTable(modelRows);
        });

        btn_clear.addActionListener(e->{
            setModelTable(null);
            loadModelFilter();
        });

    }

    public void loadModelFilter(){
        cmb_s_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        cmb_s_type.setSelectedItem(null);
        cmb_s_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        cmb_s_fuel.setSelectedItem(null);
        cmb_s_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        cmb_s_gear.setSelectedItem(null);
        loadModelFilterBrand();
    }

    public void loadBookingFilter(){
        cmb_booking_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        cmb_booking_type.setSelectedItem(null);
        cmb_booking_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        cmb_booking_fuel.setSelectedItem(null);
        cmb_booking_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        cmb_booking_gear.setSelectedItem(null);
    }

    public void loadBookFilter(){
        cmb_s_plate.removeAllItems();
        for (Car car : carManager.findAll()) {
            cmb_s_plate.addItem(new ComboItem(car.getId(), car.getPlate()));
        }
        cmb_s_plate.setSelectedItem(null);
    }

    public void loadModelFilterBrand(){
        cmb_s_brand.removeAllItems();
        for (Brand brand : brandManager.findAll()) {
            cmb_s_brand.addItem(new ComboItem(brand.getId(), brand.getName()));
        }
        cmb_s_brand.setSelectedItem(null);
    }


    private void createUIComponents() throws ParseException {
        this.fld_start_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_start_date.setText("01/01/2024");
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_fnsh_date.setText("02/01/2024");
    }
}
