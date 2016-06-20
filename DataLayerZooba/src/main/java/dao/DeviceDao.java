/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Device;

/**
 *
 * @author yoka
 */
public class DeviceDao extends AbstractDao<Device>{
    Session  session;
    public DeviceDao(Session s) {
         super(Device.class,s);
    session=s;
    }
    public List<Device> getdevicePerUser(int userId)
    {
      Criteria crt = session.createCriteria(Device.class, "device").
                createAlias("device.user", "user")
                .add(Restrictions.eq("user.id",  userId));
        List<Device> lst = crt.list();

        return lst;
    }
    
    public List<Device> getdevicebyToken(String token)
    {
      Criteria crt = session.createCriteria(Device.class, "device")
                .add(Restrictions.eq("token",  token));
        List<Device> lst = crt.list();

        return lst;
    }
}
