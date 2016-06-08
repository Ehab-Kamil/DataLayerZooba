/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Service;
import pojo.ServiceProviderServices;
import pojo.Type;

/**
 *
 * @author omima
 */
public class ServiceDAO extends AbstractDao<Service> {

    Session session;

    public ServiceDAO(Session session) {
        super(Service.class,session);
        this.session = session;
    }

     public List<Service> findAll() throws DataAccessLayerException {
        return super.findAll(Service.class); //To change body of generated methods, choose Tools | Templates.
    }

    public Service find(int id) throws DataAccessLayerException {
        return super.find(Service.class, id); //To change body of generated methods, choose Tools | Templates.
    }

 
    public ArrayList<Type> findServiceType(Service s) {
        
        
        Criteria crit = session.createCriteria(Service.class);
        crit.add(Restrictions.eq("Service", s));
        
        List results = new ArrayList<Type>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<Type>) results;
    }

    public ArrayList<ServiceProviderServices> findServiceProviderService(Service s) {

        Criteria crit = session.createCriteria(Service.class);

         crit.add(Restrictions.eq("Service", s));
        
        List results = new ArrayList<ServiceProviderServices>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderServices>) results;
    }
}
