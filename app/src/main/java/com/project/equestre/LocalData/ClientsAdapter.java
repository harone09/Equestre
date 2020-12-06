package com.project.equestre.LocalData;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.project.equestre.Models.Schedule;
import com.project.equestre.Models.User;
import com.project.equestre.R;

import java.util.List;

public class ClientsAdapter extends ArrayAdapter<User> {
    private int res;
    private Context context;
    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position, User value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
    public customButtonListener getCustomButtonListner() {
        return this.customListner;
    }


    public ClientsAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.res=resource;
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=null;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, null);
            viewHolder = new ViewHolder();
            viewHolder.txt_ClientUserName=convertView.findViewById(R.id.txt_ClientUserName);
            viewHolder.txt_ClientName=convertView.findViewById(R.id.txt_ClientName);
            viewHolder.txt_ClientEmail=convertView.findViewById(R.id.txt_ClientEmail);
            viewHolder.txt_ClientNumber=convertView.findViewById(R.id.txt_ClientNumber);
            viewHolder.txt_reservations=convertView.findViewById(R.id.txt_reservations);
            viewHolder.btn_AddScheduleClient=convertView.findViewById(R.id.btn_AddScheduleClient);
            viewHolder.btn_abonnementView=convertView.findViewById(R.id.btn_abonnementView);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(this.getItem(position)!=null){
            viewHolder.txt_ClientUserName.setText(this.getItem(position).getUsername());
            viewHolder.txt_ClientName.setText(this.getItem(position).getName());
            viewHolder.txt_ClientEmail.setText(this.getItem(position).getEmail());
            viewHolder.txt_ClientNumber.setText(this.getItem(position).getPhone());
            viewHolder.txt_reservations.setText("Reservation : "+this.getItem(position).getReservation());
            if(this.getItem(position).getAbonnement().equals("1")){
                viewHolder.btn_abonnementView.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.abonnement_view_color));
           }
        }

        viewHolder.btn_AddScheduleClient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    customListner.onButtonClickListner(position,getItem(position));


            }
        });

        return convertView;
    }
    public class ViewHolder {
        TextView txt_ClientUserName;
        TextView txt_ClientName;
        TextView txt_ClientEmail;
        TextView txt_ClientNumber;
        TextView txt_reservations;
        Button btn_AddScheduleClient;
        Button btn_abonnementView;

    }


}
