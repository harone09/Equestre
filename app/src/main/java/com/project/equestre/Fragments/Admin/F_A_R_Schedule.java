package com.project.equestre.Fragments.Admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.equestre.LocalData.ScheduleAdapter3;
import com.project.equestre.Models.Schedule;
import com.project.equestre.R;


public class F_A_R_Schedule extends Fragment implements
        ScheduleAdapter3.customButtonListener,AdapterView.OnItemSelectedListener {

    private IARSchedule listener;
    Button btn_Reset;
    Button btn_Search;
    EditText txt_Search;
    View rview;
    Context cx;
    Spinner spinner;

    String Time=null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_schedule_admin,container,false);
        //listener.getRSchedule(rview, F_A_R_Schedule.this);
        btn_Search=rview.findViewById(R.id.btn_Search);
        txt_Search=rview.findViewById(R.id.txt_Search);
        btn_Reset=rview.findViewById(R.id.btn_Reset);
        spinner=rview.findViewById(R.id.spinner_admin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.View_by_Time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_Search){

                    listener.getRSearch(rview, F_A_R_Schedule.this, String.valueOf(txt_Search.getText()),Time);

                }
            }
        });
        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_Reset){
                    txt_Search.setText("");
                    listener.getRSchedule(rview, F_A_R_Schedule.this, Time);


                }
            }
        });

        return rview;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IARSchedule){
            listener= (IARSchedule) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Time= (String) parent.getItemAtPosition(position);
        listener.getRSchedule(rview, F_A_R_Schedule.this,Time);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onButtonClickListner(int position, Schedule value, String c) {

            listener.MoveSchedule(String.valueOf(value.getid_sechedule()));


    }


    public interface IARSchedule {
        void getRSchedule(View view, F_A_R_Schedule f_a_C_schedule, String time);
        void getRSearch(View view, F_A_R_Schedule f_a_C_schedule, String name, String time);
        void MoveSchedule(String id_S);
    }

}
