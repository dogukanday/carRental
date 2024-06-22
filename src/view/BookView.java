package view;

import business.BookManager;
import core.Helper;
import entities.Book;
import entities.Car;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookView extends Layout{

    private JPanel container;
    private JTextField fld_name;
    private JTextField fld_idno;
    private JTextField fld_mpno;
    private JTextField fld_mail;
    private JFormattedTextField fld_start_date;
    private JFormattedTextField fld_fnsh_date;
    private JTextField fld_price;
    private JTextArea area_note;
    private JButton btn_save;
    private JLabel lbl_welcome;
    private JLabel lbl_car;
    private JLabel lbl_name;
    private JLabel lbl_idno;
    private JLabel lbl_mpno;
    private JLabel lbl_mail;
    private JLabel lbl_strt_date;
    private JLabel lbl_fnsh_date;
    private JLabel lbl_price;
    private JLabel lbl_note;
    private Car selectedCar;
    private String srtr_date;
    private String fnsh_date;
    private BookManager bookManager;

    public BookView(Car selectedCar, String srtr_date, String fnsh_date) {
        this.selectedCar = selectedCar;
        this.srtr_date = srtr_date;
        this.fnsh_date = fnsh_date;
        this.bookManager = new BookManager();
        this.add(container);
        this.guiInit(300, 600, "Book Panel");

        lbl_car.setText("Araç : " + selectedCar.getPlate() + " / " + selectedCar.getModel().getBrand().getName() + " / " + selectedCar.getModel().getName());

        fld_start_date.setText(srtr_date);
        fld_fnsh_date.setText(fnsh_date);

        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[] fields = {fld_name, fld_start_date, fld_fnsh_date, fld_idno, fld_mpno, fld_mail, fld_price};

                if (Helper.isFieldListEmpty(fields)) {
                    Helper.showMessage("fill");
                } else {
                    Book book = new Book();
                    book.setbCase("done");
                    book.setCar_id(selectedCar.getId());
                    book.setCar(selectedCar);
                    book.setName(fld_name.getText());
                    book.setStrt_date(LocalDate.parse(srtr_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    book.setFnsh_date(LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    book.setIdno(fld_idno.getText());
                    book.setMpno(fld_mpno.getText());
                    book.setMail(fld_mail.getText());
                    book.setPrc(Integer.parseInt(fld_price.getText()));
                    book.setNote(area_note.getText());
                    boolean result = bookManager.save(book);
                    if (result) {
                        Helper.showMessage("success");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }


        });
    }
}
