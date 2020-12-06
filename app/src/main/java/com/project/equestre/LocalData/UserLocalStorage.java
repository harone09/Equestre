package com.project.equestre.LocalData;

import android.content.Context;
import android.content.SharedPreferences;

import com.project.equestre.Models.User;

public class UserLocalStorage {
    public static final String SP_NAME = "userDetails";

    SharedPreferences LocalDb;


    public UserLocalStorage(Context context) {
        this.LocalDb = context.getSharedPreferences(SP_NAME,0);
    }
    public void storeUserData(User user) {
        clearUserData();
        SharedPreferences.Editor LocalDbEditor=LocalDb.edit();
        LocalDbEditor.putLong("id_user",user.getId_user());
        LocalDbEditor.putString("email",user.getEmail());
        LocalDbEditor.putString("password",user.getPassword());
        LocalDbEditor.putString("username",user.getUsername());
        LocalDbEditor.putString("name",user.getName());
        LocalDbEditor.putString("phone",user.getPhone());
        LocalDbEditor.putString("role",user.getRole());
        LocalDbEditor.putString("abonnement",user.getAbonnement());
        LocalDbEditor.putString("reservation",user.getReservation());
        LocalDbEditor.commit();

        }
    public void setUserLoggedIn() {
        SharedPreferences.Editor LocalDbEditor = LocalDb.edit();
        LocalDbEditor.putBoolean("loggedIn", true);
        LocalDbEditor.commit();
    }
    public boolean isLoggedIn(){
        return LocalDb.getBoolean("loggedIn", false);
    }
    public void clearUserData() {
        SharedPreferences.Editor LocalDbEditor = LocalDb.edit();
        LocalDbEditor.clear();
        LocalDbEditor.commit();
    }

    public User getLoggedInUser() {
       if (isLoggedIn()==false) {
            return null;
        }


        User user = new User(
                LocalDb.getLong("id_user",0),
                LocalDb.getString("email", ""),
                LocalDb.getString("password", ""),
                LocalDb.getString("username", ""),
                LocalDb.getString("name", ""),
                LocalDb.getString("phone", ""),
                LocalDb.getString("role", ""),
                LocalDb.getString("abonnement", ""),
                LocalDb.getString("reservation", "")
                             );


        return user;
    }






}
