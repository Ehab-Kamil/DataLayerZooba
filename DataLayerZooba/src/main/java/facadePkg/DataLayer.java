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
import dao.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.*;

/**
 *
 * @author Ehab
 */
public class DataLayer implements Serializable {

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
    VehicleModel v1 = new VehicleModel();

    public VehicleModel insertVehicle(Make make, Model model, Year year, Trim trim) {
        int result = 0;
        VehicleModel vehicleModel = null;
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        try {
            makeDao = new MakeDao(session);
            modelDao = new ModelDao(session);
            yearDao = new YearDao(session);
            trimDao = new TrimDao(session);
            vehicleModelDao = new VehicleModelDao(session);

            Make finalMake = makeDao.getUniqueMakeByName(make.getName());
            Model finalModel = modelDao.getUniqueModelByName(model.getName());
            Year finalYear = yearDao.getYearByName(year.getName());
            Trim finalTrim = trimDao.getUniqueTrimByName(trim.getName());

            vehicleModel = new VehicleModel();
            if (finalModel == null) {
                vehicleModel.setModel(model);
            } else {
                vehicleModel.setModel(finalModel);
            }
            if (finalTrim == null) {
                vehicleModel.setTrim(trim);
            } else {
                vehicleModel.setTrim(finalTrim);
            }
            if (finalYear == null) {
                vehicleModel.setYear(year);
            } else {
                vehicleModel.setYear(finalYear);
            }

//            make.getModels().add(model);
            if (finalMake == null) {
                model.setMake(make);
            } else {
                model.setMake(finalMake);
            }
//            model.getVehicleModels().add(vehicleModel);
//            year.getVehicleModels().add(vehicleModel);
//            trim.getVehicleModels().add(vehicleModel);
            makeDao.create(make);
            modelDao.create(model);
            yearDao.create(year);
            trimDao.create(trim);
            vehicleModelDao.create(vehicleModel);
            transaction.commit();
            vehicleModel = vehicleModelDao.getLastInserted();
        } catch (DataAccessLayerException ex) {
//            HibernateFactory.rollback(transaction);
            result = 0;
        } finally {
            HibernateFactory.close(session);
        }
        return vehicleModel;
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

    public void insertServiceProvider(ServiceProvider newServiceProvider, ServiceProvider mainBranch) {

        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        if (mainBranch.getName().length()>0) {
            ServiceProvider main = (ServiceProvider) serviceProviderDAO.findByExample(mainBranch).get(0);
            newServiceProvider.setServiceProvider(main);
        }
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
            //  iteratedMake.getServiceProviders().add(serviceProvider);
            //  makeDao.saveOrUpdate(iteratedMake);
            result.add(iteratedMake);
        }
        Set<Make> makeSet = new HashSet<>(result);
        serviceProvider.setMakes(makeSet);
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
//serviceProviderServicesDao.
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

    public ServiceProvider getServiceProviderById(int serviceProviderId) {
        session = HibernateFactory.openSession();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        ServiceProvider result = serviceProviderDAO.find(serviceProviderId);
        Hibernate.initialize(result.getAddress());
        Hibernate.initialize(result.getMakes());
        Hibernate.initialize(result.getServiceProviderPhones());
        Hibernate.initialize(result.getServiceProviderCalendars());
        Hibernate.initialize(result.getServiceProviderServiceses());
        HibernateFactory.close(session);
        return result;
    }

    public List<VehicleModel> getVehicles() {
        session = HibernateFactory.openSession();
        Transaction transaction = session.beginTransaction();
        vehicleModelDao = new VehicleModelDao(session);
        List<VehicleModel> vehicles = vehicleModelDao.findAll();
        HibernateFactory.close(session);
        return vehicles;
    }

    public boolean deleteVehicle(int id) {
        boolean flag = true;
        int vmId;
        List<Integer> vmIds = new ArrayList();
        session = HibernateFactory.openSession();
        vehicleModelDao = new VehicleModelDao(session);
        vehicleDao = new VehicleDao(session);
        try {
            v1 = vehicleModelDao.find(id);
            List<Vehicle> vehicleList = vehicleDao.findAll();
            for (Vehicle vehicle : vehicleList) {
                VehicleModel vm = vehicle.getVehicleModel();
                vmId = vm.getId();
                vmIds.add(vmId);
            }
            for (int n : vmIds) {
                if (id == n) {
                    flag = false;
                    System.out.println("Can't delete");
                    break;
                }
            }
            if (flag) {
                Transaction transaction = session.beginTransaction();
                vehicleModelDao.delete(v1);
                transaction.commit();
            }
        } catch (HibernateException ex) {
            transaction.rollback();
        } finally {
            HibernateFactory.close(session);
        }
        return flag;
    }

    public List showFeatures(int modelId) {
        VehicleModel vm = new VehicleModel();
        session = HibernateFactory.openSession();
        modelFeatureValueDao = new ModelFeatureValueDao(session);
        vehicleModelDao = new VehicleModelDao(session);
        vm = vehicleModelDao.getByVehicleModelId(modelId).get(0);
        List<ModelFeaturesValues> lst = new ArrayList<>(vm.getModelFeaturesValueses());
        for (ModelFeaturesValues m : lst) {
            Hibernate.initialize(m.getCarFeatures().getName());
            Hibernate.initialize(m.getValue());
        }
        HibernateFactory.close(session);
        return lst;
    }

    public List<Make> showMakes(int d) {
        session = HibernateFactory.openSession();
        serviceProviderDAO = new ServiceProviderDAO(session);
        makeDao = new MakeDao(session);
        ServiceProvider p;
        p = serviceProviderDAO.find(d);
        List<Make> makeList = makeDao.getMakebyServiceProvider(p.getName());
        HibernateFactory.close(session);
        return makeList;
    }

    public void updateModelFeatureValues(ModelFeaturesValues m) {
        session = HibernateFactory.openSession();
        modelFeatureValueDao = new ModelFeatureValueDao(session);
        carFeaturesDao = new CarFeaturesDao(session);
        transaction = session.beginTransaction();
        carFeaturesDao.saveOrUpdate(m.getCarFeatures());
        modelFeatureValueDao.saveOrUpdate(m);
        transaction.commit();
        HibernateFactory.close(session);
    }

    public List<Object[]> findServiceProviders() {
        session = HibernateFactory.openSession();
        serviceProviderDAO = new ServiceProviderDAO(session);
        List<Object[]> list;
        list = serviceProviderDAO.getServiceProviderInfo();
        HibernateFactory.close(session);
        return list;

    }

    public List<ServiceProviderServices> getServices(ServiceProvider serviceProvider) {
        session = HibernateFactory.openSession();
        serviceProviderServicesDao = new ServiceProviderServicesDao(session);
        List<ServiceProviderServices> list = serviceProviderServicesDao.getByServiceProvider(serviceProvider);
        HibernateFactory.close(session);
        return list;
    }

    public ServiceProvider findById(int d) {
        session = HibernateFactory.openSession();
        serviceProviderDAO = new ServiceProviderDAO(session);
        ServiceProvider s = serviceProviderDAO.find(d);
        HibernateFactory.close(session);
        return s;
    }

    public List<Service> getServices() {
        session = HibernateFactory.openSession();
        serviceDAO = new ServiceDAO(session);
        List<Service> list = serviceDAO.findAll();
        HibernateFactory.close(session);
        return list;
    }

    public List<VehicleModel> getVehicleModel(int d) {
        session = HibernateFactory.openSession();
        vehicleModelDao = new VehicleModelDao(session);
        List<VehicleModel> v = vehicleModelDao.getByVehicleModelId(d);
        HibernateFactory.close(session);
        return v;
    }

    public void updateVehicleModel(VehicleModel v) {
        session = HibernateFactory.openSession();
        vehicleModelDao = new VehicleModelDao(session);
        YearDao yearDao = new YearDao(session);
        TrimDao trimDao = new TrimDao(session);
        ModelDao modelDao = new ModelDao(session);
        MakeDao makeDao = new MakeDao(session);

        transaction = session.beginTransaction();
        yearDao.create(v.getYear());
        trimDao.create(v.getTrim());
        modelDao.create(v.getModel());
        makeDao.create(v.getModel().getMake());
        vehicleModelDao.saveOrUpdate(v);
        transaction.commit();
        HibernateFactory.close(session);
    }

    public VehicleModel find(int d) {
        session = HibernateFactory.openSession();
        vehicleModelDao = new VehicleModelDao(session);
        VehicleModel m = vehicleModelDao.find(d);

        HibernateFactory.close(session);
        return m;
    }

    public List<Device> getAllDevices() {
        session = HibernateFactory.openSession();
        DeviceDao deviceDao = new DeviceDao(session);
        List<Device> list = deviceDao.getAllDevices();
        list.stream().forEach((d) -> {
            Hibernate.initialize(d.getUser());
        });
        return list;
    }

    public void insertMakeForServiceProvider(String makeName, ServiceProvider serviceProvider) {

        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        MakeDao makeDao = new MakeDao(session);
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        Make make = makeDao.getUniqueMakeByName(makeName);
        serviceProvider.getMakes().add(make);
        serviceProviderDAO.saveOrUpdate(serviceProvider);
        transaction.commit();
        HibernateFactory.close(session);
    }

    public void deleteMakeForServiceProvider(String makeName, ServiceProvider serviceProvider) {
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        MakeDao makeDao = new MakeDao(session);
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        Make make = makeDao.getUniqueMakeByName(makeName);
        serviceProvider.getMakes().remove(make);
        serviceProviderDAO.saveOrUpdate(serviceProvider);
        transaction.commit();
        HibernateFactory.close(session);
    }

    public void insertServiceForServiceProvider(String serviceName, ServiceProvider serviceProvider, Date serviceFrom, Date serviceTo) {
        session = HibernateFactory.openSession();
        transaction = session.beginTransaction();
        ServiceDAO serviceDAO = new ServiceDAO(session);
//        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        ServiceProviderServicesDao serviceProviderServicesDao = new ServiceProviderServicesDao(session);

        Service service = serviceDAO.getUniqueServiceByName(serviceName);
        ServiceProviderServices serviceProviderServices = new ServiceProviderServices(service, serviceProvider, serviceFrom, serviceTo);
        serviceProviderServicesDao.create(serviceProviderServices);

        transaction.commit();
        HibernateFactory.close(session);
    }

    public void deleteServiceForServiceProvider(String serviceName, ServiceProvider serviceProvider, Date serviceFrom, Date serviceTo) {
//        session = HibernateFactory.openSession();
//        transaction = session.beginTransaction();
//        ServiceDAO serviceDAO = new ServiceDAO(session);
////        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
//        ServiceProviderServicesDao serviceProviderServicesDao = new ServiceProviderServicesDao(session);
//
//        Service service = serviceDAO.getUniqueServiceByName(serviceName);
//        ServiceProviderServices serviceProviderServices = new ServiceProviderServices(service, serviceProvider, serviceFrom, serviceTo);
//        for (ServiceProviderServices sps : serviceProvider.getServiceProviderServiceses()) {
//            if (sps.getService().getId() == service.getId()) {
//                serviceProviderServicesDao.delete(sps);
//            }
//        }
//        serviceProviderServicesDao.delete(serviceProviderServices);
//
//        transaction.commit();8
//        HibernateFactory.close(session);
        ///This Method might be done after a while  
    }

    public List<String> getAllServiceProviders() {
        session = HibernateFactory.openSession();
        serviceProviderDAO = new ServiceProviderDAO(session);
        List<String> result = new ArrayList<>();
        List<ServiceProvider> list = serviceProviderDAO.findAll();
        HibernateFactory.close(session);
        for (ServiceProvider sp : list) {
            result.add(sp.getName());
        }
        return result;
    }
}
