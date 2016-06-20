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

import pojo.Trim;

/**
 *
 * @author yoka
 */
public class TrimDao extends AbstractDao<Trim> {

    Session session;

    public TrimDao(Session s) {
        super(Trim.class, s);
        session = s;
    }

    @Override
    public List<Trim> findByExample(Trim t) {
        return super.findByExample(t); //To change body of generated methods, choose Tools | Templates.
    }

    public Trim find(int id) {
        return super.find(Trim.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Trim t) {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(Trim t) {
        super.saveOrUpdate(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Trim t) {
        Trim trim = getUniqueTrimByName(t.getName());
        if (trim == null) {
            super.create(t); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public List<Trim> getTrimByName(String name) {
        Criteria crt = session.createCriteria(Trim.class).add(Restrictions.like("name", "%" + name + "%"));
        List<Trim> lst = crt.list();

        return lst;

    }

    public List<Trim> getTrimByModel(String model) {
        Criteria crt = session.createCriteria(Trim.class, "trim").
                createAlias("trim.vehicleModels", "vModel")
                .createAlias("vModel.model", "model")
                .add(Restrictions.like("name", "%" + model + "%"));
        List<Trim> lst = crt.list();

        return lst;
    }

    public List<Trim> getTrimByYear(String year) {
        Criteria crt = session.createCriteria(Trim.class, "trim").
                createAlias("trim.vehicleModels", "vModel")
                .createAlias("vModel.year", "year")
                .add(Restrictions.like("name", "%" + year + "%"));
        List<Trim> lst = crt.list();

        return lst;
    }

    public List<Trim> getTrimByYearAndModel(String model, String year) {
        Criteria crt = session.createCriteria(Trim.class, "trim").
                createAlias("trim.vehicleModels", "vModel")
                .createAlias("vModel.year", "year")
                .add(Restrictions.eq("year.name", Integer.parseInt(year)))
                .createAlias("vModel.model", "model")
                .add(Restrictions.like("model.name", "%" + model + "%"));

        List<Trim> lst = crt.list();

        return lst;
    }

    public Trim getUniqueTrimByName(String name) {
        Criteria crt = session.createCriteria(Trim.class).add(Restrictions.like("name", name));
        List<Trim> listTrims = crt.list();
        Trim result = null;

        if (listTrims.size() > 0) {
            result = listTrims.get(0);
        }
        return result;

    }
}
