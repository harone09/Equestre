package com.project.equestre.LocalData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.project.equestre.Models.Schedule;
import com.project.equestre.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientsLocalStorage {
    public static final String SP_NAME = "ClientsList";
    public static int entries=0;

    SharedPreferences LocalDb;

    public ClientsLocalStorage(Context context) {
        this.LocalDb = context.getSharedPreferences(SP_NAME,0);
    }


    public void storeClientsData(ArrayList<User> users) {
        clearClientsData();

        SharedPreferences.Editor LocalDbEditor=LocalDb.edit();

        for ( User u : users ) {
            entries++;
            LocalDbEditor.putString(String.valueOf(entries), u.toString());
        }

        LocalDbEditor.commit();

    }

    public void clearClientsData() {
        entries=0;
        SharedPreferences.Editor LocalDbEditor = LocalDb.edit();
        LocalDbEditor.clear();
        LocalDbEditor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<User> getClients() {

        ArrayList<User> myUser=new ArrayList<User>() ;
        JSONObject json= null;


        for (int i=1;i<=entries;i++){
            try {
                json = new JSONObject(LocalDb.getString(String.valueOf(i),null));
                User User1=new User(json.getString("id_user"),json.getString("username")
                        ,json.getString("password"),json.getString("name"),json.getString("email")
                        ,json.getString("phone"),json.getString("role"),json.getString("abonnement"),json.getString("reservation"));
                myUser.add(User1);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        return myUser;
    }

}
