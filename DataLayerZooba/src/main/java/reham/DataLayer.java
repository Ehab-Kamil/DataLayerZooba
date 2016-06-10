/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reham;

import Exceptions.DataAccessLayerException;
import abstractDao.HibernateFactory;
import dao.ModelFeatureValueDao;
import dao.VehicleDao;
import dao.VehicleModelDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.ModelFeaturesValues;
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
    public DataLayer() {
        session=HibernateFactory.openSession();
        mfDao=new ModelFeatureValueDao(session);
        vmDao= new VehicleModelDao(session);
        vDao=new VehicleDao(session);
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
   
   public void update(ModelFeaturesValues m)
   {
       session.beginTransaction();
       try{
       
       mfDao.saveOrUpdate(m);
       session.getTransaction().commit();
       }
       catch (DataAccessLayerException ex) {
            HibernateFactory.rollback(transaction);
        } finally {
            HibernateFactory.close(session);
        }
   }
}
