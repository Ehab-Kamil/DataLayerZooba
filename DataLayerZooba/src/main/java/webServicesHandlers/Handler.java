/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServicesHandlers;

import Exceptions.DataAccessLayerException;
import abstractDao.HibernateFactory;
import dao.CoordinatesDAO;
import dao.DeviceDao;
import dao.MakeDao;
import dao.ModelDao;
import dao.ServiceProviderDAO;
import dao.TrackingDataDao;
import dao.TrimDao;
import dao.TripDAO;
import dao.TypeDao;
import dao.UserDao;
import dao.VehicleDao;
import dao.VehicleModelDao;
import dao.YearDao;
import facadePkg.DataLayer;
import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Coordinates;
import pojo.Device;
import pojo.Make;
import pojo.Model;
import pojo.ServiceProvider;
import pojo.TrackingData;
import pojo.Trim;
import pojo.Trips;
import pojo.Type;
import pojo.User;
import pojo.Vehicle;
import pojo.VehicleModel;
import pojo.Year;
import pojo.Vehicle;

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

    public User register(User u) {
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);
        session.getTransaction().begin();
        uDao.create(u);
        session.getTransaction().commit();
        u = uDao.getUser(u);
        HibernateFactory.close(session);
        return u;
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

    public Vehicle addVehicle(String m, String y, String trim, int u_id, String carName, int intialOdemeter,String liString) {

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
        vehicle.setCurrentOdemeter(intialOdemeter);
        vehicle.setIntialOdemeter(intialOdemeter);
        vehicle.setName(carName);
        vehicle.setUser(user);
        vehicle.setLicencePlate(liString);
        vehicle.setVehicleModel(vm);
        vm.getVehicles().add(vehicle);
        user.getVehicles().add(vehicle);
        session.getTransaction().begin();
        vehicleDao.create(vehicle);
        userDao.create(user);
        vmd.create(vm);
        session.getTransaction().commit();
        // result=session.getTransaction().wasCommitted();
        List<Vehicle> vs = vehicleDao.findByExample(vehicle);

        HibernateFactory.close(session);
        return vs.get(0);
    }

    public User loginByEmail(String email, String pass) {
        session = HibernateFactory.openSession();
        User u = new User();
        u.setEmail(email);
        u.setPassword(pass);
        uDao = new UserDao(session);
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
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);

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
        session = HibernateFactory.openSession();
        DeviceDao deviceDao = new DeviceDao(session);
        UserDao userDao = new UserDao(session);
        User u = userDao.find(userId);
        try {
            System.out.println("u " + u);
        } catch (Exception e) {
            return false;
        }
        if (deviceDao.getdevicebyToken(token).isEmpty()) {
            Device device = new Device(u, token);
            session.getTransaction().begin();
            deviceDao.create(device);
            session.getTransaction().commit();
        }
        HibernateFactory.close(session);
        return true;
    }

    public void insertPictureToUserAccount(String image, int userId) {
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);
        User user = uDao.find(userId);
        user.setImage(image);
        session.getTransaction().begin();
        uDao.create(user);
        session.getTransaction().commit();
    }

    public List<Vehicle> getVehiclesPerUser(int userId) {
        session = HibernateFactory.openSession();
        VehicleDao vehicleDao = new VehicleDao(session);
        List<Vehicle> list = vehicleDao.getVehicleByUser(userId);
        HibernateFactory.close(session);
        return list;
    }

    public User loginWithFacebook(String email) {
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);
        User u = new User();
        u.setEmail(email);
        u.setPassword("fbp");

        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        if (uarr.size() > 0) {
            User u1 = uarr.get(0);
            HibernateFactory.close(session);
            return u1;
        } else {
            HibernateFactory.close(session);
            return null;
        }
    }

    public Make getMakeByModel(Integer id) {
        session = HibernateFactory.openSession();
        MakeDao makeDao = new MakeDao(session);
        ModelDao md = new ModelDao(session);
        Model m = md.find(id);
        List<Make> mks = makeDao.getMakebyModel(m.getName());
        return mks.get(0);
    }

    public Trips addTrip(int initialOdemeter, int coveredMilage, int vehicleId) {
        session = HibernateFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TripDAO tripDAO = new TripDAO(session);
        VehicleDao vehicleDao = new VehicleDao(session);

        Trips trip = new Trips();
        trip.setIntialOdemeter(initialOdemeter);
        trip.setCoveredMilage(coveredMilage);

        Vehicle vehicle = vehicleDao.find(vehicleId);
        trip.setVehicle(vehicle);

        tripDAO.create(trip);
        Trips result = tripDAO.findByExample(trip).get(0);
        Hibernate.initialize(result.getCoordinateses());
      
        transaction.commit();
        HibernateFactory.close(session);

        return result;
    }

    public Coordinates addCoordinatesToTrip(float longitude, float latitude, int tripId) {
        session = HibernateFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CoordinatesDAO coordinatesDAO = new CoordinatesDAO(session);
        TripDAO tripDAO = new TripDAO(session);

        Trips trips = tripDAO.find(tripId);
        Coordinates coordinates = new Coordinates();

        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);
        coordinates.setTrips(trips);

        coordinatesDAO.create(coordinates);
List<Coordinates> list=coordinatesDAO.findByExample(coordinates);
   Coordinates result=new Coordinates();
if(list.size()>0)
 result =list.get(0);

        transaction.commit();

        HibernateFactory.close(session);
        return result;
    }

    public void insertImageToTrip(String image, int tripId) {
        session = HibernateFactory.openSession();
        TripDAO tripDAO = new TripDAO(session);

        Trips trips = tripDAO.find(tripId);
        trips.setImage(image);
        session.getTransaction().begin();
        tripDAO.create(trips);
        session.getTransaction().commit();

    }

    public boolean insertTrackingData(int intialOdemeter, String dataAdded, String dataModified, int typeId, int vehicleId, String value) {
        session = HibernateFactory.openSession();
        TrackingDataDao trackingDataDAO = new TrackingDataDao(session);
        TypeDao typeDao = new TypeDao(session);
        VehicleDao vd = new VehicleDao(session);
        TrackingData trackingData = new TrackingData();
        trackingData.setIntialOdemeter(intialOdemeter);
        trackingData.setValue(value);
        Type type = typeDao.find(typeId);
        trackingData.setType(type);
        Vehicle vehicle = vd.find(vehicleId);
        trackingData.setVehicle(vehicle);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateadded;
        java.util.Date datemodified;
        try {
            dateadded = sdf.parse(dataAdded);
            java.sql.Date sqlDate = new Date(dateadded.getTime());
            trackingData.setDateAdded(sqlDate);
            datemodified = sdf.parse(dataModified);
            java.sql.Date sqlDate1 = new Date(datemodified.getTime());
            trackingData.setDateModified(sqlDate1);

        } catch (ParseException ex) {

            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            HibernateFactory.close(session);
            return false;
        }
        boolean flag = true;
        try {
            System.out.println("" + type.getId() + " " + vehicle.getId());
        } catch (Exception e) {
            flag = false;
        }
        if (flag && value != null) {
            session.getTransaction().begin();
            trackingDataDAO.create(trackingData);
            session.getTransaction().commit();
            HibernateFactory.close(session);
            return true;
        } else {
            return false;
        }
    }

    public List<ServiceProvider> getServiceProviders() {
        session = HibernateFactory.openSession();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        List<ServiceProvider> serviceProviders = new ArrayList<>(serviceProviderDAO.getMainServiceProviders());
        serviceProviders.stream().forEach((sp) -> {
            Hibernate.initialize(sp.getMakes());
            Hibernate.initialize(sp.getAddress());
            Hibernate.initialize(sp.getServiceProviderCalendars());
            Hibernate.initialize(sp.getServiceProviderPhones());
            Hibernate.initialize(sp.getServiceProviderServiceses());
            //System.out.println(""+sp.getName());
        });
        HibernateFactory.close(session);
        return serviceProviders;
    }

    public List<ServiceProvider> getServiceProviderBranches(int serviceProviderId) {
        session = HibernateFactory.openSession();
        ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO(session);
        List<ServiceProvider> serviceProviders = new ArrayList<>(serviceProviderDAO.getServiceProviderBranches(serviceProviderId));
        serviceProviders.stream().forEach((sp) -> {
            Hibernate.initialize(sp.getMakes());
            Hibernate.initialize(sp.getAddress());
            Hibernate.initialize(sp.getServiceProviderCalendars());
            Hibernate.initialize(sp.getServiceProviderPhones());
            Hibernate.initialize(sp.getServiceProviderServiceses());
            //System.out.println(""+sp.getName());
        });
        HibernateFactory.close(session);
        return serviceProviders;
    }

    public TrackingData getTrackingData() {
        session = HibernateFactory.openSession();
        BigInteger bi = (BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();
        TrackingDataDao trackingDataDao = new TrackingDataDao(session);
        int id = bi.intValue();
        TrackingData td = trackingDataDao.find(id);
        Hibernate.initialize(td.getType());
        Hibernate.initialize(td.getType().getService());
         Hibernate.initialize(td.getType().getMeasuringUnit());
        HibernateFactory.close(session);
        return td;
    }

    public List<TrackingData> getTrackingData(int vehicleId) {
       session = HibernateFactory.openSession();
       TrackingDataDao tdd=new TrackingDataDao(session);
       List<TrackingData> list=tdd.getByVehicle(vehicleId);
       list.stream().forEach((td)->{
       Hibernate.initialize(td.getType());
        Hibernate.initialize(td.getType().getService());
         Hibernate.initialize(td.getType().getMeasuringUnit());
       });
       
       HibernateFactory.close(session);
       return list;
    }

    public boolean lPlateisExists(String licencePlate) {
       session = HibernateFactory.openSession();
       VehicleDao vd=new VehicleDao(session);
      Vehicle v=new Vehicle();
      v.setLicencePlate(licencePlate);
     List<Vehicle> list=vd.findByExample(v);
     return list.size()>0;
    }

    public boolean updateUserPassword(String email, String password) {
   session=HibernateFactory.openSession();
   UserDao userDao=new UserDao(session);
   User u=new User();
   u.setEmail(email);
   List<User> list=userDao.findByExample(u);
           if(list.size()>0)
           {   
              User u1=list.get(0);
           u1.setPassword(password);
           session.getTransaction().begin();
           userDao.create(u1);
            session.getTransaction().commit();
            HibernateFactory.close(session);
           return true;
           }
           return false;
    }

    public User getUserByMail(String email) {
      session=HibernateFactory.openSession();
      uDao=new UserDao(session);
      User u=new User();
      u.setEmail(email);
      u=uDao.getUser(u);
      HibernateFactory.close(session);
      return u;
    }
}
