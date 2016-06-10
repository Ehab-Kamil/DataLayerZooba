/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServicesHandlers;

import Exceptions.DataAccessLayerException;
import abstractDao.HibernateFactory;
import dao.DeviceDao;
import dao.MakeDao;
import dao.ModelDao;
import dao.TrimDao;
import dao.UserDao;
import dao.VehicleDao;
import dao.VehicleModelDao;
import dao.YearDao;
import facadePkg.DataLayer;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Device;
import pojo.Make;
import pojo.Model;
import pojo.Trim;
import pojo.User;
import pojo.Vehicle;
import pojo.VehicleModel;
import pojo.Year;

/**
 *
 * @author yoka
 */
public class Handler {

    Session session;
    UserDao uDao;

    public Handler() {

    }

    public User login(String user, String pass) {
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);
        User u = new User();
        u.setUsername(user);
        u.setPassword(pass);

        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        if (uarr.size() > 0) {
            User u1 = uarr.get(0);
            return u1;
        } else {
            return null;
        }
    }

    public boolean userExists(String user) {
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);
        User u = new User();
        u.setUsername(user);
        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        return uarr.size() > 0;
    }

    public int register(User u) {
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);
        uDao.create(u);
        return 0;
    }

    public List<Make> getMake() {
        session = HibernateFactory.openSession();
        List<Make> result = new ArrayList<>();
        MakeDao makeDao = new MakeDao(session);
        result = makeDao.findAll();
        HibernateFactory.close(session);
        return result;
    }

    public List<Model> getModelByMake(String make) {
        session = HibernateFactory.openSession();
        ModelDao modelDao = new ModelDao(session);
        List<Model> result = modelDao.getModelsByMake(make);
        HibernateFactory.close(session);
        return result;
    }

    public List<Trim> getTrim(String model, String year) {
        session = HibernateFactory.openSession();
        TrimDao trimDao = new TrimDao(session);
        List<Trim> lst = trimDao.getTrimByYearAndModel(model, year);
        HibernateFactory.close(session);
        return lst;

    }

    public List<Year> getYear(String model) {
        session = HibernateFactory.openSession();
        YearDao yearDao = new YearDao(session);
        List<Year> lst = yearDao.getYearByModel(model);
        HibernateFactory.close(session);
        return lst;

    }

    public boolean addVehicle(String m, String y, String trim, int u_id, String carName, int intialOdemeter) {

        boolean result = false;
        session = HibernateFactory.openSession();
        ModelDao modelDao = new ModelDao(session);
        Model model = new Model();
        model.setName(m);
        ArrayList<Model> models = (ArrayList<Model>) modelDao.findByExample(model);
        if (models.size() > 0) {
            model = models.get(0);
        }
        YearDao yearDao = new YearDao(session);
        Year year = new Year(Integer.parseInt(y));
        ArrayList<Year> years = (ArrayList<Year>) yearDao.findByExample(year);
        if (years.size() > 0) {
            year = years.get(0);
        }
        TrimDao trimDao = new TrimDao(session);
        Trim newTrim = new Trim(trim);
        ArrayList<Trim> trims = (ArrayList<Trim>) trimDao.findByExample(newTrim);
        if (trims.size() > 0) {
            newTrim = trims.get(0);
        }

        VehicleModel vm = new VehicleModel(model, newTrim, year);
        VehicleModelDao vmd = new VehicleModelDao(session);
        ArrayList<VehicleModel> vms = (ArrayList<VehicleModel>) vmd.findByExample(vm);
        if (vms.size() > 0) {
            vm = vms.get(0);
        }
        UserDao userDao = new UserDao(session);
        User user = userDao.find(u_id);
        VehicleDao vehicleDao = new VehicleDao(session);
        Vehicle vehicle = new Vehicle();
        vehicle.setCurrentOdemeter(0);
        vehicle.setIntialOdemeter(intialOdemeter);
        vehicle.setName(carName);
        vehicle.setUser(user);
        vehicle.setVehicleModel(vm);
        vm.getVehicles().add(vehicle);
        user.getVehicles().add(vehicle);
        session.getTransaction().begin();
        vehicleDao.create(vehicle);
        userDao.create(user);
        vmd.create(vm);
        session.getTransaction().commit();
        // result=session.getTransaction().wasCommitted();
        result = true;
        HibernateFactory.close(session);
        return result;
    }

    public User loginByEmail(String email, String pass) {
        User u = new User();
        u.setEmail(email);
        u.setPassword(pass);
//        uDao = new UserDao();
        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        if (uarr.size() > 0) {
            User u1 = uarr.get(0);
            return u1;
        } else {
            return null;
        }
    }
//    String forgetPassword()
//    {
//        
//    }

    public boolean emailExists(String email) {
        User u = new User();
        u.setEmail(email);
        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        return uarr.size() > 0;
    }

    public List<Device> getDeviceByUser(int userId) {
        session = HibernateFactory.openSession();
        DeviceDao deviceDao = new DeviceDao(session);
        List<Device> lst = deviceDao.getdevicePerUser(userId);
        HibernateFactory.close(session);
        return lst;
    }

    public boolean addDevice(int userId, String token) {
        Session session = HibernateFactory.openSession();
        DeviceDao deviceDao = new DeviceDao(session);
        UserDao userDao = new UserDao(session);
        User u = userDao.find(userId);
        try {
            System.out.println("u "+u);}
           catch (Exception e) {
            return false;
                  } 
           Device device = new Device(u, token);
            session.getTransaction().begin();
            deviceDao.create(device);
            session.getTransaction().commit();
            HibernateFactory.close(session);
            return true;
        } 
    }


