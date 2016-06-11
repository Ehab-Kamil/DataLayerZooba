/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facadePkg;

import Exceptions.DataAccessLayerException;
import Utils.MailSender;
import abstractDao.HibernateFactory;
import dao.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.*;

/**
 *
 * @author Ehab
 */
public class DataLayer {
    
    CarFeaturesDao carFeaturesDao;
    CoordinatesDAO coordinatesDAO;
    MakeDao makeDao;
    MeasuringUnitDao measuringUnitDao;
    ModelDao modelDao;
    ModelFeatureValueDao modelFeatureValueDao;
    ServiceDAO serviceDAO;
    ServiceProviderCalendarDAO serviceProviderCalendarDAO;
    ServiceProviderDAO serviceProviderDAO;
    ServiceProviderPhoneDao serviceProviderPhoneDao;
    ServiceProviderServicesDao serviceProviderServicesDao;
    TrackingDataDao ttraTrackingDataDao;
    TrimDao trimDao;
    TripDAO tripDAO;
    TypeDao typeDao;
    UserDao userDao;
    VehicleDao vehicleDao;
    VehicleModelDao vehicleModelDao;
    YearDao yearDao;
    Session session;
    Transaction transaction;
    
    public int insertVehicle(Make make, Model model, Year year, Trim trim) {
        
        int result = 0;
        
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        try {
            makeDao = new MakeDao(session);
            modelDao = new ModelDao(session);
            yearDao = new YearDao(session);
            trimDao = new TrimDao(session);
            vehicleModelDao = new VehicleModelDao(session);
            VehicleModel vehicleModel = new VehicleModel();
            vehicleModel.setModel(model);
            vehicleModel.setTrim(trim);
            vehicleModel.setYear(year);
            
            make.getModels().add(model);
            model.setMake(make);
            model.getVehicleModels().add(vehicleModel);
            year.getVehicleModels().add(vehicleModel);
            trim.getVehicleModels().add(vehicleModel);
            
            makeDao.create(make);
            modelDao.create(model);
            yearDao.create(year);
            trimDao.create(trim);
            vehicleModelDao.create(vehicleModel);
            transaction.commit();
            result = 1;
        } catch (DataAccessLayerException ex) {
            HibernateFactory.rollback(transaction);
            result = 0;
        } finally {
            HibernateFactory.close(session);
        }
        return result;
    }

    ////This Function is Not Tested Yet 
    public int sendForgetPasswordMail(String email) {
        
        int result = 0;
        
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        userDao = new UserDao(session);
        User u = new User();
        u.setEmail(email);
        User user = (User) userDao.findByExample(u);
        
        MailSender mailSender = new MailSender();
        mailSender.sendRestPasswordMail(user.getEmail(), user.getPassword());
        
        result = 1;
        
        return result;
    }
    
    public ServiceProvider getServiceProviderByName(String serviceProviderName) {
        
        session = HibernateFactory.openSession();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(serviceProviderName);
        List<ServiceProvider> lstServProv = (List<ServiceProvider>) serviceProviderDAO.findByExample(serviceProvider);
        HibernateFactory.close(session);
        return lstServProv.get(0);
        
    } 
    
    public void insertServiceProvider(ServiceProvider newServiceProvider) {
        
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        serviceProviderDAO.create(newServiceProvider);
        transaction.commit();
        HibernateFactory.close(session);
        
    }
    
    public void insertAddressForServiceProvider(Address address) {
        Session session = HibernateFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        AddressDao addressDao = new AddressDao(session);
        
        addressDao.create(address);
        
        transaction.commit();
        HibernateFactory.close(session);
    }
    
    public void insertPhoneForServiiceProvider(ServiceProviderPhone phone) {
        Session session = HibernateFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        ServiceProviderPhoneDao serviceProviderPhoneDao = new ServiceProviderPhoneDao(session);
        
        serviceProviderPhoneDao.create(phone);
        
        transaction.commit();
        HibernateFactory.close(session);
        
    }
    
    public void updateServiceProvider(ServiceProvider serviceProvider) {
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        serviceProviderDAO.saveOrUpdate(serviceProvider);
        transaction.commit();
        HibernateFactory.close(session);
        
    }
    
    public List<String> getAllMakesAsStrings() {
        List<String> results = new ArrayList<>();
        session = HibernateFactory.openSession();
        MakeDao makeDao = new MakeDao(session);
        List<Make> lsMakes = makeDao.findAll();
        
        lsMakes.stream().forEach((make) -> {
            results.add(make.getName());
        });
        HibernateFactory.close(session);
        return results;
    }
    
    public boolean getMakesFromStringArray(String[] selectedMakes, ServiceProvider serviceProvider) {
        
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        MakeDao makeDao = new MakeDao(session);
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        List<Make> result = new ArrayList<>();
        
        for (String makeName : selectedMakes) {
            Make iteratedMake = makeDao.getUniqueMakeByName(makeName);
            iteratedMake.getServiceProviders().add(serviceProvider);
            makeDao.saveOrUpdate(iteratedMake);
            result.add(iteratedMake);
        }
        Set<Make> makeSet=new HashSet<>(result);
        serviceProviderDAO.saveOrUpdate(serviceProvider);
        transaction.commit();
        HibernateFactory.close(session);
        
        return true;
    }
    
}
