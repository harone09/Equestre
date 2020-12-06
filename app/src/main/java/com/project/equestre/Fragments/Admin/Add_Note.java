package com.project.equestre.Fragments.Admin;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.project.equestre.Models.Schedule;
import com.project.equestre.R;

public class Add_Note extends Fragment {


    private IAddNote listener;
    View rview;
    EditText txt;
    Button btn;

    public Add_Note() {
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_add_note,container,false);
        btn=rview.findViewById(R.id.btn_AddNote);
        txt=rview.findViewById(R.id.txt_Note);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                listener.addNote(txt.getText().toString());
            }


        });

        return rview;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IAddNote){
            listener= (IAddNote) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }



    public interface IAddNote {
        void addNote(String note);

    }


}
