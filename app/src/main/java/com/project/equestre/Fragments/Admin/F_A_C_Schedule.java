package com.project.equestre.Fragments.Admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.equestre.LocalData.ScheduleAdapter2;
import com.project.equestre.Models.Schedule;
import com.project.equestre.R;


public class F_A_C_Schedule extends Fragment implements
        ScheduleAdapter2.customButtonListener {

    private IASchedule listener;
    Button btn_Reset;
    Button btn_Search;
    EditText txt_Search;
    View rview;
    Context cx;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_schedule_admin,container,false);
        listener.getSchedule(rview, F_A_C_Schedule.this);
        btn_Search=rview.findViewById(R.id.btn_Search);
        txt_Search=rview.findViewById(R.id.txt_Search);
        btn_Reset=rview.findViewById(R.id.btn_Reset);

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_Search){

                    listener.getSearch(rview, F_A_C_Schedule.this, String.valueOf(txt_Search.getText()));

                }
            }
        });
        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_Reset){
                    txt_Search.setText("");
                    listener.getSchedule(rview, F_A_C_Schedule.this);


                }
            }
        });

        return rview;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IASchedule){
            listener= (IASchedule) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }

    @Override
    public void onButtonClickListner(int position, Schedule value, String c) {
        if(c=="r"){
            listener.setScheduleStatus("reported", String.valueOf(value.getid_sechedule()));

        }
        else if(c=="c"){
            listener.setScheduleStatus("complete", String.valueOf(value.getid_sechedule()));

        }
        listener.getSchedule(rview, F_A_C_Schedule.this);
    }


    public interface IASchedule {
        void getSchedule(View view, F_A_C_Schedule f_a_C_schedule);
        void getSearch(View view, F_A_C_Schedule f_a_C_schedule, String name);
        void setScheduleStatus(String status, String id_S);
    }

}
