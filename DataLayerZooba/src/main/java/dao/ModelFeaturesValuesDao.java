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
import pojo.ModelFeaturesValues;

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

    public ModelFeaturesValues find(Long id) throws DataAccessLayerException {
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
 
}
