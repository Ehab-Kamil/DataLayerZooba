/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServicesHandlers;

import Exceptions.DataAccessLayerException;
import abstractDao.HibernateFactory;
import dao.MakeDao;
import dao.ModelDao;
import dao.TrimDao;
import dao.UserDao;
import dao.YearDao;
import facadePkg.DataLayer;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Make;
import pojo.Model;
import pojo.Trim;
import pojo.User;
import pojo.Year;

/**
 *
 * @author yoka
 */
public class Handler {

    Session session;
    UserDao uDao;

    public Handler() {
        session = HibernateFactory.openSession();
        uDao = new UserDao(session);
    }

    public User login(String user, String pass) {
        User u = new User();
        u.setUsername(user);
        u.setPassword(pass);

        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        if (uarr.size() > 0) {
            User u1 = uarr.get(0);
            return u1;
        } else {
            return null;
        }
    }

    public boolean userExists(String user) {
        User u = new User();
        u.setUsername(user);
        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        return uarr.size() > 0;
    }

    public int register(User u) {
        uDao.create(u);
        return 0;
    }

    public List<Make> getMake() {
        Session session = HibernateFactory.openSession();
        List<Make> result = new ArrayList<>();
        MakeDao makeDao = new MakeDao(session);
        result = makeDao.findAll();
        return result;
    }

    public List<Model> getModelByMake(String make) {
        Session session = HibernateFactory.openSession();
        ModelDao modelDao = new ModelDao(session);
        List<Model> result = modelDao.getModelsByMake(make);
        return result;
    }

    public List<Trim> getTrim(String model, String year) {
        Session session = HibernateFactory.openSession();
        TrimDao trimDao = new TrimDao(session);
        return trimDao.getTrimByYearAndModel(model, year);

    }

    public List<Year> getYear(String model) {
        Session session = HibernateFactory.openSession();
        YearDao yearDao = new YearDao(session);
        return yearDao.getYearByModel(model);

    }

    public boolean addVehicle(String make, String model, String year, String trim) {
        boolean result = false;
        try {
            DataLayer dataLayer = new DataLayer();
            Make newMake = new Make(make, make);
            Model newModel = new Model(newMake, model, model);
            Year newYear = new Year(Integer.parseInt(year));
            Trim newTrim = new Trim(trim);
            dataLayer.insertVehicle(newMake, newModel, newYear, newTrim);
            result = true;
        } catch (DataAccessLayerException ex) {
            result = false;
        }
        return result;
    }

    public User loginByEmail(String email, String pass) {
        User u = new User();
        u.setEmail(email);
        u.setPassword(pass);
//        uDao = new UserDao();
        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        if (uarr.size() > 0) {
            User u1 = uarr.get(0);
            return u1;
        } else {
            return null;
        }
    }
//    String forgetPassword()
//    {
//        
//    }

    public boolean emailExists(String email) {
        User u = new User();
        u.setEmail(email);
        ArrayList<User> uarr = (ArrayList<User>) uDao.findByExample(u);
        return uarr.size() > 0;
    }

}
