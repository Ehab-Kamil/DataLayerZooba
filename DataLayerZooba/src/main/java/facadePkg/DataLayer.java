/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facadePkg;

import Exceptions.DataAccessLayerException;
import Utils.MailSender;
import DTO.TypeAndUnit;
import abstractDao.HibernateFactory;
import com.sun.tools.internal.xjc.api.TypeAndAnnotation;
import dao.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
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
//            HibernateFactory.rollback(transaction);
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
        Set<Make> makeSet = new HashSet<>(result);
//        serviceProvider.setMakes(makeSet);
        serviceProviderDAO.saveOrUpdate(serviceProvider);
        transaction.commit();
        HibernateFactory.close(session);

        return true;
    }

    public List<String> getAllDaysAsString() {
        List<String> results = new ArrayList<>();
        session = HibernateFactory.openSession();
        DaysDAO daysDAO = new DaysDAO(session);
        List<Days> lsDays = daysDAO.findAll();

        lsDays.stream().forEach((day) -> {
            results.add(day.getName());
        });

        HibernateFactory.close(session);
        return results;
    }

    public void insertSchedule(String[] selectedDays, ServiceProvider serviceProvider, Date from, Date to) {

        Days day = null;
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();

        DaysDAO daysDAO = new DaysDAO(session);
        ServiceProviderCalendarDAO serviceProviderCalendarDAO = new ServiceProviderCalendarDAO(session);

        for (int i = 0; i < selectedDays.length; i++) {
            day = daysDAO.getDayByName(selectedDays[i]);
            serviceProviderCalendarDAO.create(new ServiceProviderCalendar(day, serviceProvider, from, to));
        }

        transaction.commit();
        HibernateFactory.close(session);
    }

    public List<String> getAllServicesAsString() {

        session = HibernateFactory.openSession();

        List<String> results = new ArrayList<>();
        ServiceDAO serviceDAO = new ServiceDAO(session);

        List<Service> lsServices = serviceDAO.findAll();

        lsServices.stream().forEach((service) -> {
            results.add(service.getName());
        });

        HibernateFactory.close(session);
        return results;
    }

    public void setServicesForServiceProvider(String[] selectedServices, ServiceProvider serviceProvider, Date serviceFrom, Date serviceTo) {

        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        ServiceDAO serviceDAO = new ServiceDAO(session);
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        ServiceProviderServicesDao serviceProviderServicesDao = new ServiceProviderServicesDao(session);

        for (String serviceName : selectedServices) {
            Service iteratedService = serviceDAO.getUniqueServiceByName(serviceName);
            ServiceProviderServices serviceProviderServices = new ServiceProviderServices(iteratedService, serviceProvider, serviceFrom, serviceTo);
            serviceProviderServicesDao.create(serviceProviderServices);
        }

        transaction.commit();
        HibernateFactory.close(session);
    }

    public List<User> getAllUsers() {
        session = HibernateFactory.openSession();

        userDao = new UserDao(session);
        List<User> users = userDao.findAll();
        HibernateFactory.close(session);
        return users;

    }

    public int suspendUser(User u) {
        session = HibernateFactory.openSession();

        userDao = new UserDao(session);
        u.setSuspended(1);
        try {
            session.getTransaction().begin();
            userDao.create(u);
            session.getTransaction().commit();
            return 1;
        } catch (HibernateException e) {
            return 0;
        } finally {
            HibernateFactory.close(session);
        }
    }

    public int unsuspendUser(User u) {
        session = HibernateFactory.openSession();

        userDao = new UserDao(session);
        u.setSuspended(0);
        try {
            session.getTransaction().begin();
            userDao.create(u);
            session.getTransaction().commit();
            return 1;
        } catch (HibernateException e) {
            return 0;
        } finally {
            HibernateFactory.close(session);
        }
    }

    public void insertService(String name, List<TypeAndUnit> typeAndUnits) {

        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        ServiceDAO serviceDAO = new ServiceDAO(session);

        Service service = new Service(name);
        serviceDAO.create(service);

        service = serviceDAO.getUniqueServiceByName(name);
        typeDao = new TypeDao(session);
        measuringUnitDao = new MeasuringUnitDao(session);
        for (TypeAndUnit tau : typeAndUnits) {
            Type type = new Type();
            type.setName(tau.getTypeName());
            type.setService(service);
            type.setMeasuringUnit(measuringUnitDao.getByName(tau.getUnitName()));
            this.typeDao.create(type);

        }
        transaction.commit();
        HibernateFactory.close(session);
    }

    public List<String> getAllUnits() {
        List<String> result = new ArrayList<>();

        session = HibernateFactory.openSession();
        MeasuringUnitDao measuringUnitDao = new MeasuringUnitDao(session);
        List<MeasuringUnit> lsMeasuringUnits = measuringUnitDao.findAll();
        lsMeasuringUnits.stream().forEach((unit) -> {
            result.add(unit.getName());
        });
        HibernateFactory.close(session);
        return result;
    }

    public void insertTypeForService(List<TypeAndUnit> selectedType, int serviceId) {
        session = HibernateFactory.openSession();

    }
}
