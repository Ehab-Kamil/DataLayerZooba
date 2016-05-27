/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.TrackingData;
import pojo.Vehicle;

/**
 *
 * @author Mohammed
 */

public class TrackingDataDao extends AbstractDao<TrackingData> {
       private final SessionFactory factory;

    public TrackingDataDao(SessionFactory factory) {
        super(TrackingData.class);
        this.factory = factory ;
    }

       public TrackingData getById(int id){
        Session session=factory.openSession();
        
         TrackingData td = (TrackingData) session.createQuery("SELECT td FROM TrackingData td WHERE td.id = :id").setInteger("id", id).uniqueResult();
         session.close();
        return td;
    }
    
       public TrackingData getByintialOdemeter(int intialOdemeter){
        Session session=factory.openSession();
        
         TrackingData td = (TrackingData) session.createQuery("SELECT td FROM TrackingData td WHERE td.intialOdemeter = :intialOdemeter").setInteger("intialOdemeter", intialOdemeter).uniqueResult();
         session.close();
        return td;
    }
     
       public List<TrackingData> getByVehicle(Vehicle vehicle){
        Session session=factory.openSession();
        
           List<TrackingData> list =  session.createQuery("SELECT td FROM TrackingData td ,Vehicle v WHERE td.vehicle.id = :id").setInteger("id", vehicle.getId()).list();
         session.close();
        return list;
    }
  
}
