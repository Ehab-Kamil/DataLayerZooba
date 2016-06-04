/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import pojo.Coordinates;
import pojo.Trips;

/**
 *
 * @author Ehab
 */
public class CoordinatesDAO extends AbstractDao<Coordinates> {

    private static SessionFactory factory;
    
    public CoordinatesDAO() {
        super(Coordinates.class);
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {            
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);            
    }
    }
    
    public ArrayList<Coordinates> getCoordinatesByTrip(Trips trip) {
        Session session = factory.openSession();
        Transaction tx = null;
        List coordinates = new ArrayList<Coordinates>();
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Coordinates.class);
            // Add restriction.
            cr.add(Restrictions.eq("trips", trip));
            coordinates = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();            
        } finally {
            session.close();            
        }
        
        return (ArrayList<Coordinates>) coordinates;
    }
    
    public ArrayList<Coordinates> getCoordinatesByLongitude(float longitude) {
        Session session = factory.openSession();
        Transaction tx = null;
        List coordinates = new ArrayList<Coordinates>();
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Coordinates.class);
            // Add restriction.
            cr.add(Restrictions.eq("longitude", longitude));
            coordinates = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();            
        } finally {
            session.close();            
        }
        
        return (ArrayList<Coordinates>) coordinates;
    }
    
    public ArrayList<Coordinates> getCoordinatesByLatitude(float latitude) {
        Session session = factory.openSession();
        Transaction tx = null;
        List coordinates = new ArrayList<Coordinates>();
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Coordinates.class);
            // Add restriction.
            cr.add(Restrictions.eq("latitude", latitude));
            coordinates = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();            
        } finally {
            session.close();            
        }
        
        return (ArrayList<Coordinates>) coordinates;
    }
    
}
