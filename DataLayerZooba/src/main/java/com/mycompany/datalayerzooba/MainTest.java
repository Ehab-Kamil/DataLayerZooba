/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.datalayerzooba;

import dao.MakeDao;
import dao.UserDao;
import java.util.ArrayList;
import pojo.Make;
import pojo.User;

/**
 *
 * @author Ehab
 */
public class MainTest {
    
    
    public static void main(String[] args) {
        
//        User u = new User();
//        u.setUsername("Ehab 1UserName");
//        u.setFirstName("Ehab 1Kamil");
//        u.setEmail("Ehab Email1");
//        u.setPassword("Ehab Pas1sword");
//        u.setSuspended(0);
////        
//        UserDao uDao = new UserDao();
//      uDao.create(u);
//       User u=new User();
//       // u.setUsername("aya");
//        u.setPassword("aya");
//        u.setEmail("ai.30111992@yahoo.com");
//      //u.setSuspended(1);
//        ArrayList<User> uarr= (ArrayList<User>) uDao.findByExample(u);
//   User u1= uarr.get(0);
//        System.out.println("uarr "+uarr.size());
//        
MakeDao m1=new MakeDao();
ArrayList<Make> m=new ArrayList<>(m1.findAll());
m.stream().forEach((mm) -> {
    System.out.println("m :"+mm.getName());
        });
    }
    
}
