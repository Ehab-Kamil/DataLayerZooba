/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractDao;

import Exceptions.DataAccessLayerException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import pojo.User;

/**
 *
 * @author Ehab
 */
public abstract class AbstractDao<T> {

    private Class<T> entity;
    protected Session session;
    private Transaction tx;

    public AbstractDao(Class<T> t, Session s) {
        HibernateFactory.buildIfNeeded();
        entity = t;
        session = s;
    }

    public void create(T t) {
        try {
            session.saveOrUpdate(t);
        } catch (HibernateException e) {
            handleException(e);
        }
    }

    public void saveOrUpdate(T t) {
        try {
            session.update(t);
        } catch (HibernateException e) {
            handleException(e);
        }
    }

    public void delete(T t) {
        try {
            session.delete(t);
        } catch (HibernateException e) {
            handleException(e);
        }
    }

    protected T find(Class clazz, int id) {
        T obj = null;
        try {
            obj = (T) session.load(clazz, id);
        } catch (HibernateException e) {
            handleException(e);
        }
        return obj;
    }

    protected List<T> findAll(Class clazz) {
        List<T> objects = null;
        try {
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
        } catch (HibernateException e) {
            handleException(e);
        }
        return objects;
    }

    public List<T> findByExample(T t) {
        List<T> objects = null;
        try {
            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }

    protected void handleException(HibernateException e) throws DataAccessLayerException {
        HibernateFactory.rollback(tx);
        throw new DataAccessLayerException(e);
    }

    protected void startOperation() throws HibernateException {
        session = HibernateFactory.openSession();
        tx = session.beginTransaction();
    }

}
