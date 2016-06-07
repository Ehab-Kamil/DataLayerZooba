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
import pojo.TrackingData;

/**
 *
 * @author Mohammed
 */

public class TrackingDataDao extends AbstractDao<TrackingData> {
        Session session;

    public TrackingDataDao(Session s) {
        super(TrackingData.class);
        session = s;
    }

    public List<TrackingData> findAll() throws DataAccessLayerException {
        return super.findAll(TrackingData.class); //To change body of generated methods, choose Tools | Templates.
    }

    public TrackingData find(Long id) throws DataAccessLayerException {
        return super.find(TrackingData.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrackingData> findByExample(TrackingData t) throws DataAccessLayerException {
        // return super.findByExample(t);
        List<TrackingData> objects = null;
        try {
            startOperation();
            session = HibernateFactory.openSession();

            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
            for (TrackingData object : objects) {
                Hibernate.initialize(object.getType());
                Hibernate.initialize(object.getVehicle());
                
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
