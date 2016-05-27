/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Exceptions.DataAccessLayerException;
import abstractDao.AbstractDao;
import java.util.List;
import org.hibernate.Session;
import pojo.User;

/**
 *
 * @author Ehab
 */
public class UserDao extends AbstractDao<User> {

    Session session;

    public UserDao() {
        super(User.class);
    }

    @Override
    public void create(User u) throws DataAccessLayerException {
        super.create(u);
    }

    public List<User> findAll() throws DataAccessLayerException {
        return super.findAll(User.class); //To change body of generated methods, choose Tools | Templates.
    }

    public User find(Long id) throws DataAccessLayerException {
        return super.find(User.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User t) throws DataAccessLayerException {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(User t) throws DataAccessLayerException {
        super.saveOrUpdate(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findByExample(User t) throws DataAccessLayerException {
        return super.findByExample(t);
    }

}
