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
import pojo.Days;

/**
 *
 * @author repixels
 */
public class DaysDAO extends AbstractDao<Days>
{
    Session session;
    
    public DaysDAO(Session s)
    {
        super(Days.class);
        session = s;
    }

    public Days find(Long id)
    {
        return super.find(Days.class, id);
    }
    
    public List<Days> findAll() throws DataAccessLayerException
    {
        return super.findAll(Days.class);
    }
    
}
