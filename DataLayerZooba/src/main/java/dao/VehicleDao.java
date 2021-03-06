/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Make;
import pojo.User;
import pojo.Vehicle;
import pojo.Year;

/**
 *
 * @author Ehab
 */
public class VehicleDao extends AbstractDao<Vehicle> {

    Session session;

    public VehicleDao(Session s) {
        super(Vehicle.class,s);
        session = s;
    }

    public Vehicle find(int id) throws DataAccessLayerException {
        return super.find(Vehicle.class, id); //To change body of generated methods, choose Tools | Templates.
    }
    
     public List<Vehicle> findAll()
    {
        return super.findAll(Vehicle.class);   
    }

    public List<Object[]> getByMake() {
        List<Vehicle> v = new ArrayList();
        String hql = "select v.name , vm.year, vm.trim, vm.model, ma.name ,vm.id From Vehicle v , VehicleModel vm, Make ma, "
                + "Model mo where v.vehicleModel.id=vm.id "
                + "and  mo.make.id=ma.id  and vm.model.id=mo.id";
        Query query = session.createQuery(hql);
        List<Object[]> result = query.list();
        for (Object[] row : result) {
            String name = (String) row[0];
            Year y = (Year) row[1];
            String makename = (String) row[4];
            System.out.println("Hello " + name + " " + y.getName() + " " + makename);
        }
        return result;
    }

    public Vehicle getVehicleByName(String name) {
        Vehicle v = new Vehicle();
        String hql = "From Vehicle v where v.name=?";
        Query query = session.createQuery(hql).setString(0, name);

        v = (Vehicle) query.uniqueResult();
        
        return v;
    }

    public List<Vehicle> getVehicleByUser(int userId) {
      Criteria criteria=session.createCriteria(Vehicle.class, "vehicle")
              .createAlias("vehicle.users", "user")
              .add(Restrictions.eq("user.id", userId));
      List<Vehicle> list=criteria.list();
               return list;
    }
}
