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

public class Add_Task extends Fragment {


    private IAddTask listener;
Schedule sc=null;
    View rview;
    Button btn;

    public Add_Task(Schedule schedule) {
        this.sc=schedule;
    }
    public Add_Task() {
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_add_task,container,false);
        btn=rview.findViewById(R.id.btn_AddTask);

        if(sc!=null){
            String[] date=sc.getDateD().split("-");
            String[] time=sc.getDateT().split(":");

            EditText t1=rview.findViewById(R.id.txt_TYear);
            EditText t2=rview.findViewById(R.id.txt_TMonth);
            EditText t3=rview.findViewById(R.id.txt_TDay);
            EditText t4=rview.findViewById(R.id.txt_THours);
            EditText t5=rview.findViewById(R.id.txt_TMinutes);
            EditText t6=rview.findViewById(R.id.txt_TAdescription);
            EditText t7=rview.findViewById(R.id.txt_TmonitorS);
            EditText t8=rview.findViewById(R.id.txt_Trepetition);

            t1.setText(date[2]);
            t2.setText(date[1]);
            t3.setText(date[0]);
            t4.setText(time[0]);
            t5.setText(time[1]);
            t6.setText(sc.getDescription());
            t7.setText(sc.getMonitor());
            t8.setVisibility(View.INVISIBLE);
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_AddTask){
                    String y=((TextView)rview.findViewById(R.id.txt_TYear)).getText().toString().trim();


                    String m=((TextView)rview.findViewById(R.id.txt_TMonth)).getText().toString().trim();


                    String d=((TextView)rview.findViewById(R.id.txt_TDay)).getText().toString().trim();
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


                    String h=((TextView)rview.findViewById(R.id.txt_THours)).getText().toString().trim();
                    if( h.isEmpty() || Integer.parseInt(h)>23){

                        Toast.makeText(getActivity(),"enter valid hour",Toast.LENGTH_LONG).show();
                        return;
                    }

                    String mi=((TextView)rview.findViewById(R.id.txt_TMinutes)).getText().toString().trim();
                    if(mi.isEmpty() || Integer.parseInt(mi)>59 ){
                        Toast.makeText(getActivity(),"enter valid minutes",Toast.LENGTH_LONG).show();
                        return;
                    }

                    String cl=((TextView)rview.findViewById(R.id.txt_TAdescription)).getText().toString().trim();
                    String mn=((TextView)rview.findViewById(R.id.txt_TmonitorS)).getText().toString().trim();
                    String rep= ((TextView)rview.findViewById(R.id.txt_Trepetition)).getText().toString().trim();
                    if(rep.isEmpty() || rep.equals("0")){
                        rep="1";
                    }

                    Schedule schedule=new Schedule();
                    schedule.setDate(d,m,y,h,mi);
                    schedule.setMonitor(mn);
                    schedule.setDescription(cl);
                    listener.addTask(schedule,rep);



                }
            }
        });


        return rview;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IAddTask){
            listener= (IAddTask) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }



    public interface IAddTask {
        void addTask(Schedule schedule, String rep);

    }


}
