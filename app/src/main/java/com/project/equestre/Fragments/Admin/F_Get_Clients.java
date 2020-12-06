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

import com.project.equestre.LocalData.ClientsAdapter;
import com.project.equestre.Models.User;
import com.project.equestre.R;


public class F_Get_Clients extends Fragment implements
        ClientsAdapter.customButtonListener {

    private IAClients listener;
    Button btn_Reset;
    Button btn_Search;
    EditText txt_Search;
    View rview;
    Context cx;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_schedule_admin,container,false);
        listener.getClients(rview, F_Get_Clients.this);
        btn_Search=rview.findViewById(R.id.btn_Search);
        txt_Search=rview.findViewById(R.id.txt_Search);
        btn_Reset=rview.findViewById(R.id.btn_Reset);

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_Search){

                    listener.getClientsSearch(rview, F_Get_Clients.this, String.valueOf(txt_Search.getText()));

                }
            }
        });
        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_Reset){
                    txt_Search.setText("");
                    listener.getClients(rview, F_Get_Clients.this);


                }
            }
        });

        return rview;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IAClients){
            listener= (IAClients) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }

    @Override
    public void onButtonClickListner(int position, User value) {

    listener.addClientsSchedule(value);

    }


    public interface IAClients {
        void getClients(View view, F_Get_Clients f_a_C_schedule);
        void getClientsSearch(View view, F_Get_Clients f_a_C_schedule, String name);
        void addClientsSchedule(User value);
    }

}
