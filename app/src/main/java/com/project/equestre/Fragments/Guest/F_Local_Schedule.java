package com.project.equestre.Fragments.Guest;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.project.equestre.LocalData.PendingScheduleLocalStorage;
import com.project.equestre.R;

public class F_Local_Schedule extends Fragment implements AdapterView.OnItemSelectedListener{

    private ILocalSchedule listener;
    PendingScheduleLocalStorage pendingScheduleLocalStorage;
    Spinner spinner;
    View rview;
    String Time=null;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_schedule,container,false);
        //listener.getSchedule(view);
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

        if(context instanceof ILocalSchedule){
            listener= (ILocalSchedule) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Time= (String) parent.getItemAtPosition(position);
        listener.getLSchedule(rview,Time);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface ILocalSchedule {
        void getLSchedule(View view, String time);

    }

}
