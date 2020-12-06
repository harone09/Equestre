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

import com.project.equestre.LocalData.UserLocalStorage;
import com.project.equestre.MainActivityClient;
import com.project.equestre.Models.User;
import com.project.equestre.R;

public class Profile extends Fragment {
    private IProfile listener;

    Button btn;
    EditText name;
    EditText username;
    EditText email;
    EditText password;
    EditText phone;
    UserLocalStorage userLocalStorage;
    Long id_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        btn=view.findViewById(R.id.bnt_Profilesave);
        name=view.findViewById(R.id.txt_Pname);
        username=view.findViewById(R.id.txt_Pusername);
        email=view.findViewById(R.id.txt_Pemail);
        password=view.findViewById(R.id.txt_Ppassword);
        phone =view.findViewById(R.id.txt_Pphone);
        userLocalStorage=new UserLocalStorage(this.getContext());
        final User user=userLocalStorage.getLoggedInUser();

         name.setText(user.getName());
         username.setText(user.getUsername());
         email.setText(user.getEmail());
         password.setText(user.getPassword());
         phone.setText(user.getPhone());
         id_user=user.getId_user();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.bnt_Profilesave){

                    User user1=new User(id_user,email.getText().toString(),
                            password.getText().toString().trim(),
                            username.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),null,null,null);
                    listener.editProfile(user1);
                }
            }
        });


        return view;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Profile.IProfile){
            listener= (Profile.IProfile) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listener");
        }

    }


    public interface IProfile{
        void editProfile(User user);

    }

}
