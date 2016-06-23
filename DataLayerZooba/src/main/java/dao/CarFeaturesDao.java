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

    public CarFeaturesDao(Session s) {
        super(CarFeatures.class, s);
        session = s;
    }

    public CarFeatures getCarFeaturesByName(String name) {

        Criteria crt = session.createCriteria(CarFeatures.class).add(Restrictions.eq("name", name));
        CarFeatures result = (CarFeatures) crt.uniqueResult();

        return result;
    }

    @Override
    public List<CarFeatures> findByExample(CarFeatures t) {
        return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.
    }

    public List<CarFeatures> findAll() {
        return super.findAll(CarFeatures.class); //To change body of generated methods, choose Tools | Templates.
    }

    public CarFeatures find(int id) {
        return super.find(CarFeatures.class, id); //To change body of generated methods, choose Tools | Templates.
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
        CarFeatures carFeature = getUniqueFeatureByName(t.getName());
        if (carFeature == null) {
            super.create(t); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private CarFeatures getUniqueFeatureByName(String name) {
        Criteria crt = session.createCriteria(CarFeatures.class).add(Restrictions.eq("name", name));
        CarFeatures carFeatures = (CarFeatures) crt.uniqueResult();
        return carFeatures;
    }

}
