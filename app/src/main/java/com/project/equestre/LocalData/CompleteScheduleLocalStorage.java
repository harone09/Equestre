package com.project.equestre.LocalData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.project.equestre.Models.Schedule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompleteScheduleLocalStorage {
    public static final String SP_NAME = "CompleteSchedules";
    public static int entries=0;

    SharedPreferences LocalDb;

    public CompleteScheduleLocalStorage(Context context) {
        this.LocalDb = context.getSharedPreferences(SP_NAME,0);
    }


    public void storeScheduleData(ArrayList<Schedule> schedule) {
        clearScheduleData();

        SharedPreferences.Editor LocalDbEditor=LocalDb.edit();

        for ( Schedule sc : schedule ) {
            entries++;
            LocalDbEditor.putString(String.valueOf(entries), sc.toString());
        }

        LocalDbEditor.commit();

    }

    public void clearScheduleData() {
        entries=0;
        SharedPreferences.Editor LocalDbEditor = LocalDb.edit();
        LocalDbEditor.clear();
        LocalDbEditor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Schedule> getSchedule() {

        ArrayList<Schedule> mySchedule=new ArrayList<Schedule>() ;
        JSONObject json= null;


        for (int i=1;i<=entries;i++){
            try {
                json = new JSONObject(LocalDb.getString(String.valueOf(i),null));
                Schedule schedule1=new Schedule(json.getString("id_sechedule"),json.getString("Date")
                        ,json.getString("monitor"),json.getString("user"),json.getString("status")
                        ,json.getString("reported"), json.getString("description"),json.getString("task"));
                mySchedule.add(schedule1);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        return mySchedule;
    }

}
