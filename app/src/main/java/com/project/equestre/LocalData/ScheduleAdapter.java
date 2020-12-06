package com.project.equestre.LocalData;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.project.equestre.Models.Schedule;
import com.project.equestre.R;

import org.w3c.dom.Text;

import java.util.List;

public class ScheduleAdapter  extends ArrayAdapter<Schedule> {
private int res;
    private Context context;


    public ScheduleAdapter(@NonNull Context context, int resource, @NonNull List<Schedule> objects) {
        super(context, resource, objects);
        this.res=resource;
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view;
       LayoutInflater li= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=li.inflate(res,parent,false);
       TextView dateS=view.findViewById(R.id.txt_DateS);
        TextView heurS=view.findViewById(R.id.txt_heurS);
        TextView monitor=view.findViewById(R.id.txt_monitor);
        TextView id_schedule=view.findViewById(R.id.txt_id_schedule);
        TextView user=view.findViewById(R.id.txt_user);
        if(this.getItem(position)!=null){
            dateS.setText(this.getItem(position).getDateD());
            heurS.setText(this.getItem(position).getDateT());
            monitor.setText(this.getItem(position).getMonitor()+"");
            id_schedule.setText(this.getItem(position).getid_sechedule()+"");
            user.setText(this.getItem(position).getUser()+"");
        }
        return view;
    }
}
