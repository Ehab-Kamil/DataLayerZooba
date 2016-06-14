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

    public ModelDao(Session s) {
        super(Model.class, s);
        session = s;
    }

    public Model find(int id) {
        return super.find(Model.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Model t) {
        Model model = getUniqueModelByName(t.getName());
        if (model == null) {
            super.create(t);
        }
    }

    public List<Model> getModelByMakeAndName(String make, String part) {
        Criteria crt = session.createCriteria(Model.class, "model").
                createAlias("model.make", "make")
                .add(Restrictions.like("make.name", make)).add(Restrictions.like("name", "%" + part + "%"));
        List<Model> lst = crt.list();
        return lst;
    }

    public List<Model> getModelByName(String name) {
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

    public Model getUniqueModelByName(String modelName) {
        Criteria crt = session.createCriteria(Model.class).add(Restrictions.eq("name", modelName));
        Model model = (Model) crt.uniqueResult();
        return model;

    }
}
