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

import com.project.equestre.LocalData.NoteAdapter;
import com.project.equestre.Models.User;
import com.project.equestre.R;


public class F_Get_Notes extends Fragment implements
        NoteAdapter.customButtonListener {

    private IGetNotes listener;
    Button btn;
    Button btn_Search;
    EditText txt_Search;
    View rview;
    Context cx;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rview=inflater.inflate(R.layout.fragment_notes,container,false);
        btn=rview.findViewById(R.id.btn_MAddNote);
        listener.getNotes(rview, F_Get_Notes.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addNote1();
            }
        });

        return rview;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IGetNotes){
            listener= (IGetNotes) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }

    @Override
    public void onButtonClickListner(int position, String value, String ss) {
        if(ss.contains("s")){
            listener.EditNotes(value,position);
        }
        if(ss.contains("d")){
            listener.DeleteNotes(position);
        }

        
    }


    public interface IGetNotes {
        void addNote1();
        void getNotes(View view, F_Get_Notes f_a_C_schedule);
        void EditNotes(String value,int p);
        void DeleteNotes(int p);
    }

}
