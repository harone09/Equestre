package com.project.equestre.Fragments.Admin;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.project.equestre.Models.Schedule;
import com.project.equestre.Models.User;
import com.project.equestre.R;

import java.util.Calendar;

public class Add_Schedule extends Fragment {


    private IAddSchedule listener;
Schedule sc=null;
User user=null;
    View rview;
    Button btn;

    public Add_Schedule(Schedule schedule) {
        this.sc=schedule;
    }
    public Add_Schedule() {
    }

    public Add_Schedule(User value) {
        this.user=value;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_add_schedule,container,false);
        btn=rview.findViewById(R.id.btn_AddSchedule);

        if(user!=null){
            EditText t8=rview.findViewById(R.id.txt_repetition);
            EditText t6=rview.findViewById(R.id.txt_clientS);
            t8.setText(user.getReservation());
            t6.setText(user.getUsername());

        }




        if(sc!=null){
            String[] date=sc.getDateD().split("-");
            String[] time=sc.getDateT().split(":");

            EditText t1=rview.findViewById(R.id.txt_Year);
            EditText t2=rview.findViewById(R.id.txt_Month);
            EditText t3=rview.findViewById(R.id.txt_Day);
            EditText t4=rview.findViewById(R.id.txt_Hours);
            EditText t5=rview.findViewById(R.id.txt_Minutes);
            EditText t6=rview.findViewById(R.id.txt_clientS);
            EditText t7=rview.findViewById(R.id.txt_monitorS);
            EditText t8=rview.findViewById(R.id.txt_repetition);

            t1.setText(date[2]);
            t2.setText(date[1]);
            t3.setText(date[0]);
            t4.setText(time[0]);
            t5.setText(time[1]);
            t6.setText(sc.getUser());
            t7.setText(sc.getMonitor());
            t8.setVisibility(View.INVISIBLE);
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_AddSchedule){
                    String y=((TextView)rview.findViewById(R.id.txt_Year)).getText().toString().trim();


                    String m=((TextView)rview.findViewById(R.id.txt_Month)).getText().toString().trim();


                    String d=((TextView)rview.findViewById(R.id.txt_Day)).getText().toString().trim();
                    if( y.isEmpty() || Integer.parseInt(y)< Calendar.getInstance().get(Calendar.YEAR)){

                        Toast.makeText(getActivity(),"enter valid year",Toast.LENGTH_LONG).show();
                        if(m.isEmpty() || Integer.parseInt(m)< Calendar.getInstance().get(Calendar.MONTH)){

                            Toast.makeText(getActivity(),"enter valid month",Toast.LENGTH_LONG).show();
                            if(d.isEmpty() || Integer.parseInt(d)< Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
                                Toast.makeText(getActivity(),"enter valid day",Toast.LENGTH_LONG).show();
                                return;

                            }
                            return;

                        }
                        return;

                    }


                    String h=((TextView)rview.findViewById(R.id.txt_Hours)).getText().toString().trim();
                    if( h.isEmpty() || Integer.parseInt(h)>23){

                        Toast.makeText(getActivity(),"enter valid hour",Toast.LENGTH_LONG).show();
                        return;
                    }

                    String mi=((TextView)rview.findViewById(R.id.txt_Minutes)).getText().toString().trim();
                    if(mi.isEmpty() || Integer.parseInt(mi)>59 ){
                        Toast.makeText(getActivity(),"enter valid minutes",Toast.LENGTH_LONG).show();
                        return;
                    }

                    String cl=((TextView)rview.findViewById(R.id.txt_clientS)).getText().toString().trim();
                    String mn=((TextView)rview.findViewById(R.id.txt_monitorS)).getText().toString().trim();
                    String rep= ((TextView)rview.findViewById(R.id.txt_repetition)).getText().toString().trim();
                    if(rep.isEmpty() || rep.equals("0")){
                        rep="1";
                    }

                    Schedule schedule=new Schedule();
                    schedule.setDate(d,m,y,h,mi);
                    schedule.setMonitor(mn);
                    schedule.setUser(cl);
                    listener.addSchedule(schedule,rep);



                }
            }
        });


        return rview;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Add_Schedule.IAddSchedule){
            listener= (Add_Schedule.IAddSchedule) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }



    public interface IAddSchedule {
        void addSchedule(Schedule schedule, String rep);

    }


}
