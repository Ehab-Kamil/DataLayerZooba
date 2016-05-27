/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.MeasuringUnit;
import pojo.Type;

/**
 *
 * @author Mohammed
 */
public class MeasuringUnitDao extends AbstractDao<MeasuringUnit> {
    
    
     private final SessionFactory factory;

    public MeasuringUnitDao(SessionFactory factory) {
        super(MeasuringUnit.class);
        this.factory = factory;
    }
    
     public MeasuringUnit getById(int id) {
        Session session = factory.openSession();

        MeasuringUnit m = (MeasuringUnit) session.createQuery("SELECT m FROM MeasuringUnit m WHERE m.id = :id").setInteger("id", id).uniqueResult();
        session.close();
        return m;
    }

   
     public MeasuringUnit getByName(String name) {
        Session session = factory.openSession();

        MeasuringUnit m = (MeasuringUnit) session.createQuery("SELECT m FROM MeasuringUnit m WHERE m.name = :name").setString("name", name).uniqueResult();
        session.close();
        return m;
    }

     public MeasuringUnit getByMeasuringUnitcol(String measuringUnitcol) {
        Session session = factory.openSession();

        MeasuringUnit m = (MeasuringUnit) session.createQuery("SELECT m FROM MeasuringUnit m WHERE m.measuringUnitcol = :measuringUnitcol").setString("measuringUnitcol", measuringUnitcol).uniqueResult();
        session.close();
        return m;
    }

       public MeasuringUnit getBytype(Type type) {
        Session session = factory.openSession();

        MeasuringUnit m = (MeasuringUnit) session.createQuery("SELECT m FROM MeasuringUnit m , Type t WHERE t.id = :id and t.measuringUnit=m").setInteger("id", type.getId()).uniqueResult();
        session.close();
        return m;
    }

            public MeasuringUnit getBySuffix(String suffix) {
        Session session = factory.openSession();

        MeasuringUnit m = (MeasuringUnit) session.createQuery("SELECT m FROM MeasuringUnit m WHERE m.suffix = :suffix").setString("suffix", suffix).uniqueResult();
        session.close();
        return m;
    }

       
}
