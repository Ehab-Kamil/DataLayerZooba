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
import pojo.*;

/**
 *
 * @author Ehab
 */
public class DataLayer {

    CarFeaturesDao carFeaturesDao;
    CoordinatesDAO coordinatesDAO;
    MakeDao makeDao;
    MakeServiceProviderDAO makeServiceProviderDAO;
    MeasuringUnitDao measuringUnitDao;
    ModelDao modelDao;
    ModelFeatureValueDao modelFeatureValueDao;
    ModelFeaturesValuesDao modelFeaturesValuesDao;
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
        try {
            makeDao = new MakeDao();
            modelDao = new ModelDao();
            yearDao = new YearDao();
            trimDao = new TrimDao();
            vehicleModelDao = new VehicleModelDao();
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
            result = 1;
        } catch (DataAccessLayerException ex) {
            result = 0;
        } finally {
            HibernateFactory.close(session);
        }
        return result;
    }

}
