/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.List;
import pojo.VehicleModel;

/**
 *
 * @author Ehab
 */
public class VehicleModelDao extends AbstractDao<VehicleModel>{
    
    public VehicleModelDao() {
        super(VehicleModel.class);
    }

    @Override
    public List<VehicleModel> findByExample(VehicleModel t) {
        return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.
    }

    public VehicleModel find(int id) {
        return super.find(VehicleModel.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(VehicleModel t) {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(VehicleModel t) {
        super.saveOrUpdate(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(VehicleModel t) {
        super.create(t); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
