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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.Address;
import pojo.ServiceProvider;
import pojo.ServiceProviderCalendar;
import pojo.ServiceProviderPhone;
import pojo.ServiceProviderServices;

/**
 *
 * @author omima
 */
public class ServiceProviderDAO extends AbstractDao<ServiceProvider> {

    SessionFactory sessionFactory;

    public ServiceProviderDAO(SessionFactory sessionFactory) {
        super(ServiceProvider.class);
        this.sessionFactory = sessionFactory;
    }

    public ArrayList<ServiceProvider> findServiceProviderData(ServiceProvider sp) {
        Session session = sessionFactory.openSession();
        Criteria crit = session.createCriteria(ServiceProvider.class);

        List results = new ArrayList<ServiceProvider>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProvider>) results;
    }

    public ArrayList<ServiceProvider> findServiceProviderbranch(ServiceProvider sp) {
        Session session = sessionFactory.openSession();

        List branch = new ArrayList<ServiceProvider>();
        Query quary = (Query) session.createQuery("select sp from ServiceProvider s , ServiceProvider sp where sp.serviceProvider = :id");
        quary.setInteger("id", sp.getId());

        sp = (ServiceProvider) quary.uniqueResult();

        branch = quary.list();

        session.close();
        return (ArrayList<ServiceProvider>) branch;
    }

    public ArrayList<ServiceProviderPhone> findServiceProviderPhone(ServiceProvider sp) {
        Session session = sessionFactory.openSession();
        Criteria crit = session.createCriteria(ServiceProviderPhone.class);

        List results = new ArrayList<ServiceProviderPhone>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderPhone>) results;
    }

    public ArrayList<ServiceProviderCalendar> findServiceProviderCalender(ServiceProvider sp) {
        Session session = sessionFactory.openSession();
        Criteria crit = session.createCriteria(ServiceProviderCalendar.class);

        List results = new ArrayList<ServiceProviderCalendar>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderCalendar>) results;
    }

    public ArrayList<Address> findServiceProviderAddress(ServiceProvider sp) {
        Session session = sessionFactory.openSession();

        Criteria crit = session.createCriteria(Address.class);

        List results = new ArrayList<Address>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<Address>) results;
    }

    public ArrayList<ServiceProviderServices> findServiceProviderServices(ServiceProvider sp) {
        Session session = sessionFactory.openSession();

        Criteria crit = session.createCriteria(ServiceProviderServices.class);

        List results = new ArrayList<ServiceProviderServices>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderServices>) results;
    }

}
