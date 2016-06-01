/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.datalayerzooba;

import dao.MakeDao;
import dao.ModelDao;
import dao.UserDao;
import facadePkg.DataLayer;
import pojo.Make;
import pojo.Model;
import pojo.Trim;
import pojo.User;
import pojo.Year;

/**
 *
 * @author Ehab
 */
public class MainTest {

    public static void main(String[] args) {

        Model model = new Model();
        Make make = new Make();
        Trim trim = new Trim();
        Year year = new Year();

        make.setName("qwe");
        make.setNiceName("qwe");
        model.setName("qwe");
        model.setNiceName("ert");
        year.setName(0);
        trim.setName("trim name ");

        DataLayer dataLayer = new DataLayer();
        dataLayer.insertVehicle(make, model, year, trim);

    }

}
