/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.ServiceProvider;
import pojo.ServiceProviderPhone;

/**
 *
 * @author Mohammed
 */
public class ServiceProviderPhoneDao extends AbstractDao<ServiceProviderPhone> {
    
    private final SessionFactory factory;
    
    public ServiceProviderPhoneDao(SessionFactory factory) {
        super(ServiceProviderPhone.class);
        this.factory = factory;
    }

    public ServiceProviderPhone getById(int id) {
        Session session = factory.openSession();
        
        ServiceProviderPhone spp = (ServiceProviderPhone) session.createQuery("SELECT spp FROM ServiceProviderPhone spp WHERE spp.id = :id").setInteger("id", id).uniqueResult();
        session.close();
        return spp;
    }
    
    public ServiceProviderPhone getByPhone(String phone) {
        Session session = factory.openSession();
        
        ServiceProviderPhone spp = (ServiceProviderPhone) session.createQuery("SELECT spp FROM ServiceProviderPhone spp WHERE spp.phone = :phone").setString("phone", phone).uniqueResult();
        session.close();
        return spp;
    }
    
    public List<ServiceProviderPhone> getByServiceProvider(ServiceProvider serviceProvider) {
        Session session = factory.openSession();
        
        List<ServiceProviderPhone> list = session.createQuery("SELECT spp FROM ServiceProviderPhone spp ,ServiceProvider sp WHERE spp.serviceProvider.id = :id ").setInteger("id", serviceProvider.getId()).list();
        session.close();
        return list;
    }
    
}
