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

}
