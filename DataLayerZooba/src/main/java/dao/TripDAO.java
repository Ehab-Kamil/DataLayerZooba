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
import pojo.Trips;
import pojo.Vehicle;

/**
 *
 * @author Ehab
 */
public class TripDAO extends AbstractDao<Trips>{

    private static SessionFactory factory;

    public TripDAO() {
        super(Trips.class);
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public ArrayList<Trips> getTripsByIntialOdemeter(int intialOdemeter) {
        Session session = factory.openSession();
        Transaction tx = null;
        List trips = new ArrayList<Trips>();
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Trips.class);
            // Add restriction.
            cr.add(Restrictions.eq("intialOdemeter", intialOdemeter));
            trips = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return (ArrayList<Trips>) trips;
    }

    public ArrayList<Trips> getTripsByVehicle(Vehicle vehicle) {
        Session session = factory.openSession();
        Transaction tx = null;
        List trips = new ArrayList<Trips>();
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Trips.class);
            // Add restriction.
            cr.add(Restrictions.eq("vehicle", vehicle));
            trips = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return (ArrayList<Trips>) trips;
    }

}
