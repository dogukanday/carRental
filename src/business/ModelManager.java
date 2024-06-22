package business;

import core.Helper;
import dao.ModelDao;
import entities.Brand;
import entities.Model;

import java.util.ArrayList;

public class ModelManager {
    private final ModelDao modelDao;

    public ModelManager() {
        this.modelDao = new ModelDao();
    }

    public ArrayList<Model> findAll() {
        return modelDao.findAll();
    }

    public boolean save(Model obj) {
        if (obj.getId() !=0){
            Helper.showMessage("error");
        }
        return modelDao.save(obj);
    }

    public Model getById(int id){
        if (id == 0) {
            Helper.showMessage("error");
        }
        return modelDao.getById(id);
    }

    public Model update(Model obj){
        if (obj.getId() == 0){
            Helper.showMessage("error");
        }
        return modelDao.update(obj);
    }

    public boolean delete(int id){
        if (id == 0){
            Helper.showMessage("error");
        }
        return modelDao.delete(id);
    }

    public ArrayList<Model> getByBrandId(int id) {
        return modelDao.getByBrandId(id);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> models){
        ArrayList<Object[]> modelRow = new ArrayList<>();
        for (Model model : models) {
            Object[] row = new Object[size];
            int i = 0;
            row[i++] = model.getId();
            row[i++] = model.getBrand().getName();
            row[i++] = model.getName();
            row[i++] = model.getType();
            row[i++] = model.getYear();
            row[i++] = model.getFuel();
            row[i++] = model.getGear();
            modelRow.add(row);
        }
        return modelRow;
    }

    public ArrayList<Model> getByFilter(int brand_id, Model.Type type, Model.Fuel fuel, Model.Gear gear){
        String select = "SELECT * FROM public.model";
        ArrayList<String> whereList = new ArrayList<>(); //List of expressions that should be written with "where".

        if (brand_id != 0){
            whereList.add("model_brand_id = " + brand_id);
        }
        if (fuel != null){
            whereList.add("model_fuel ='" + fuel.toString() + "'");
        }
        if (gear != null){
            whereList.add("model_gear ='" + gear.toString() + "'");
        }
        if (type != null){
            whereList.add("model_type ='" + type.toString() + "'");
        }

        String whereStr = String.join(" AND ", whereList); //To combine expressions within the array.
        String query = select;
        if (whereStr.length() > 0){
            query = query + " WHERE " + whereStr;
        }
        return this.modelDao.selectByQuery(query);
    }

}
