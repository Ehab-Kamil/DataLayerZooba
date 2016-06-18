/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Make;
import pojo.User;

/**
 *
 * @author yoka
 */
public class MakeDao extends AbstractDao<Make> {

    public List<Make> findAll() {
        // return super.findAll(Make.class); //To change body of generated methods, choose Tools | Templates.
        List<Make> objects = null;
        try {
            Query query = session.createQuery("from Make");
            objects = query.list();
            for (Make object : objects) {
                Hibernate.initialize(object.getModels());
                Hibernate.initialize(object.getServiceProviders());
            }
        } catch (HibernateException e) {
            handleException(e);
        }
        return objects;
    }

    public MakeDao(Session s) {
        super(Make.class, s);
        session = s;
    }

    public Make find(int id) {
        return super.find(Make.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Make t) {
        Make make = getUniqueMakeByName(t.getName());
        if (make == null) {
            super.create(t); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public List<Make> getMakeByName(String name) {
        Criteria crt = session.createCriteria(Make.class).add(Restrictions.like("name", "%" + name + "%"));
        List<Make> lst = crt.list();

        return lst;

    }

    public List<Make> getMakeByNiceName(String niceName) {
        Criteria crt = session.createCriteria(Make.class).add(Restrictions.like("nicename", "%" + niceName + "%"));
        List<Make> lst = crt.list();

        return lst;

    }

    public List<Make> getMakebyModel(String modelparam) {
        Criteria crt = session.createCriteria(Make.class, "make").
                createAlias("make.models", "model")
                .add(Restrictions.like("model.name", "%" + modelparam + "%"));
        List<Make> lst = crt.list();

        return lst;
    }

    public List<Make> getMakebyServiceProvider(String serviceProvider) {
        Criteria crt = session.createCriteria(Make.class, "make").
                createAlias("make.serviceProviders", "serviceprovider")
                .add(Restrictions.like("serviceprovider.name", "%" + serviceProvider + "%"));
        List<Make> lst = crt.list();
        for(Make m:lst)
        {
            Hibernate.initialize(m.getName());
        }
        return lst;
    }

    public Make getUniqueMakeByName(String makeName) {
        Criteria crt = session.createCriteria(Make.class).add(Restrictions.eq("name", makeName));
        Make make = (Make) crt.uniqueResult();
        return make;
    }
}
