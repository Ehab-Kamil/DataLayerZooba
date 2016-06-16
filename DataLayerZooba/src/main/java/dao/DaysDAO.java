/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import pojo.Days;
import pojo.Device;

/**
 *
 * @author repixels
 */
public class DaysDAO extends AbstractDao<Days> {

    Session session;

    public DaysDAO(Session s) {
        super(Days.class, s);
        session = s;
    }

    public Days find(int id) {
        return super.find(Days.class, id);
    }

    public List<Days> findAll() throws DataAccessLayerException {
        return super.findAll(Days.class);
    }

    public Days getDayByName(String dayName) {
        Days result = null;
        Criteria crt = session.createCriteria(Days.class).add(Restrictions.eq("name", dayName));
        result = (Days) crt.uniqueResult();
        return result;
    }

}
