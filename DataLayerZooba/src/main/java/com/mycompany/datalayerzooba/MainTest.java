/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.datalayerzooba;

import dao.UserDao;
import pojo.User;

/**
 *
 * @author Ehab
 */
public class MainTest {
    
    
    public static void main(String[] args) {
        
        User u = new User();
        u.setUsername("Ehab UserName");
        u.setFirstName("Ehab Kamil");
        u.setEmail("Ehab Email");
        u.setPassword("Ehab Password");
        
        UserDao uDao = new UserDao();
        uDao.create(u);
        
    }
    
}
