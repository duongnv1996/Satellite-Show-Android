package com.example.administrator.statilitesshow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends Activity implements GpsStatus.Listener, SensorEventListener {
    //tao ra doi tuong sensormanager
    private SensorManager mSensorManager;
    ArrayList<GpsSatellite> veTinh;
    float gocXoay = 0;
    HinhTron h;
    CustomView customView1;
    LocationManager locationManager = null;
    RelativeLayout relativeLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        h = new HinhTron(this);
        customView1 = new CustomView(this);
        // get sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        //Truy cap den location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(this);
        //Khi thay doi GPS
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        //Yeu cau update GPS
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    public void onGpsStatusChanged(int event) {
        GpsStatus gpsStatus;
        gpsStatus = locationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            //relativeLayout.removeAllViewsInLayout();
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            //sat : danh sach cac ve tinh
            Iterator<GpsSatellite> sat = satellites.iterator();
            veTinh = new ArrayList<>();
            String strGpsStats = "";
            int i = 0;
            int numberOfInView = 0;
            int numberOfInUse = 0;
            while (sat.hasNext()) {
                GpsSatellite satellite = sat.next();
                veTinh.add(satellite);
                numberOfInView++;
                if (satellite.usedInFix() == true) {
                    numberOfInUse++;
                }
                ;
            }
            //Ve cac thong so lien quan
            //Ve ve tinh + hinh tron
            veVeTinh(veTinh);
            // Ve cac thong so
            veThongSo(numberOfInView, numberOfInUse);
        }

    }

    public void veVeTinh(ArrayList<GpsSatellite> veTinh) {
        relativeLayout.removeAllViews();
        //ve hinh tron
        relativeLayout.addView(h);
        //ve tung ve tinh
        customView1.setVeTinh(veTinh);
        relativeLayout.addView(customView1);
    }
    private void veThongSo(int numberOfInView, int numberOfInUse) {
        ThongSo thongSo = new ThongSo(this);
        thongSo.setNumberOfInView(numberOfInView);
        thongSo.setNumberOfInUse(numberOfInUse);
        relativeLayout.addView(thongSo);
        thongSo.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSensorChanged(SensorEvent event) {
        //Lay ra goc xoay
        gocXoay = Math.round(event.values[0]);
        h.setDirection(gocXoay);
        customView1.setDirection(gocXoay);

    }

    public float getGocXoay() {
        return gocXoay;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
