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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

    Session session;

    public ServiceProviderDAO(Session session) {
        super(ServiceProvider.class,session);
        this.session = session;
    }

      public List<ServiceProvider> findAll() throws DataAccessLayerException {
        return super.findAll(ServiceProvider.class); //To change body of generated methods, choose Tools | Templates.
    }

    public ServiceProvider find(int id) throws DataAccessLayerException {
        return super.find(ServiceProvider.class, id); //To change body of generated methods, choose Tools | Templates.
    }
    
  
    public ArrayList<ServiceProvider> findServiceProviderbranch(ServiceProvider sp) {

        List branch = new ArrayList<ServiceProvider>();
        Query quary = (Query) session.createQuery("select sp from ServiceProvider s , ServiceProvider sp where sp.serviceProvider = :id");
        quary.setInteger("id", sp.getId());

        sp = (ServiceProvider) quary.uniqueResult();

        branch = quary.list();

        session.close();
        return (ArrayList<ServiceProvider>) branch;
    }

    public ArrayList<ServiceProviderPhone> findServiceProviderPhone(ServiceProvider sp) {

        Criteria crit = session.createCriteria(ServiceProviderPhone.class);
        crit.add(Restrictions.eq("ServiceProvider", sp));

        List results = new ArrayList<ServiceProviderPhone>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderPhone>) results;
    }

    public ArrayList<ServiceProviderCalendar> findServiceProviderCalender(ServiceProvider sp) {

        Criteria crit = session.createCriteria(ServiceProviderCalendar.class);
        crit.add(Restrictions.eq("ServiceProvider", sp));
        List results = new ArrayList<ServiceProviderCalendar>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderCalendar>) results;
    }

    public ArrayList<Address> findServiceProviderAddress(ServiceProvider sp) {

        Criteria crit = session.createCriteria(Address.class);
        crit.add(Restrictions.eq("ServiceProvider", sp));
        List results = new ArrayList<Address>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<Address>) results;
    }

    public ArrayList<ServiceProviderServices> findServiceProviderServices(ServiceProvider sp) {

        Criteria crit = session.createCriteria(ServiceProviderServices.class);
        crit.add(Restrictions.eq("ServiceProvider", sp));
        List results = new ArrayList<ServiceProviderServices>();

        results = crit.list();

        System.out.println(results);

        session.close();

        return (ArrayList<ServiceProviderServices>) results;
    }

    public List<ServiceProvider> findServiceProviderMakes(String make) {

        Criteria crt = session.createCriteria(ServiceProvider.class);

        crt.add(Restrictions.eq("ServiceProvider.makes", make));
        List<ServiceProvider> lst = crt.list();

        return lst;
    }

}
