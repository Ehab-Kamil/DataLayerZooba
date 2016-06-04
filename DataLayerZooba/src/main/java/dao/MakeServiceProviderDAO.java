/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.ServiceProvider;
import pojo.Make;

/**
 *
 * @author omima
 */
public class MakeServiceProviderDAO {

    SessionFactory sessionFactory;

    public MakeServiceProviderDAO(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public ArrayList<ServiceProvider> findMakeServiceProvider(Make make) {

        Session session = sessionFactory.openSession();

        Criteria crit = session.createCriteria(Make.class);

        List results = new ArrayList<>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProvider>) results;
    }

    public ArrayList<Make> findServiceProviderMake(ServiceProvider sp) {

        Session session = sessionFactory.openSession();

        Criteria crit = session.createCriteria(ServiceProvider.class);

        List results = new ArrayList<Make>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<Make>) results;
    }

}
