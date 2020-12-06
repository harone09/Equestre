package com.project.equestre.Models;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Schedule {

    private  long id_sechedule;
    private LocalDateTime date ;
    private  String monitor ;
    private  String user ;
    private  String status ;
    private  boolean reported ;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getTask() {
        return task;
    }

    public void setTask(boolean task) {
        this.task = task;
    }

    private  String description ;
    private  boolean task ;

    public Schedule(long id_sechedule, LocalDateTime date, String monitor, String user, String status, boolean reported,String description,boolean task ) {
        this.id_sechedule = id_sechedule;
        this.date = date;
        this.monitor = monitor;
        this.user = user;
        this.status=status;
        this.reported=reported;
        this.description = description;
        this.task = task;
    }
    public Schedule(Schedule s) {
        this.id_sechedule = s.id_sechedule;
        this.date = s.date;
        this.monitor = s.monitor;
        this.user = s.user;
        this.status=s.status;
        this.reported=s.reported;
        this.description = s.description;
        this.task = s.task;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Schedule(String id_sechedule, String date, String monitor, String user, String status, String reported,String description , String task) {
        this.id_sechedule = Long.valueOf(id_sechedule);
        this.date = LocalDateTime.parse(date) ;
        this.monitor = monitor;
        this.user = user;
        this.status=status;
        this.reported=Boolean.parseBoolean(reported);
        this.description = description;
        if(task.equals("1")){
            this.task = true;

        }
        else{
            this.task = false;
        }
    }

    public Schedule() {

    }

    public long getid_sechedule() {
        return id_sechedule;
    }

    public void setid_sechedule(long id_sechedule) {
        this.id_sechedule = id_sechedule;
    }

    public LocalDateTime getDate() {
        return date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDateD() {
        return date.getDayOfMonth()+"-"+date.getMonthValue()+"-"+date.getYear();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDateT() {
        String t;

        if(date.getMinute()<=9){
            t="0"+date.getMinute();
       }
        else{
            t=date.getMinute()+"";
        }


        return date.getHour()+":"+t;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDate(String d, String mo, String y, String h, String min) {
        if(Integer.parseInt(d)<9 && !d.contains("0")) d="0"+d;
        if(Integer.parseInt(mo)<9 && !mo.contains("0")) mo="0"+mo;
        if(Integer.parseInt(h)<9 && !h.contains("0")) h="0"+h;
        if(Integer.parseInt(min)<9 && !min.contains("0")) min="0"+min;
        if(Integer.parseInt(min)==0 && !min.contains("00")) min="0"+min;

        String s;


        s = LocalDateTime.parse(y+"-"+mo+"-"+d+"T"+h+":"+min).toString();

        this.date=LocalDateTime.parse(s);


    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "{" + "id_sechedule='" + id_sechedule +
                '\'' + ", Date='" + date + '\'' + ", monitor='" + monitor + '\'' +
                ", user='" + user + '\'' +", status='" + status + '\'' +", reported='" + reported +
                '\'' +", task='" +task + '\'' +", description='" + description + '\'' + '}';
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public boolean getReported() {
        return reported;
    }
    public void setReported(boolean reported) {
        this.reported = reported;
    }
}
