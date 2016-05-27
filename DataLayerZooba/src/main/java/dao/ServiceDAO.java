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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.Service;
import pojo.ServiceProviderServices;
import pojo.Type;

/**
 *
 * @author omima
 */
public class ServiceDAO extends AbstractDao<Service> {

    SessionFactory sessionFactory;

    public ServiceDAO(SessionFactory sessionFactory) {
        super(Service.class);
        this.sessionFactory = sessionFactory;
    }

    public ArrayList<Service> findServiceName(Service s) {
        Session session = sessionFactory.openSession();
        Criteria crit = session.createCriteria(Service.class);

        List results = new ArrayList<Service>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<Service>) results;
    }

    public ArrayList<Type> findServiceType(Service s) {
        Session session = sessionFactory.openSession();
        Criteria crit = session.createCriteria(Service.class);

        List results = new ArrayList<Type>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<Type>) results;
    }

    public ArrayList<ServiceProviderServices> findServiceProviderService(Service s) {
        Session session = sessionFactory.openSession();
        Criteria crit = session.createCriteria(Service.class);

        List results = new ArrayList<ServiceProviderServices>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderServices>) results;
    }
}
