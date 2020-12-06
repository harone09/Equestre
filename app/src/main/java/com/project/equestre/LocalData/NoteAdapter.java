package com.project.equestre.LocalData;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.project.equestre.R;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<String> {
    private int res;
    private Context context;
    customButtonListener customListner;
    List<String> lil;

    public interface customButtonListener {
        public void onButtonClickListner(int position, String value, String ss);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
    public customButtonListener getCustomButtonListner() {
        return this.customListner;
    }


    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
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
            viewHolder.button_save=convertView.findViewById(R.id.btn_Save);
            viewHolder.button_delete=convertView.findViewById(R.id.btn_Delete);
            viewHolder.note=convertView.findViewById(R.id.txt_Lnote);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(this.getItem(position)!=null){
            String s=this.getItem(position);
          viewHolder.note.setText(s);


        }

        final View finalConvertView = convertView;
        viewHolder.button_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText note= finalConvertView.findViewById(R.id.txt_Lnote);
                String fsd=note.getText().toString().trim();
                    customListner.onButtonClickListner(position,fsd,"s");


            }
        });
        viewHolder.button_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    customListner.onButtonClickListner(position,getItem(position), "d");


            }
        });

        return convertView;
    }
    public class ViewHolder {
        EditText note;
        Button button_save;
        Button button_delete;
    }


}
