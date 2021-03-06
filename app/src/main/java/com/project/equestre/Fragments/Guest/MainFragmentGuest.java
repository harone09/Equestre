package com.project.equestre.Fragments.Guest;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.equestre.R;

public class MainFragmentGuest extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        TextView t1= view.findViewById(R.id.textView);
        ImageView iv=view.findViewById(R.id.iv_slideShow);
        AnimationDrawable ad= (AnimationDrawable) iv.getDrawable();
        ad.start();
        t1.append("  Guest");

        return view;
    }
}
