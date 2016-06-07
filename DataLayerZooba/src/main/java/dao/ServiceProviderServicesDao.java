/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import pojo.Service;
import pojo.ServiceProvider;
import pojo.ServiceProviderServices;

/**
 *
 * @author Mohammed
 */
public class ServiceProviderServicesDao extends AbstractDao<ServiceProviderServices> {

      Session session;

    public ServiceProviderServicesDao(Session s) {
        super(ServiceProviderServices.class);
        session = s;
    }

    public List<ServiceProviderServices> findAll() throws DataAccessLayerException {
        return super.findAll(ServiceProviderServices.class); //To change body of generated methods, choose Tools | Templates.
    }

    public ServiceProviderServices find(int id) throws DataAccessLayerException {
        return super.find(ServiceProviderServices.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServiceProviderServices> findByExample(ServiceProviderServices t) throws DataAccessLayerException {
        // return super.findByExample(t);
        List<ServiceProviderServices> objects = null;
        try {
            startOperation();
            session = HibernateFactory.openSession();

            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
            for (ServiceProviderServices object : objects) {
                Hibernate.initialize(object.getServiceProvider());
                Hibernate.initialize(object.getService());
                
            }
//            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }

    
    public ServiceProviderServices getById(int id) {
        

        ServiceProviderServices s = (ServiceProviderServices) session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.id = :id").setInteger("id", id).uniqueResult();
        
        return s;
    }

    public List<ServiceProviderServices> getByStartingHour(Date startingHour) {
        

        List<ServiceProviderServices> list = session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.startingHour = :startingHour").setDate("startingHour", startingHour).list();
        
        return list;
    }

    public List<ServiceProviderServices> getByEndingHour(Date endingHour) {
        

        List<ServiceProviderServices> list = session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.endingHour = :endingHour").setDate("endingHour", endingHour).list();
        
        return list;
    }

    public List<ServiceProviderServices> getByService(Service service) {
        

        List<ServiceProviderServices> list =  session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.service.id = :id")
                .setInteger("id", service.getId()).list();
        
        return list;
    }

    public List<ServiceProviderServices> getByServiceProvider(ServiceProvider serviceProvider) {
        

        List<ServiceProviderServices> list =  session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.serviceProvider.id = :id")
                .setInteger("id", serviceProvider.getId()).list();
        
        return list;
    }
}
