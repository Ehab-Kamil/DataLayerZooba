/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facadePkg;

import Exceptions.DataAccessLayerException;
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

    public int insertVehicle(Make make, Model model, Year year, Trim trim) {

        int result = 0;

        Session session = HibernateFactory.openSession();
        Transaction tx = session.beginTransaction();
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
            tx.commit();
            result = 1;
        } catch (DataAccessLayerException ex) {
            HibernateFactory.rollback(tx);
            result = 0;
        } finally {
            HibernateFactory.close(session);
        }
        return result;
    }

}
