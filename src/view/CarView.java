package view;

import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entities.Car;
import entities.Model;

import javax.swing.*;

public class CarView extends Layout {
    private JPanel container;
    private Car car;
    private JLabel fld_welcome;
    private JComboBox cbm_model;
    private JComboBox cmb_color;
    private JTextField fld_km;
    private JTextField fld_plate;
    private JButton btn_save;
    private JLabel lbl_model;
    private JLabel lbl_color;
    private JLabel lbl_km;
    private JLabel lbl_plate;
    private CarManager carManager;
    private ModelManager modelManager;

    public CarView(Car car) {
        this.car = car;
        this.carManager = new CarManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.guiInit(500, 500, "Car Panel");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.cmb_color.setModel(new DefaultComboBoxModel<>(Car.Color.values()));
        for (Model model : modelManager.findAll()) {
            this.cbm_model.addItem(model.getComboItem());
        }

        if(this.car.getId() !=0){
            ComboItem selectedItem = car.getModel().getComboItem();
            this.cbm_model.setSelectedItem(selectedItem);
            this.cmb_color.setSelectedItem(car.getColor());
            this.fld_km.setText(Integer.toString(car.getKm()));
            this.fld_plate.setText(car.getPlate());
        }

        btn_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{fld_km, fld_plate})) {
                Helper.showMessage("error");
            } else {
                boolean result = false;
                ComboItem selectedModel = (ComboItem) cbm_model.getSelectedItem();
                this.car.setModel_id(selectedModel.getKey());
                this.car.setColor((Car.Color) cmb_color.getSelectedItem());
                this.car.setKm(Integer.parseInt(fld_km.getText()));
                this.car.setPlate(fld_plate.getText());
                if (this.car.getId() != 0) {
                    result = carManager.update(this.car) != null;
                } else {
                    result = carManager.save(this.car);
                }
                if (result) {
                    Helper.showMessage("success");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

    }

}
