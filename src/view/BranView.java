package view;

import business.BrandManager;
import core.Helper;
import entities.Brand;

import javax.swing.*;


public class BranView extends Layout {

    private JPanel container;
    private JLabel lbl_brand;
    private JLabel lbl_brand_name;
    private JTextField fld_brand_name;
    private JButton btn_brand_save;
    private Brand brand;
    private BrandManager brandManager;

    public BranView(Brand brand) {
        this.add(container);
        this.guiInit(300, 200, "Brand Panel");
        this.brand = brand;
        this.brandManager = new BrandManager();
        if (brand != null) {
            fld_brand_name.setText(brand.getName());

        }
        btn_brand_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_brand_name)) {
                Helper.showMessage("error");
            } else {
               boolean result = true;
                if (this.brand == null) {
                    Brand saveBrand = new Brand(fld_brand_name.getText());
                     result = brandManager.save(saveBrand);
                }else{
                    this.brand.setName(fld_brand_name.getText());
                    brandManager.update(this.brand);

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
