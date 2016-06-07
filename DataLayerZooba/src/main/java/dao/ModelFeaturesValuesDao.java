/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import pojo.CarFeatures;
import pojo.ModelFeaturesValues;
import pojo.VehicleModel;

/**
 *
 * @author Mohammed
 */
public class ModelFeaturesValuesDao extends AbstractDao<ModelFeaturesValues> {
    
      Session session;

    public ModelFeaturesValuesDao(Session s) {
        super(ModelFeaturesValues.class);
        session = s;
    }

    public List<ModelFeaturesValues> findAll() throws DataAccessLayerException {
        return super.findAll(ModelFeaturesValues.class); //To change body of generated methods, choose Tools | Templates.
    }

    public ModelFeaturesValues find(int id) throws DataAccessLayerException {
        return super.find(ModelFeaturesValues.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ModelFeaturesValues> findByExample(ModelFeaturesValues t) throws DataAccessLayerException {
        // return super.findByExample(t);
        List<ModelFeaturesValues> objects = null;
        try {
            startOperation();
            session = HibernateFactory.openSession();

            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
            for (ModelFeaturesValues object : objects) {
                Hibernate.initialize(object.getCarFeatures());
                Hibernate.initialize(object.getVehicleModels());
                
            }
//            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }

    
    
    public ModelFeaturesValues getById(int id) {
       
        
        ModelFeaturesValues m = (ModelFeaturesValues) session.createQuery("SELECT m FROM ModelFeaturesValues m WHERE m.id = :id").setInteger("id", id).uniqueResult();
       
        return m;
    }
    
    public ModelFeaturesValues getByValue(String value) {
       
        
        ModelFeaturesValues m = (ModelFeaturesValues) session.createQuery("SELECT m FROM ModelFeaturesValues m WHERE m.value = :value").setString("value", value).uniqueResult();
       
        return m;
    }
    
    public List<ModelFeaturesValues> getByCarFeatures(CarFeatures carFeatures) {
       
        
        List<ModelFeaturesValues> list = session.createQuery("SELECT m FROM ModelFeaturesValues m WHERE m.carFeatures.id = :id").setInteger("id", carFeatures.getId()).list();
        
        return list;
    }
    
    public List<ModelFeaturesValues> getByVehicleModel(VehicleModel vehicleModel) {
        
        
        List<ModelFeaturesValues> list = session.createQuery("SELECT m FROM VehicleModel v join v.modelFeaturesValueses m WHERE v.id = :id").setInteger("id", vehicleModel.getId()).list();
        
        return list;
    }
    
}
