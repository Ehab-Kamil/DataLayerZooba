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
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Days;
import pojo.ServiceProvider;
import pojo.ServiceProviderCalendar;

/**
 *
 * @author Ehab
 */
public class ServiceProviderCalendarDAO extends AbstractDao<ServiceProviderCalendar> {

    Session session;

    public ServiceProviderCalendarDAO(Session s) {
        super(ServiceProviderCalendar.class,s);
        session = s;
    }

    public ArrayList<ServiceProvider> getServiceProvidersByDay(Days day) {

        List serviceProviders = new ArrayList<ServiceProvider>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(ServiceProviderCalendar.class);
            // Add restriction.
            cr.add(Restrictions.eq("days", day));
            serviceProviders = cr.list();

        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<ServiceProvider>) serviceProviders;
    }

    public ArrayList<ServiceProvider> getServiceProvidersByStartingHours(Date startingHour) {

        List serviceProviders = new ArrayList<ServiceProvider>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(ServiceProviderCalendar.class);
            // Add restriction.
            cr.add(Restrictions.eq("startingHour", startingHour));
            serviceProviders = cr.list();

        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }

        return (ArrayList<ServiceProvider>) serviceProviders;
    }

    public ArrayList<ServiceProvider> getServiceProvidersByEndingHours(Date endingHour) {

        List serviceProviders = new ArrayList<ServiceProvider>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(ServiceProviderCalendar.class);
            // Add restriction.
            cr.add(Restrictions.eq("endingHour", endingHour));
            serviceProviders = cr.list();

        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<ServiceProvider>) serviceProviders;
    }

    public ArrayList<ServiceProvider> getServiceProviderByWorkingHours(Days day, Date startingHour, Date endingHour) {

        List serviceProviders = new ArrayList<ServiceProvider>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(ServiceProviderCalendar.class);
            // Add restriction.
            cr.add(Restrictions.eq("days", day));
            cr.add(Restrictions.eq("startingHour", startingHour));
            cr.add(Restrictions.eq("endingHour", endingHour));
            serviceProviders = cr.list();

        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<ServiceProvider>) serviceProviders;
    }

    public ArrayList<Days> getDaysForServiceProvider(ServiceProvider serviceProvider) {

        List days = new ArrayList<ServiceProvider>();
        try {
            startOperation();
            session = HibernateFactory.openSession();
            Criteria cr = session.createCriteria(ServiceProviderCalendar.class);
            // Add restriction.
            cr.add(Restrictions.eq("serviceProvider", serviceProvider));
            days = cr.list();

        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }

        return (ArrayList<Days>) days;
    }

    public List<ServiceProviderCalendar> findAll() throws DataAccessLayerException {
        return super.findAll(ServiceProviderCalendar.class);
    }

    public ServiceProviderCalendar find(int id) throws DataAccessLayerException {
        return super.find(ServiceProviderCalendar.class, id);
    }

}
