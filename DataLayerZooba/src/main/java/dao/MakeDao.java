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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Make;

/**
 *
 * @author yoka
 */
public class MakeDao extends AbstractDao<Make> {

    Session session;

    public MakeDao() 
    {
        super(Make.class);
        session = HibernateFactory.openSession();
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

        return lst;
    }
}
