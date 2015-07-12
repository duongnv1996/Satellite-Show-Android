package com.example.administrator.statilitesshow;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.GpsSatellite;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by Administrator on 9/7/2015.
 */
public class MyAsyncTask extends AsyncTask<satellite,void,void>{

    @Override
    protected void doInBackground(satellite... params) {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Object doInBackground(Object[] params) {

        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
