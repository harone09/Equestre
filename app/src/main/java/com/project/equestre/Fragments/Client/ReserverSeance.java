package com.project.equestre.Fragments.Client;

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

import com.project.equestre.R;

public class ReserverSeance extends Fragment {

private Ireservation listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reserver_seance,container,false);
        Button btn_reservation=view.findViewById(R.id.btn_reservation);
        final EditText edit = (EditText)view.findViewById(R.id.txt_nbrReservation);
        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_reservation){
                    listener.reservationConfirmed(edit.getText().toString());
                }
            }
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Ireservation){
            listener= (Ireservation) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }



    public interface Ireservation{
        public void reservationConfirmed(String s);

    }

}
