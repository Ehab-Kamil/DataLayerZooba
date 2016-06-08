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
import pojo.Year;

/**
 *
 * @author yoka
 */
public class YearDao extends AbstractDao<Year> {

    Session session;

    public YearDao(Session s) {
        super(Year.class,s);
        session =s;
    }

    @Override
    public List<Year> findByExample(Year t) {
        return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.
    }

    public Year find(int id) {
        return super.find(Year.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Year t) {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(Year t) {
        super.saveOrUpdate(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Year t) {
        super.create(t); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Year> findAll() {
        return super.findAll(Year.class); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    public List<Year> getYearByModel(String model) {
        Criteria crt = session.createCriteria(Year.class, "year").
                createAlias("year.vehicleModels", "vModel")
                .createAlias("vModel.model", "model")
                .add(Restrictions.like("model.name", "%" + model + "%"));
        List<Year> lst = crt.list();

        return lst;
    }

    public List<Year> getYearByTrim(String trim) {
        Criteria crt = session.createCriteria(Year.class, "year").
                createAlias("year.vehicleModels", "vModel")
                .createAlias("vModel.trim", "trim")
                .add(Restrictions.like("trim.name", "%" + trim + "%"));
        List<Year> lst = crt.list();

        return lst;
    }

    public List<Year> getYearBytrimAndModel(String trim, String model) {
        Criteria crt = session.createCriteria(Year.class, "year").
                createAlias("year.vehicleModels", "vModel")
                .createAlias("vModel.trim", "trim")
                .add(Restrictions.like("trim.name", "%" + trim + "%"))
                .createAlias("vModel.model", "model")
                .add(Restrictions.like("model.name", "%" + model + "%"));

        List<Year> lst = crt.list();

        return lst;
    }
}
