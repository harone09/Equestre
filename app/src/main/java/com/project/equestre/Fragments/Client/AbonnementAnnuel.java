package com.project.equestre.Fragments.Client;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.equestre.R;

public class AbonnementAnnuel extends Fragment {
    private Iabonnement listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_abonnement_annuel,container,false);
        Button btn_abonnement=view.findViewById(R.id.btn_abonnement);
        btn_abonnement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_abonnement){
                    listener.abonnementRenouvler();
                }
            }
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof AbonnementAnnuel.Iabonnement){
            listener= (AbonnementAnnuel.Iabonnement) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }

    public interface Iabonnement{
         void abonnementRenouvler();

    }




}
