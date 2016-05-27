/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.Service;
import pojo.ServiceProvider;
import pojo.ServiceProviderServices;

/**
 *
 * @author Mohammed
 */
public class ServiceProviderServicesDao extends AbstractDao<ServiceProviderServices> {

    private final SessionFactory factory;

    public ServiceProviderServicesDao(SessionFactory factory) {
        super(ServiceProviderServices.class);
        this.factory = factory;
    }

    public ServiceProviderServices getById(int id) {
        Session session = factory.openSession();

        ServiceProviderServices s = (ServiceProviderServices) session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.id = :id").setInteger("id", id).uniqueResult();
        session.close();
        return s;
    }

    public List<ServiceProviderServices> getByStartingHour(Date startingHour) {
        Session session = factory.openSession();

        List<ServiceProviderServices> list = session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.startingHour = :startingHour").setDate("startingHour", startingHour).list();
        session.close();
        return list;
    }

    public List<ServiceProviderServices> getByEndingHour(Date endingHour) {
        Session session = factory.openSession();

        List<ServiceProviderServices> list = session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.endingHour = :endingHour").setDate("endingHour", endingHour).list();
        session.close();
        return list;
    }

    public List<ServiceProviderServices> getByService(Service service) {
        Session session = factory.openSession();

        List<ServiceProviderServices> list =  session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.service.id = :id")
                .setInteger("id", service.getId()).list();
        session.close();
        return list;
    }

    public List<ServiceProviderServices> getByServiceProvider(ServiceProvider serviceProvider) {
        Session session = factory.openSession();

        List<ServiceProviderServices> list =  session.createQuery("SELECT s FROM ServiceProviderServices s WHERE s.serviceProvider.id = :id")
                .setInteger("id", serviceProvider.getId()).list();
        session.close();
        return list;
    }
}
