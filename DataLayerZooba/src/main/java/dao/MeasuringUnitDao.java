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

/**
 *
 * @author Mohammed
 */
public class MeasuringUnitDao extends AbstractDao<MeasuringUnit> {

    Session session;

    public MeasuringUnitDao(Session s) {
        super(MeasuringUnit.class);
        this.session = s;
    }

    public List<MeasuringUnit> findAll() throws DataAccessLayerException{
        return super.findAll(MeasuringUnit.class); //To change body of generated methods, choose Tools | Templates.
    }

    public MeasuringUnit find(Long id) throws DataAccessLayerException{
        return super.find(MeasuringUnit.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MeasuringUnit> findByExample(MeasuringUnit t) throws DataAccessLayerException {
        //    return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.

        List<MeasuringUnit> objects = null;
        try {

            startOperation();
            session = HibernateFactory.openSession();
            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
            for(MeasuringUnit m : objects){
                Hibernate.initialize(m.getTypes());
                
            }
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }


}
