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
import pojo.Coordinates;
import pojo.Trips;

/**
 *
 * @author Ehab
 */
public class CoordinatesDAO extends AbstractDao<Coordinates> {

    Session session;

    public CoordinatesDAO(Session s) {
        super(Coordinates.class,s);
        session = s;
    }

    public ArrayList<Coordinates> getCoordinatesByTrip(Trips trip) {

        List coordinates = new ArrayList<Coordinates>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(Coordinates.class);
            // Add restriction.
            cr.add(Restrictions.eq("trips", trip));
            coordinates = cr.list();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<Coordinates>) coordinates;
    }

    public ArrayList<Coordinates> getCoordinatesByLongitude(float longitude) {

        List coordinates = new ArrayList<Coordinates>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(Coordinates.class);
            // Add restriction.
            cr.add(Restrictions.eq("longitude", longitude));

        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<Coordinates>) coordinates;
    }

    public ArrayList<Coordinates> getCoordinatesByLatitude(float latitude) {

        List coordinates = new ArrayList<Coordinates>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(Coordinates.class);
            // Add restriction.
            cr.add(Restrictions.eq("latitude", latitude));
            coordinates = cr.list();

        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<Coordinates>) coordinates;
    }

    public List<Coordinates> findAll() throws DataAccessLayerException {
        return super.findAll(Coordinates.class);
    }

    public Coordinates find(int id) throws DataAccessLayerException {
        return super.find(Coordinates.class, id);
    }

}
