package business;

import core.Helper;
import dao.BrandDao;
import entities.Brand;
import entities.Model;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;
    private final ModelManager modelManager;

    public BrandManager() {
        this.brandDao = new BrandDao();
        this.modelManager = new ModelManager();
    }

    public ArrayList<Brand> findAll() {
        return brandDao.findAll();
    }

    public boolean save(Brand obj) {
        if (obj.getId() !=0){
            Helper.showMessage("error");
        }
        return brandDao.save(obj);
    }

    public Brand getById(int id){
        if (id == 0) {
            Helper.showMessage("error");
        }
        return brandDao.getById(id);
    }

    public Brand update(Brand obj){
        if (obj.getId() == 0){
            Helper.showMessage("error");
        }
        return brandDao.update(obj);
    }

    public boolean delete(int id){
        if (id == 0){
            Helper.showMessage("error");
        }
        for (Model model : modelManager.getByBrandId(id)) {
            modelManager.delete(model.getId());
        }
        return brandDao.delete(id);
    }

    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> brandRow = new ArrayList<>();
        for (Brand brand : this.findAll()) {
            Object[] row = new Object[size];
            int i = 0;
            row[i++] = brand.getId();
            row[i++] = brand.getName();
            brandRow.add(row);
        }
        return brandRow;
    }
}
