package com.example.administrator.statilitesshow;

import android.app.Activity;
import android.location.GpsSatellite;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 18/7/2015.
 */
public class MyAdapter extends ArrayAdapter<GpsSatellite> {
    Activity context = null;
    ArrayList<GpsSatellite> myArray = null;
    int layoutId;

    public MyAdapter(Activity context, int layoutId, ArrayList<GpsSatellite> gpsSatellites) {
        super(context, layoutId, gpsSatellites);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = gpsSatellites;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Lay giao dien tu listview qua
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        GpsSatellite gpsSatellite = myArray.get(position);
        TextView tvSat = (TextView) convertView.findViewById(R.id.tvSat);
        TextView tvSnr = (TextView) convertView.findViewById(R.id.tvSnr);
        ImageView imgIsUse = (ImageView) convertView.findViewById(R.id.imgIsUse);
        TextView tvAzimuth = (TextView) convertView.findViewById(R.id.tvAzimuth);
        TextView tvElevation = (TextView) convertView.findViewById(R.id.tvElevation);
        //set du lieu
        tvSat.setText("" + gpsSatellite.getPrn());
        tvSnr.setText("" + gpsSatellite.getSnr());
        if (gpsSatellite.usedInFix() == true) {
            imgIsUse.setImageResource(R.drawable.used);
            imgIsUse.setMaxWidth(16);
            imgIsUse.setMaxHeight(16);
        } else {
            imgIsUse.setImageResource(R.drawable.noneused);
            imgIsUse.setMaxWidth(16);
            imgIsUse.setMaxHeight(16);
        }

        tvAzimuth.setText("" + gpsSatellite.getAzimuth());
        tvElevation.setText("" + gpsSatellite.getElevation());

        return (convertView);
    }
}
