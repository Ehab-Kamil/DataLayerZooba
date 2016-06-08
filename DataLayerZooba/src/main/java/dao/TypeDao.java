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
import pojo.MeasuringUnit;
import pojo.Service;
import pojo.Type;

/**
 *
 * @author Mohammed
 */
public class TypeDao extends AbstractDao<Type> {
    
    Session session;

    public TypeDao(Session s) {
        super(Type.class,s);
        session = s;
    }

    public List<Type> findAll() throws DataAccessLayerException {
        return super.findAll(Type.class); //To change body of generated methods, choose Tools | Templates.
    }

    public Type find(int id) throws DataAccessLayerException {
        return super.find(Type.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Type> findByExample(Type t) throws DataAccessLayerException {
        // return super.findByExample(t);
        List<Type> objects = null;
        try {
            startOperation();
            session = HibernateFactory.openSession();

            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
            for (Type object : objects) {
                Hibernate.initialize(object.getMeasuringUnit());
                Hibernate.initialize(object.getTrackingDatas());
                Hibernate.initialize(object.getService());
            }
//            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }

    public Type getById(int id) {
        
        
        Type t = (Type) session.createQuery("SELECT t FROM Type t WHERE t.id = :id").setInteger("id", id).uniqueResult();
        
        return t;
    }
    
    public List<Type> getByMeasuringUnit(MeasuringUnit measuringUnit) {
        
        
        List<Type> list = session.createQuery("SELECT t FROM Type t WHERE t.measuringUnit.id = :id").setInteger("id", measuringUnit.getId()).list();
        
        return list;
    }
    
    public List<Type> getByService(Service service) {
        
        
        List<Type> list = session.createQuery("SELECT t FROM Type t WHERE t.service = :id").setInteger("id", service.getId()).list();
        
        return list;
    }
    
}
