/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ehab
 */
public class DayAndTime {

    private String dayName;
    private Date from;
    private Date to;
    private int index;

    public DayAndTime(String dayName, Date from, Date to, int index) {
        this.dayName = dayName;
        this.from = from;
        this.to = to;
        this.index = index;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
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
        DayAndTime theOther = (DayAndTime) obj;
        return this.dayName.equals(theOther.getDayName());
    }

    @Override
    public String toString() {

        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");

        return dayName + " " + ft.format(from) + " " + ft.format(to);
    }

}
