/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pojo.Address;

/**
 *
 * @author Riham
 */
public class AddressDao extends AbstractDao<Address> {

    Session session;

    public AddressDao(Session s) {
        super(Address.class,s);
        session = s;
    }

    public List<Address> findAll() {
        return super.findAll(Address.class);
    }

    public Address find(int id) {
        return (Address) super.find(Address.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    public Address findByServiceprovider(String name) {
        String hql = "FROM Address A join A.serviceProvider S where A.id=S.address.id and S.name=?";
        Query query = session.createQuery(hql).setString(0, name);
        Address address = new Address();
        Iterator itr = query.list().iterator();
        while (itr.hasNext()) {
            Object[] p = (Object[]) itr.next();
            address = (Address) p[0];
        }
        return address;
    }

    public List<Address> findAddressByCountry(String country) {
        List<Address> address = new ArrayList();
        String hql = "From Address A  where A.country=?";
        Query query = session.createQuery(hql).setString(0, country);
        address = query.list();
        return address;
    }

    public List<Address> findAddressByCity(String city) {
        List<Address> address = new ArrayList();
        String hql = "From Address A  where A.city=?";
        Query query = session.createQuery(hql).setString(0, city);
        address = query.list();
        return address;
    }

    public List<Address> findAddressByStreet(String street) {
        List<Address> address = new ArrayList();
        String hql = "From Address A  where A.street=?";
        Query query = session.createQuery(hql).setString(0, street);
        address = query.list();
        return address;
    }

    public List<Address> findAddressByLandmark(String landmark) {
        List<Address> address = new ArrayList();
        String hql = "From Address A  where A.landmark=?";
        Query query = session.createQuery(hql).setString(0, landmark);
        address = query.list();
        return address;
    }

    public Address findAddressByPostalcode(String postalcode) {
        Address address = new Address();
        String hql = "From Address A  where A.postalCode=?";
        Query query = session.createQuery(hql).setString(0, postalcode);
        address = (Address) query.uniqueResult();
        return address;
    }
}
