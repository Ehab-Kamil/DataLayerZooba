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
import pojo.User;

/**
 *
 * @author Ehab
 */
public class UserDao extends AbstractDao<User> {

    Session session;

    public UserDao(Session s) {
        super(User.class);
        session = s;
    }

    public List<User> findAll() throws DataAccessLayerException {
        return super.findAll(User.class); //To change body of generated methods, choose Tools | Templates.
    }

    public User find(Long id) throws DataAccessLayerException {
        return super.find(User.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findByExample(User t) throws DataAccessLayerException {
        // return super.findByExample(t);
        List<User> objects = null;
        try {
            startOperation();
            session = HibernateFactory.openSession();

            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
            for (User object : objects) {
                Hibernate.initialize(object.getDevices());
                Hibernate.initialize(object.getVehicles());
                Hibernate.initialize(object.getVehicles_1());
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
