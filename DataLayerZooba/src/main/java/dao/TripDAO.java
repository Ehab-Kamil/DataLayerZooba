/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Trips;
import pojo.Vehicle;

/**
 *
 * @author Ehab
 */
public class TripDAO extends AbstractDao<Trips> {

    Session session;

    public TripDAO(Session s) {
        super(Trips.class);
        session = s;
    }

    public ArrayList<Trips> getTripsByIntialOdemeter(int intialOdemeter) {

        List trips = new ArrayList<Trips>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(Trips.class);

            // Add restriction.
            cr.add(Restrictions.eq("intialOdemeter", intialOdemeter));
            trips = cr.list();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<Trips>) trips;
    }

    public List<Trips> findAll() throws DataAccessLayerException {
        return super.findAll(Trips.class);
    }

    public Trips find(Long id) throws DataAccessLayerException {
        return super.find(Trips.class, id);
    }

    public ArrayList<Trips> getTripsByVehicle(Vehicle vehicle) {
        List trips = new ArrayList<Trips>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(Trips.class);
            // Add restriction.
            cr.add(Restrictions.eq("vehicle", vehicle));
            trips = cr.list();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<Trips>) trips;
    }

}
