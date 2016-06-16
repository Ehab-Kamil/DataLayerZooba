/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reham;

import Exceptions.DataAccessLayerException;
import abstractDao.HibernateFactory;
import dao.MakeDao;
import dao.ModelFeatureValueDao;
import dao.ServiceDAO;
import dao.ServiceProviderDAO;
import dao.ServiceProviderServicesDao;
import dao.VehicleDao;
import dao.VehicleModelDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
   Vehicle v1=new Vehicle();
    ModelFeatureValueDao mfDao;
    VehicleModelDao vmDao;
    Transaction transaction;
    VehicleDao vDao;
    ServiceProviderDAO sDao;
    ServiceProviderServicesDao spDao;
    MakeDao mDao;
    ServiceDAO serviceDao;
    public DataLayer() {
        session=HibernateFactory.openSession();
        mfDao=new ModelFeatureValueDao(session);
        vmDao= new VehicleModelDao(session);
        vDao=new VehicleDao(session);
        sDao=new ServiceProviderDAO(session);
        spDao=new ServiceProviderServicesDao(session);
        mDao=new MakeDao(session);
        serviceDao=new ServiceDAO(session);
    }
   
    public List<Object[]> getVehicles()
   {
       List<Object[]> vehicles=vDao.getByMake();
       return vehicles;
   }
    public void deleteVehicle(String name)
   {
       
       session.beginTransaction();
       try{
       v1=(Vehicle)vDao.getVehicleByName(name);
       vDao.delete(v1); 
       session.getTransaction().commit();
       }
       catch (DataAccessLayerException ex) {
            HibernateFactory.rollback(transaction);
        } 
       
   }
    
   public ArrayList showFeatures(int modelId)
   {
       VehicleModel vm=new VehicleModel();
       vm=vmDao.find(modelId);
       ArrayList<ModelFeaturesValues> featuresList= (ArrayList<ModelFeaturesValues>)mfDao.getMFValuesByByVehicleModel(vm);
       return featuresList;
   }
   
   public List<Make> showMakes(int d)
   {
       ServiceProvider p;
       p=sDao.find(d);
       List<Make> makeList= mDao.getMakebyServiceProvider(p.getName());
       return makeList;
   }
   public void update(ModelFeaturesValues m)
   {
       session.beginTransaction();
       try{
       
       mfDao.saveOrUpdate(m);
       session.getTransaction().commit();
       }
       catch (DataAccessLayerException ex) {
            HibernateFactory.rollback(transaction);
        }
   }
   public List<Object[]> findServiceProviders()
   {
       List<Object[]> list;
       list=sDao.getServiceProviderInfo();
       return list;
       
   }
   public List<ServiceProviderServices> getServices(ServiceProvider serviceProvider)
   {
       List<ServiceProviderServices> list=spDao.getByServiceProvider(serviceProvider);
       return list;
   }
   public ServiceProvider findById(int d)
   {
       ServiceProvider s=sDao.find(d);
       return s;
   }
   
   public List<Service> getServices(){
       List<Service> list=serviceDao.findAll();
       return list;
   }
   
   
}
