package view;

import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entities.Brand;
import entities.Model;

import javax.swing.*;

public class ModelView extends Layout{
    private JPanel container;
    private JLabel lbl_brand;
    private JLabel lbl_header;
    private JComboBox<ComboItem> cmb_brand;
    private JLabel lbl_model;
    private JTextField fld_model_name;
    private JTextField fld_year;
    private JLabel lbl_year;
    private JLabel lbl_type;
    private JComboBox<Model.Type> cmb_type;
    private JLabel lbl_fuel;
    private JComboBox<Model.Fuel> cmb_fuel;
    private JLabel lbl_gear;
    private JComboBox<Model.Gear> cmb_gear;
    private JButton btn_save;
    private Model model;
    private ModelManager modelManager;
    private BrandManager brandManager;

    public ModelView(Model model) {
        this.add(container);
        this.model = model;
        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.guiInit(300, 500, "Model Panel");

        for (Brand brand : brandManager.findAll()) {
            cmb_brand.addItem(new ComboItem(brand.getId(), brand.getName()));
        }
        cmb_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        cmb_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        cmb_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));

        if (model.getId() != 0) {
            fld_model_name.setText(model.getName());
            fld_year.setText(model.getYear());
            cmb_brand.setSelectedItem(new ComboItem(model.getBrand_id(), brandManager.getById(model.getBrand_id()).getName()));
            cmb_type.setSelectedItem(model.getType());
            cmb_fuel.setSelectedItem(model.getFuel());
            cmb_gear.setSelectedItem(model.getGear());
        }

        this.btn_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{fld_model_name,fld_year})) {
                Helper.showMessage("error");
            } else {
                boolean result = false;
                ComboItem selectedBrand = (ComboItem) cmb_brand.getSelectedItem();
                this.model.setYear(fld_year.getText());
                this.model.setName(fld_model_name.getText());
                this.model.setBrand_id(selectedBrand.getKey());
                this.model.setType((Model.Type) cmb_type.getSelectedItem());
                this.model.setFuel((Model.Fuel) cmb_fuel.getSelectedItem());
                this.model.setGear((Model.Gear) cmb_gear.getSelectedItem());

                if (this.model.getId() != 0) {
                    modelManager.update(this.model);
                    result = true;
                } else {
                    result = modelManager.save(this.model);
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
