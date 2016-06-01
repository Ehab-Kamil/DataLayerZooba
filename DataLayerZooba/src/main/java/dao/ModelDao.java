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
import pojo.Model;

/**
 *
 * @author yoka
 */
public class ModelDao extends AbstractDao<Model> {

    Session session;

    public ModelDao() {
        super(Model.class);
        session = HibernateFactory.openSession();
    }

    @Override
    public List<Model> findByExample(Model t) {
        return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.
    }

    public Model find(Long id) {
        return super.find(Model.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Model t) {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(Model t) {
        super.saveOrUpdate(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Model t) {
        super.create(t); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Model> getMakeByName(String name) {
        Criteria crt = session.createCriteria(Model.class).add(Restrictions.like("name", "%" + name + "%"));
        List<Model> lst = crt.list();

        return lst;

    }

    public List<Model> getMakeByNiceName(String niceName) {
        Criteria crt = session.createCriteria(Model.class).add(Restrictions.like("nicename", "%" + niceName + "%"));
        List<Model> lst = crt.list();

        return lst;

    }

    public List<Model> getModelsByMake(String make) {
        Criteria crt = session.createCriteria(Model.class, "model").
                createAlias("model.make", "make")
                .add(Restrictions.like("make.name", "%" + make + "%"));
        List<Model> lst = crt.list();
        return lst;
    }

    public List<Model> getModelsByVechileModelYear(String year) {
        Criteria crt = session.createCriteria(Model.class, "model").
                createAlias("model.vehicleModels", "vModel")
                .createAlias("vModel.year", "year")
                .add(Restrictions.like("year.name", "%" + year + "%"));
        List<Model> lst = crt.list();
        return lst;
    }

    public List<Model> getModelsByVechileModelTrim(String trim) {
        Criteria crt = session.createCriteria(Model.class, "model").
                createAlias("model.vehicleModels", "vModel")
                .createAlias("vModel.trim", "trim")
                .add(Restrictions.like("trim.name", "%" + trim + "%"));
        List<Model> lst = crt.list();
        return lst;
    }
}
