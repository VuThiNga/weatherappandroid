package com.example.ngagerrard.weatherappandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ngagerrard.weatherappandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nga Gerrard on 28/03/2017.
 */
public class AdapterChooseAddress extends ArrayAdapter {
    private Context context;
    private ArrayList<String> list;

    public AdapterChooseAddress(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProvinceHolder item;
        if(convertView == null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_city, null);
            item = new ProvinceHolder();
            item.txtName = (TextView)convertView.findViewById(R.id.textViewtinhThanh);
            item.txtStartChar = (TextView)convertView.findViewById(R.id.textFirstChar);
            convertView.setTag(item);
        }else {
            item = (ProvinceHolder) convertView.getTag();
        }

        String province = list.get(position);
        item.txtName.setText(province);
        item.txtStartChar.setText(province.charAt(0) + "");
        return convertView;
    }

    private class ProvinceHolder{
        private TextView txtName, txtStartChar;
    }
}
