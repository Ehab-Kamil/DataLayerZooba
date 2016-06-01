/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.CarFeatures;



/**
 *
 * @author yoka
 */
public class CarFeaturesDao extends AbstractDao<CarFeatures> {
     Session session;
    public CarFeaturesDao() {
     super(CarFeatures.class);
        session=HibernateFactory.openSession();
    }
    public List<CarFeatures> getCarFeaturesByName(String name)
    {
     Criteria crt=session.createCriteria(CarFeatures.class).add(Restrictions.like("name", "%"+name+"%"));
    List<CarFeatures> lst=crt.list();
 
    return lst;
    }

    @Override
    public List<CarFeatures> findByExample(CarFeatures t) {
        return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CarFeatures> findAll(Class clazz) {
        return super.findAll(clazz); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CarFeatures find(Class clazz, Long id) {
        return super.find(clazz, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(CarFeatures t) {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(CarFeatures t) {
        super.saveOrUpdate(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(CarFeatures t) {
        super.create(t); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
