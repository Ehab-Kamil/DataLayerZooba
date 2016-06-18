/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reham;

import Exceptions.DataAccessLayerException;
import abstractDao.HibernateFactory;
import dao.CarFeaturesDao;
import dao.MakeDao;
import dao.ModelDao;
import dao.ModelFeatureValueDao;
import dao.ServiceDAO;
import dao.ServiceProviderDAO;
import dao.ServiceProviderServicesDao;
import dao.TrimDao;
import dao.VehicleDao;
import dao.VehicleModelDao;
import dao.YearDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Make;
import pojo.ModelFeaturesValues;
import pojo.Service;
import pojo.ServiceProvider;
import pojo.ServiceProviderServices;
import pojo.Vehicle;
import pojo.VehicleModel;

/**
 *
 * @author Riham
 */
public class DataLayer implements Serializable {

    Session session;
    VehicleModel v1 = new VehicleModel();
    ModelFeatureValueDao mfDao;
    VehicleModelDao vmDao;
    Transaction transaction;
    VehicleDao vDao;
    ServiceProviderDAO sDao;
    ServiceProviderServicesDao spDao;
    MakeDao mDao;
    ServiceDAO serviceDao;

    public DataLayer() {

    }

    public List<VehicleModel> getVehicles() {
        session = HibernateFactory.openSession();
        Transaction transaction = session.beginTransaction();
        vmDao = new VehicleModelDao(session);
        List<VehicleModel> vehicles = vmDao.findAll();
        HibernateFactory.close(session);
        return vehicles;
    }

    public boolean deleteVehicle(int id) {
        boolean flag = true;
        int vmId;
        List<Integer> vmIds = new ArrayList();
        session = HibernateFactory.openSession();
        vmDao = new VehicleModelDao(session);
        vDao = new VehicleDao(session);
        try {
            v1 = vmDao.find(id);
            List<Vehicle> vehicleList = vDao.findAll();
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
                vmDao.delete(v1);
                transaction.commit();
            }
        } catch (HibernateException ex) {
            transaction.rollback();
        } finally {
            HibernateFactory.close(session);
        }
        return flag;
    }

    public ArrayList showFeatures(int modelId) {
        VehicleModel vm = new VehicleModel();
        session = HibernateFactory.openSession();
        mfDao = new ModelFeatureValueDao(session);
        vmDao = new VehicleModelDao(session);
        vm = vmDao.find(modelId);
        ArrayList<ModelFeaturesValues> featuresList = (ArrayList<ModelFeaturesValues>) mfDao.getMFValuesByByVehicleModel(vm);
        HibernateFactory.close(session);
        return featuresList;
    }

    public List<Make> showMakes(int d) {
        session = HibernateFactory.openSession();
        sDao = new ServiceProviderDAO(session);
        mDao = new MakeDao(session);
        ServiceProvider p;
        p = sDao.find(d);
        List<Make> makeList = mDao.getMakebyServiceProvider(p.getName());
        HibernateFactory.close(session);
        return makeList;
    }

    public void updateModelFeatureValues(ModelFeaturesValues m) {
        session = HibernateFactory.openSession();
        mfDao = new ModelFeatureValueDao(session);
        CarFeaturesDao carFeaturesDao=new CarFeaturesDao(session);
        Transaction transaction = session.beginTransaction();
        carFeaturesDao.saveOrUpdate(m.getCarFeatures());
        mfDao.saveOrUpdate(m);
        transaction.commit();
        HibernateFactory.close(session);
    }

    public List<Object[]> findServiceProviders() {
        session = HibernateFactory.openSession();
        sDao = new ServiceProviderDAO(session);
        List<Object[]> list;
        list = sDao.getServiceProviderInfo();
        HibernateFactory.close(session);
        return list;

    }

    public List<ServiceProviderServices> getServices(ServiceProvider serviceProvider) {
        session = HibernateFactory.openSession();
        spDao = new ServiceProviderServicesDao(session);
        List<ServiceProviderServices> list = spDao.getByServiceProvider(serviceProvider);
        HibernateFactory.close(session);
        return list;
    }

    public ServiceProvider findById(int d) {
        session = HibernateFactory.openSession();
        sDao = new ServiceProviderDAO(session);
        ServiceProvider s = sDao.find(d);
        HibernateFactory.close(session);
        return s;
    }

    public List<Service> getServices() {
        session = HibernateFactory.openSession();
        serviceDao = new ServiceDAO(session);
        List<Service> list = serviceDao.findAll();
        HibernateFactory.close(session);
        return list;
    }

    public List<VehicleModel> getVehicleModel(int d) {
        session = HibernateFactory.openSession();
        vmDao = new VehicleModelDao(session);
        List<VehicleModel> v = vmDao.getByVehicleModelId(d);
        HibernateFactory.close(session);
        return v;
    }

    public void updateVehicleModel(VehicleModel v) {
        session = HibernateFactory.openSession();
        vmDao = new VehicleModelDao(session);
        YearDao yearDao = new YearDao(session);
        TrimDao trimDao = new TrimDao(session);
        ModelDao modelDao = new ModelDao(session);
        MakeDao makeDao = new MakeDao(session);

        Transaction transaction = session.beginTransaction();
        yearDao.saveOrUpdate(v.getYear());
        trimDao.saveOrUpdate(v.getTrim());
        modelDao.saveOrUpdate(v.getModel());
        makeDao.saveOrUpdate(v.getModel().getMake());
        vmDao.saveOrUpdate(v);
        transaction.commit();
        HibernateFactory.close(session);
    }

    public VehicleModel find(int d) {
        session = HibernateFactory.openSession();
        vmDao = new VehicleModelDao(session);
        VehicleModel m = vmDao.find(d);

        HibernateFactory.close(session);
        return m;
    }
}
