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

    public TrimDao() {
        super(Trim.class);
        session = HibernateFactory.openSession();
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

    public List<Trim> getTrimByYearAndModel(String year, String model) {
        Criteria crt = session.createCriteria(Trim.class, "trim").
                createAlias("trim.vehicleModels", "vModel")
                .createAlias("vModel.year", "year")
                .add(Restrictions.like("year.name", "%" + year + "%"))
                .createAlias("vModel.model", "model")
                .add(Restrictions.like("model.name", "%" + model + "%"));

        List<Trim> lst = crt.list();

        return lst;
    }
}
