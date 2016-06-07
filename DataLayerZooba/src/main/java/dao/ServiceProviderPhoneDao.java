/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import pojo.ServiceProvider;
import pojo.ServiceProviderPhone;

/**
 *
 * @author Mohammed
 */
public class ServiceProviderPhoneDao extends AbstractDao<ServiceProviderPhone> {
    
       Session session;

    public ServiceProviderPhoneDao(Session s) {
        super(ServiceProviderPhone.class);
        session = s;
    }

    public List<ServiceProviderPhone> findAll() throws DataAccessLayerException {
        return super.findAll(ServiceProviderPhone.class); //To change body of generated methods, choose Tools | Templates.
    }

    public ServiceProviderPhone find(Long id) throws DataAccessLayerException {
        return super.find(ServiceProviderPhone.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ServiceProviderPhone> findByExample(ServiceProviderPhone t) throws DataAccessLayerException {
        // return super.findByExample(t);
        List<ServiceProviderPhone> objects = null;
        try {
            startOperation();
            session = HibernateFactory.openSession();

            objects = session.createCriteria(t.getClass()).add(Example.create(t).excludeZeroes()).list();
            for (ServiceProviderPhone object : objects) {
                Hibernate.initialize(object.getServiceProvider());
                
            }
//            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }

   public ServiceProviderPhone getById(int id) {
      
        
        ServiceProviderPhone spp = (ServiceProviderPhone) session.createQuery("SELECT spp FROM ServiceProviderPhone spp WHERE spp.id = :id").setInteger("id", id).uniqueResult();
      
        return spp;
    }
    
    public ServiceProviderPhone getByPhone(String phone) {
      
        
        ServiceProviderPhone spp = (ServiceProviderPhone) session.createQuery("SELECT spp FROM ServiceProviderPhone spp WHERE spp.phone = :phone").setString("phone", phone).uniqueResult();
      
        return spp;
    }
    
    public List<ServiceProviderPhone> getByServiceProvider(ServiceProvider serviceProvider) {
      
        
        List<ServiceProviderPhone> list = session.createQuery("SELECT spp FROM ServiceProviderPhone spp ,ServiceProvider sp WHERE spp.serviceProvider.id = :id ").setInteger("id", serviceProvider.getId()).list();
      
        return list;
    }
    
}
