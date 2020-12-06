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
import com.project.equestre.Fragments.Client.AbonnementAnnuel;
import com.project.equestre.Fragments.Client.F_C_Schedule;
import com.project.equestre.Fragments.Client.MainFragmentClient;
import com.project.equestre.Fragments.Client.Profile;
import com.project.equestre.Fragments.Client.ReserverSeance;
import com.project.equestre.Fragments.Guest.F_Local_Schedule;
import com.project.equestre.LocalData.PendingScheduleLocalStorage;
import com.project.equestre.LocalData.ScheduleAdapter;
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

public class MainActivityGuest extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, F_Local_Schedule.ILocalSchedule {

    DrawerLayout drawer;
    TextView DrawerHeaderText;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    MainFragmentClient mainFragmentClient;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    UserLocalStorage userLocalStorage;
    PendingScheduleLocalStorage pendingScheduleLocalStorage;
    User currentUser;
    String URL="http://192.168.0.4:8080/Equestre_WS/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        userLocalStorage=new UserLocalStorage(this);
        pendingScheduleLocalStorage =new PendingScheduleLocalStorage(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        navigationView = findViewById(R.id.nested);
        navigationView.setNavigationItemSelectedListener(this);
        changeDrawerHeader();




        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


         mainFragmentClient = new MainFragmentClient();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, mainFragmentClient);
        fragmentTransaction.commit();// add the fragment

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        closeDrawer();

        switch (menuItem.getItemId()){
            case R.id.home :  loadFragment(new MainFragmentClient())   ;break;
            case R.id.logout :  userLocalStorage.clearUserData();
            startActivity(new Intent(MainActivityGuest.this,Login.class));  break;
            case R.id.schedule: loadFragment(new F_Local_Schedule()); break;
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



    void changeDrawerHeader(){
        final View headerLayout = navigationView.getHeaderView(0);
        DrawerHeaderText = (TextView) headerLayout.findViewById(R.id.textView3);

        DrawerHeaderText.setText("Welcome Guest !!");

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





    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getLSchedule(final View vi, final String time) {
        ArrayList<Schedule> mySchedule=new ArrayList<Schedule>();

        for(Schedule sc: pendingScheduleLocalStorage.getSchedule()){
            Schedule schedule1=sc;
            if(schedule1!=null){
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

        ListView ll=vi.findViewById(R.id.listeSchedule);
        ll.setAdapter(new ScheduleAdapter(vi.getContext(),R.layout.item_c_schedule,mySchedule));




    }
}
