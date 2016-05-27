/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.CarFeatures;
import pojo.ModelFeaturesValues;
import pojo.VehicleModel;

/**
 *
 * @author Mohammed
 */
public class ModelFeaturesValuesDao extends AbstractDao<ModelFeaturesValues> {
    
    private final SessionFactory factory;
    
    public ModelFeaturesValuesDao(SessionFactory factory) {
        super(ModelFeaturesValues.class);
        this.factory = factory;
    }
    
    public ModelFeaturesValues getById(int id) {
        Session session = factory.openSession();
        
        ModelFeaturesValues m = (ModelFeaturesValues) session.createQuery("SELECT m FROM ModelFeaturesValues m WHERE m.id = :id").setInteger("id", id).uniqueResult();
        session.close();
        return m;
    }
    
    public ModelFeaturesValues getByValue(String value) {
        Session session = factory.openSession();
        
        ModelFeaturesValues m = (ModelFeaturesValues) session.createQuery("SELECT m FROM ModelFeaturesValues m WHERE m.value = :value").setString("value", value).uniqueResult();
        session.close();
        return m;
    }
    
    public List<ModelFeaturesValues> getByCarFeatures(CarFeatures carFeatures) {
        Session session = factory.openSession();
        
        List<ModelFeaturesValues> list = session.createQuery("SELECT m FROM ModelFeaturesValues m WHERE m.carFeatures.id = :id").setInteger("id", carFeatures.getId()).list();
        session.close();
        return list;
    }
    
    public List<ModelFeaturesValues> getByVehicleModel(VehicleModel vehicleModel) {
        Session session = factory.openSession();
        
        List<ModelFeaturesValues> list = session.createQuery("SELECT m FROM VehicleModel v join v.modelFeaturesValueses m WHERE v.id = :id").setInteger("id", vehicleModel.getId()).list();
        session.close();
        return list;
    }
    
}
