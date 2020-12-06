package com.project.equestre.LocalData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.project.equestre.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotesLocalStorage {
    public static final String SP_NAME = "Notes";
    public static  int entries;

    SharedPreferences LocalDb;

    public NotesLocalStorage(Context context) {
        this.LocalDb = context.getSharedPreferences(SP_NAME,0);
    }


    public void storeNotesData(ArrayList<String> notes) {

        SharedPreferences.Editor LocalDbEditor=LocalDb.edit();

        for ( String u : notes ) {
            entries++;
            LocalDbEditor.putString(String.valueOf(entries), u);
        }

        LocalDbEditor.commit();

    }

    public void clearNotesData() {
        entries=0;
        SharedPreferences.Editor LocalDbEditor = LocalDb.edit();
        LocalDbEditor.clear();
        LocalDbEditor.commit();
    }
    public void DeleteNote(int e) {
        e++;
        SharedPreferences.Editor LocalDbEditor = LocalDb.edit();
        LocalDbEditor.remove(e+"");
        LocalDbEditor.commit();
    }
    public void EditNote(int e,String s) {
        e++;
        SharedPreferences.Editor LocalDbEditor = LocalDb.edit();
        LocalDbEditor.remove(e+"");
        LocalDbEditor.putString(e+"", s);
        LocalDbEditor.commit();
    }


    public ArrayList<String> getNotes() {

        ArrayList<String> notes=new ArrayList<String>() ;

        for (int i=1;i<=entries;i++){
            if(LocalDb.getString(String.valueOf(i),"")!=""){
                notes.add(LocalDb.getString(String.valueOf(i),""));
            }



        }



        return notes;
    }

}
