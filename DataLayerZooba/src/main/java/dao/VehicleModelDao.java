/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.Vehicle;
import pojo.VehicleModel;

/**
 *
 * @author Ehab
 */
public class VehicleModelDao extends AbstractDao<VehicleModel> {

    Session session;

    public VehicleModelDao(Session s) {
        super(VehicleModel.class, s);
        session = s;
    }

    @Override
    public List<VehicleModel> findByExample(VehicleModel t) {
        return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.
    }

    public VehicleModel find(int id) {
        return super.find(VehicleModel.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(VehicleModel t) {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(VehicleModel t) {
        super.saveOrUpdate(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(VehicleModel t) {
        super.create(t); //To change body of generated methods, choose Tools | Templates.
    }

    public List<VehicleModel> findAll() throws DataAccessLayerException {
         List<VehicleModel> objects = null;
        try {
            Query query = session.createQuery("from  VehicleModel");
            objects = query.list();
            for(VehicleModel vm:objects)
            {
            Hibernate.initialize(vm.getModel());
             Hibernate.initialize(vm.getTrim());
              Hibernate.initialize(vm.getYear());
              Hibernate.initialize(vm.getModel().getMake());
            }
        } catch (HibernateException e) {
            handleException(e);
        }
        return objects; }

    public List<VehicleModel> getByVehicleModelId(int id) {
        String hql = "from VehicleModel v "
                + "where v.id=?";
        Query query = session.createQuery(hql).setInteger(0, id);
        List<VehicleModel> result = query.list();
        for(VehicleModel v:result)
        {
            Hibernate.initialize(v.getModel());
            Hibernate.initialize(v.getYear());
            Hibernate.initialize(v.getTrim());
            Hibernate.initialize(v.getModel().getMake());
        }
        return result;
    }

    
}
