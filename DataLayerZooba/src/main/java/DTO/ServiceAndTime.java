/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import pojo.Service;

/**
 *
 * @author Ehab
 */
public class ServiceAndTime {

    private String serviceName;
    private Date from;
    private Date to;
    private int index;

    public ServiceAndTime(String serviceName, Date from, Date to, int index) {
        this.serviceName = serviceName;
        this.from = from;
        this.to = to;
        this.index = index;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object obj) {
        ServiceAndTime theOther = (ServiceAndTime) obj;
        return this.serviceName.equals(theOther.serviceName);
    }

    @Override
    public String toString() {

        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");

        return serviceName + " " + ft.format(from) + " " + ft.format(to);
    }

}
