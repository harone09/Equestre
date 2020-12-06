package com.project.equestre;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.project.equestre.Fragments.Admin.Add_Note;
import com.project.equestre.Fragments.Admin.F_Get_Notes;
import com.project.equestre.Fragments.Monitor.F_M_Schedule;
import com.project.equestre.Fragments.Monitor.MainFragmentMonitor;
import com.project.equestre.Fragments.Client.Profile;
import com.project.equestre.LocalData.NoteAdapter;
import com.project.equestre.LocalData.NotesLocalStorage;
import com.project.equestre.LocalData.ScheduleAdapter2;
import com.project.equestre.LocalData.PendingScheduleLocalStorage;
import com.project.equestre.LocalData.UserLocalStorage;
import com.project.equestre.Models.Schedule;
import com.project.equestre.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivityMonitor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        F_M_Schedule.IMSchedule, Profile.IProfile, F_Get_Notes.IGetNotes, Add_Note.IAddNote {

    DrawerLayout drawer;
    TextView DrawerHeaderText;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    NotesLocalStorage notesLocalStorage;

    Toolbar toolbar;
    MainFragmentMonitor mainFragmentMonitor;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    UserLocalStorage userLocalStorage;
    PendingScheduleLocalStorage pendingScheduleLocalStorage;
    User currentUser;
    String URL="http://192.168.0.4:8080/Equestre_WS/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_monitor);
        userLocalStorage=new UserLocalStorage(this);
        pendingScheduleLocalStorage =new PendingScheduleLocalStorage(this);
        notesLocalStorage=new NotesLocalStorage(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        navigationView = findViewById(R.id.nested);
        navigationView.setNavigationItemSelectedListener(this);

        final View headerLayout = navigationView.getHeaderView(0);

        if(userLocalStorage.isLoggedIn()){
            changeDrawerHeader();
        }




        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        mainFragmentMonitor = new MainFragmentMonitor();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, mainFragmentMonitor);
        fragmentTransaction.commit();// add the fragment

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        closeDrawer();

        switch (menuItem.getItemId()){
            case R.id.home :  loadFragment(new MainFragmentMonitor())   ;break;
            case R.id.logout :  userLocalStorage.clearUserData(); startActivity(new Intent(MainActivityMonitor.this,Login.class));break;
            case R.id.profile: loadFragment(new Profile()); break;
            case R.id.schedule: loadFragment(new F_M_Schedule()); break;
            case R.id.notes: loadFragment(new F_Get_Notes()); break;

        }

        return true;
    }

    private void loadFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();
    }

    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void getSchedule(final View vi, final F_M_Schedule f_m_schedule, final String time) {



        //  Toast.makeText(Login.this, "click", Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.POST, URL+"getScheduleM.jsp", new Response.Listener<String>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String s) {

                try {
                    ArrayList<Schedule> mySchedule=new ArrayList<Schedule>();
                    ArrayList<JSONObject> listJson=new ArrayList<JSONObject>();
                    String[] liste=s.split(";");
                    for (String l: liste){
                        listJson.add(new JSONObject(l));
                    }

                    if(!listJson.isEmpty() && listJson.get(0).has("error")){
                        Toast.makeText(MainActivityMonitor.this,listJson.get(1).getString("error"),Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(MainActivityMonitor.this, "Getting Schedule Successful", Toast.LENGTH_LONG).show();

                        for(JSONObject json: listJson){
                            Schedule schedule1=new Schedule(json.getString("id_sechedule"),json.getString("Date")
                                    ,json.getString("monitor"),json.getString("user"),json.getString("status")
                                    ,json.getString("reported"),json.getString("description"),json.getString("task"));
                           if(schedule1!=null && schedule1.getStatus().contains("pending")){
                               if(time.contains("This Week")){
                                   if(isDateInCurrentWeek(schedule1.getDate())){
                                       mySchedule.add(schedule1);
                                   }

                               }if(time.contains("Today")){
                                   if(isDateInToday(schedule1.getDate())){
                                       mySchedule.add(schedule1);
                                   }

                               }if(time.contains("Next Week")){
                                   if(isDateInNextWeek(schedule1.getDate())){
                                       mySchedule.add(schedule1);
                                   }

                               }if(time.contains("History")){
                                   if(isDateInHistory(schedule1.getDate())){
                                       mySchedule.add(schedule1);
                                   }
                               }
                           }

                        }

                        pendingScheduleLocalStorage.storeScheduleData(mySchedule);

                        ScheduleAdapter2 adapter=new ScheduleAdapter2(vi.getContext(),R.layout.item_m_schedule, pendingScheduleLocalStorage.getSchedule());
                        adapter.setCustomButtonListner(f_m_schedule);
                        ListView ll=vi.findViewById(R.id.listeSchedule);
                        ll.setAdapter(adapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivityMonitor.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("ID_user", String.valueOf(userLocalStorage.getLoggedInUser().getId_user()));
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(MainActivityMonitor.this);
        rQueue.add(request);





    }

    @Override
    public void setScheduleStatus(final String status, final String id_S) {
        //  Toast.makeText(Login.this, "click", Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.POST, URL+"setScheduleM.jsp", new Response.Listener<String>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String s) {

              if(s.equals("ok")){
                  Toast.makeText(MainActivityMonitor.this, "Modification Successful", Toast.LENGTH_LONG).show();
                  loadFragment(new F_M_Schedule());
              }
              else{
                  Toast.makeText(MainActivityMonitor.this, "Modification failed", Toast.LENGTH_LONG).show();

              }


            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivityMonitor.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("ID_Schedule",id_S);
                parameters.put("Status",status);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(MainActivityMonitor.this);
        rQueue.add(request);





    }

    @Override
    public void editProfile(final User user2) {

        //  Toast.makeText(Login.this, "click", Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.POST, URL+"editProfile.jsp", new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject json=new JSONObject(s);

                    if(json.has("error")){
                        Toast.makeText(MainActivityMonitor.this,json.getString("error"),Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(MainActivityMonitor.this, "Profile Modification Successful", Toast.LENGTH_LONG).show();
                        currentUser= new User(json.getString("id_user") , json.getString("email")  ,
                                json.getString("password") , json.getString("username") ,
                                json.getString("name")  , json.getString("phone") ,
                                json.getString("role"), json.getString("abonnement"),
                                json.getString("reservation"));
                        userLocalStorage.storeUserData(currentUser);
                        userLocalStorage.setUserLoggedIn();
                        changeDrawerHeader();


                        loadFragment(new MainFragmentMonitor());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivityMonitor.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email", user2.getEmail() );
                parameters.put("name", user2.getName() );
                parameters.put("username", user2.getUsername() );
                parameters.put("password", user2.getPassword() );
                parameters.put("phone", user2.getPhone() );
                parameters.put("ID_user", String.valueOf(user2.getId_user()));

                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(MainActivityMonitor.this);
        rQueue.add(request);



    }

    void changeDrawerHeader(){
        final View headerLayout = navigationView.getHeaderView(0);
        DrawerHeaderText = (TextView) headerLayout.findViewById(R.id.textView3);

        DrawerHeaderText.setText("Welcome "+ userLocalStorage.getLoggedInUser().getName()+" !!");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDateInCurrentWeek(LocalDateTime date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        int dd=date.getYear()-1900;
        Date d=new Date(dd,date.getMonthValue()-1,date.getDayOfMonth(),date.getHour(),date.getMinute());
        targetCalendar.setTime(d);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDateInNextWeek(LocalDateTime date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        int dd=date.getYear()-1900;
        Date d=new Date(dd,date.getMonthValue()-1,date.getDayOfMonth(),date.getHour(),date.getMinute());
        targetCalendar.setTime(d);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week+1 == targetWeek && year == targetYear;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDateInToday(LocalDateTime date) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, 0);c.set(Calendar.MINUTE, 0);c.set(Calendar.SECOND, 0);c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();

        int year = date.getYear();int month = date.getMonthValue();int dayOfMonth = date.getDayOfMonth();
        c.set(Calendar.YEAR, year);c.set(Calendar.MONTH, month-1);c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date dateSpecified = c.getTime();

        return today.getYear()==dateSpecified.getYear() && today.getMonth()==dateSpecified.getMonth() && today.getDate()==dateSpecified.getDate();


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDateInHistory(LocalDateTime date) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, 0);c.set(Calendar.MINUTE, 0);c.set(Calendar.SECOND, 0);c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();

        int year = date.getYear();int month = date.getMonthValue();int dayOfMonth = date.getDayOfMonth();
        c.set(Calendar.YEAR, year);c.set(Calendar.MONTH, month-1);c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date dateSpecified = c.getTime();

        if(today.getYear()>dateSpecified.getYear()){
            return  true;
        }
        if(today.getYear()==dateSpecified.getYear() && today.getMonth()>dateSpecified.getMonth())
        {
            return  true;

        }
        if(today.getYear()==dateSpecified.getYear() && today.getMonth()==dateSpecified.getMonth()&& today.getDate()>dateSpecified.getDate() )
        {
            return  true;
        }


        return false;
    }


    @Override
    public void addNote1() {
        loadFragment(new Add_Note());

    }

    @Override
    public void getNotes(View vi, F_Get_Notes f_a_C_schedule) {

        ArrayList<String> stt=new ArrayList<String>();
        for(String ss:notesLocalStorage.getNotes()){
            stt.add(ss);
        }


        NoteAdapter adapter=new NoteAdapter(vi.getContext(),R.layout.item_notes,stt);
        adapter.setCustomButtonListner(f_a_C_schedule);
        ListView ll=vi.findViewById(R.id.listeNote);
        ll.setAdapter(adapter);

    }

    @Override
    public void EditNotes(String value, int p) {
        notesLocalStorage.EditNote(p,value);
        loadFragment(new F_Get_Notes());
    }

    @Override
    public void DeleteNotes(int p) {
        notesLocalStorage.DeleteNote(p);
        loadFragment(new F_Get_Notes());
    }

    @Override
    public void addNote(String note) {
        ArrayList<String> nm=new ArrayList<String>();
        nm.add(note);
        notesLocalStorage.storeNotesData(nm);
        loadFragment(new F_Get_Notes());
    }


}
