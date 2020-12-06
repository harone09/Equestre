package com.project.equestre.Fragments.Monitor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.equestre.LocalData.ScheduleAdapter2;
import com.project.equestre.Models.Schedule;
import com.project.equestre.R;


public class F_M_Schedule extends Fragment implements
        ScheduleAdapter2.customButtonListener, AdapterView.OnItemSelectedListener {

    private IMSchedule listener;
    Button btn_reported;
    Button btn_complete;
    View rview;
    Context cx;
    Spinner spinner;

    String Time=null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_schedule,container,false);
       // listener.getSchedule(rview,F_M_Schedule.this);
        btn_complete=rview.findViewById(R.id.btn_Complete);
        btn_reported=rview.findViewById(R.id.btn_Reported);

        spinner=rview.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.View_by_Time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        return rview;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof F_M_Schedule.IMSchedule){
            listener= (F_M_Schedule.IMSchedule) context;
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
        listener.getSchedule(rview, F_M_Schedule.this, Time);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Time= (String) parent.getItemAtPosition(position);
        listener.getSchedule(rview, F_M_Schedule.this,Time);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface IMSchedule {
        void getSchedule(View view, F_M_Schedule f_m_schedule, String time);
        void setScheduleStatus(String status, String id_S);
    }

}
