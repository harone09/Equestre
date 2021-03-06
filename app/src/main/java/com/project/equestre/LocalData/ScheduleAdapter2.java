package com.project.equestre.LocalData;

import android.content.Context;
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

import com.project.equestre.Models.Schedule;
import com.project.equestre.R;

import java.util.List;

public class ScheduleAdapter2  extends ArrayAdapter<Schedule> {
    private int res;
    private Context context;
    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position, Schedule value, String c);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
    public customButtonListener getCustomButtonListner() {
        return this.customListner;
    }


    public ScheduleAdapter2(@NonNull Context context, int resource, @NonNull List<Schedule> objects) {
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
            viewHolder.dateS=convertView.findViewById(R.id.txt_DateS);
            viewHolder.heurS=convertView.findViewById(R.id.txt_heurS);
            viewHolder.monitor=convertView.findViewById(R.id.txt_monitor);
            viewHolder.id_schedule=convertView.findViewById(R.id.txt_id_schedule);
            viewHolder.Description=convertView.findViewById(R.id.txt_Tdescription);
            viewHolder.button_complete=convertView.findViewById(R.id.btn_Complete);
            viewHolder.button_report=convertView.findViewById(R.id.btn_Reported);
            viewHolder.user=convertView.findViewById(R.id.txt_user);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(this.getItem(position)!=null){
            viewHolder.dateS.setText(this.getItem(position).getDateD());
            viewHolder.heurS.setText(this.getItem(position).getDateT());
            viewHolder.monitor.setText(this.getItem(position).getMonitor()+"");
            viewHolder.id_schedule.setText(this.getItem(position).getid_sechedule()+"");
            viewHolder.user.setText(this.getItem(position).getUser()+"");
            viewHolder.Description.setText(this.getItem(position).getDescription());

        }

        viewHolder.button_complete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    customListner.onButtonClickListner(position,getItem(position),"c");


            }
        });
        viewHolder.button_report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    customListner.onButtonClickListner(position,getItem(position), "r");


            }
        });

        return convertView;
    }
    public class ViewHolder {
        TextView dateS;
        TextView heurS;
        TextView monitor;
        TextView Description;
        TextView user;
        TextView id_schedule;
        Button button_complete;
        Button button_report;
    }


}
