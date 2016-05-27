/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.MeasuringUnit;
import pojo.Service;
import pojo.Type;

/**
 *
 * @author Mohammed
 */
public class TypeDao extends AbstractDao<Type> {
    
    private final SessionFactory factory;
    
    public TypeDao(SessionFactory factory) {
        super(Type.class);
        this.factory = factory;
    }
    
    public Type getById(int id) {
        Session session = factory.openSession();
        
        Type t = (Type) session.createQuery("SELECT t FROM Type t WHERE t.id = :id").setInteger("id", id).uniqueResult();
        session.close();
        return t;
    }
    
    public List<Type> getByMeasuringUnit(MeasuringUnit measuringUnit) {
        Session session = factory.openSession();
        
        List<Type> list = session.createQuery("SELECT t FROM Type t WHERE t.measuringUnit.id = :id").setInteger("id", measuringUnit.getId()).list();
        session.close();
        return list;
    }
    
    public List<Type> getByService(Service service) {
        Session session = factory.openSession();
        
        List<Type> list = session.createQuery("SELECT t FROM Type t WHERE t.service = :id").setInteger("id", service.getId()).list();
        session.close();
        return list;
    }
    
}
