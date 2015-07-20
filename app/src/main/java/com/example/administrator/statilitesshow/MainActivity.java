package com.example.administrator.statilitesshow;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends Activity implements GpsStatus.Listener, SensorEventListener {
    //tao ra doi tuong sensormanager
    private SensorManager mSensorManager;
    ArrayList<GpsSatellite> veTinh;
    float gocXoay = 0;
    HinhTron hinhTron;
    ThongSo thongSo;
    CustomView customView1;
    LocationManager locationManager = null;
    ImageView imageView;
    TextView txt, tvInUse, tvInView, tvUsa, tvRussia, tvLongitude, tvLatitude, tvAltitude, tvAccuracy, tvSpeed, tvBearing;
    Button btnInfor;
    TableRow tbRow;
    View dongKe_1, dongKe_2;
    RelativeLayout relativeLayout;
    private double mLongitude, mLatitude, mAccuracy, mSpeed, mAltitude, mBearing;
    private boolean btnCheckVal;
    MyAdapter myAdapter = null;
    ListView listItem;
    ArrayList<String> arrayList = null;
    ArrayAdapter<String> arrayAdapter = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        khoiTaoGiaTri();

        //Set su kien nhan btn
        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCheckVal == true) {
                    HienLaBan();
                } else {
                    HienBangDS();

                }
            }
        });
        //Truy cap den location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(this);
        //Khi thay doi GPS
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mLongitude = location.getLongitude();
                mLongitude = (double) Math.round(mLongitude * 10000) / 10000;
                mLatitude = location.getLatitude();
                mLatitude = (double) Math.round(mLatitude * 10000) / 10000;
                mAccuracy = location.getAccuracy();
                mAltitude = location.getAltitude();
                mAltitude = (double) Math.round(mAltitude * 10000) / 10000;
                mBearing = location.getBearing();
                mBearing = (double) Math.round(mBearing * 10000) / 10000;
                mSpeed = location.getSpeed();
                mSpeed = (double) Math.round(mSpeed * 10000) / 10000;
                InDuLieuToaDo();
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

    private void HienLaBan() {
        btnCheckVal = false;
        relativeLayout.addView(hinhTron);
        listItem.setVisibility(View.INVISIBLE);
        tbRow.setVisibility(View.INVISIBLE);
        dongKe_1.setVisibility(View.INVISIBLE);
        dongKe_2.setVisibility(View.INVISIBLE);

    }

    private void HienBangDS() {
        btnCheckVal = true;
        relativeLayout.removeView(hinhTron);
        relativeLayout.removeView(customView1);
        listItem.setVisibility(View.VISIBLE);
        tbRow.setVisibility(View.VISIBLE);
        dongKe_1.setVisibility(View.VISIBLE);
        dongKe_2.setVisibility(View.VISIBLE);

    }

    private void khoiTaoGiaTri() {
        hinhTron = new HinhTron(this);
        customView1 = new CustomView(this);
        thongSo = new ThongSo(this);
        // get sensor manager

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        tvInUse = (TextView) findViewById(R.id.tvInUse);
        tvInView = (TextView) findViewById(R.id.tvInView);
        imageView = (ImageView) findViewById(R.id.snrBar);
        tvUsa = (TextView) findViewById(R.id.tvUsa);
        tvRussia = (TextView) findViewById(R.id.tvRussia);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvAltitude = (TextView) findViewById(R.id.tvAltitude);
        tvAccuracy = (TextView) findViewById(R.id.tvAccuracy);
        tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        tvBearing = (TextView) findViewById(R.id.tvBearing);
        btnInfor = (Button) findViewById(R.id.btnInfor);
        btnCheckVal = false;
        listItem = (ListView) findViewById(R.id.listItem);
        listItem.setVisibility(View.INVISIBLE);
        tbRow = (TableRow) findViewById(R.id.tbRow);
        tbRow.setVisibility(View.INVISIBLE);
        dongKe_1 = (View) findViewById(R.id.dongKe_1);
        dongKe_1.setVisibility(View.INVISIBLE);
        dongKe_2 = (View) findViewById(R.id.dongKe_2);
        dongKe_2.setVisibility(View.INVISIBLE);

    }
    public String chuyenDoiDuLieu(double degree){
        String string="",str="";
       // string+= (char) 0x00B0+;
        string=Double.toString(degree);
        for (int index = 0; index < string.length() ; index++) {
            if (string.charAt(index)=='.'){
               // string=string.replace(string.charAt(index),(char) 0x00B0);
               str=string.substring(0, index)+ ""+(char) 0x00B0+string.substring(index+1,index+3)+"'"+string.substring(index+3)+"\"";
                break;
            }
        }
        return str;
    }
    private void InDuLieuToaDo() {
        tvLongitude.setTextColor(Color.WHITE);
        tvLongitude.setTextSize(18);
        tvLongitude.setText("Longtitude:" + chuyenDoiDuLieu(mLongitude));
        tvLatitude.setTextColor(Color.WHITE);
        tvLatitude.setTextSize(18);
        tvLatitude.setText("Latitude:" +   chuyenDoiDuLieu(mLatitude));
        tvAltitude.setTextColor(Color.WHITE);
        tvAltitude.setTextSize(18);
        tvAltitude.setText("Altitude: " + mAltitude+" (m)");
        tvAccuracy.setTextColor(Color.WHITE);
        tvAccuracy.setTextSize(18);
        tvAccuracy.setText("Accuracy: " + mAccuracy+" (m)");
        tvBearing.setTextColor(Color.WHITE);
        tvBearing.setTextSize(18);
        tvBearing.setText("Bearing: " + mBearing);
        tvSpeed.setTextColor(Color.WHITE);
        tvSpeed.setTextSize(18);
        tvSpeed.setText("Speed: " + mSpeed+" (m/s)");
    }

    public void onGpsStatusChanged(int event) {
        GpsStatus gpsStatus;
        gpsStatus = locationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            //relativeLayout.removeAllViewsInLayout();
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            //sat : danh sach cac ve tinh
            Iterator<GpsSatellite> sat = satellites.iterator();
            veTinh = new ArrayList<GpsSatellite>();
            myAdapter = new MyAdapter(this, R.layout.list_item, veTinh);
            listItem.setAdapter(myAdapter);
            String strGpsStats = "";
            int i = 0;
            int numberOfInView = 0, numberOfUsaSat = 0, numberOfRusSat = 0;
            int numberOfInUse = 0;


            while (sat.hasNext()) {
                GpsSatellite satellite = sat.next();
                veTinh.add(satellite);
                strGpsStats += i++ + "  PRN: " + satellite.getPrn() + ", Snr: " + satellite.getSnr() + ", IsUse" + satellite.usedInFix() +
                        ", Azimuth: " + satellite.getAzimuth() + ", Elevation: " + satellite.getElevation() + "\n";
                numberOfInView++;
                if (satellite.getPrn() >= 65) {
                    numberOfRusSat++;
                } else {
                    numberOfUsaSat++;
                }
                if (satellite.usedInFix() == true) {
                    numberOfInUse++;
                }
                ;
            }

            InThongSo(numberOfInView, numberOfInUse, numberOfUsaSat, numberOfRusSat);
            //Ve ve tinh + hinh tron
            if (btnCheckVal == false) {
                veVeTinh(veTinh);
            } else {
                myAdapter.notifyDataSetChanged();
            }
        }

    }

    private void InThongSo(int numberOfInView, int numberOfInUse, int numberOfUsaSat, int numberOfRusSat) {
        tvInUse.setTextSize(20);
        tvInUse.setTextColor(Color.WHITE);
        tvInUse.setText("In Use :" + numberOfInUse);
        tvInView.setTextSize(20);
        tvInView.setTextColor(Color.WHITE);
        tvInView.setText("In View :" + numberOfInView);
        tvUsa.setTextColor(Color.WHITE);
        tvUsa.setTextSize(20);
        tvUsa.setText(": " + numberOfUsaSat);
        tvRussia.setTextColor(Color.WHITE);
        tvRussia.setTextSize(20);
        tvRussia.setText(": " + numberOfRusSat);
    }

    public void veVeTinh(ArrayList<GpsSatellite> veTinh) {
        //relativeLayout.removeAllViews();
        //ve hinh tron
        relativeLayout.removeView(hinhTron);
        relativeLayout.addView(hinhTron);
        //ve tung ve tinh
        relativeLayout.removeView(customView1);
        customView1.setVeTinh(veTinh);
        relativeLayout.addView(customView1);
    }

    private void veThongSo(int numberOfInView, int numberOfInUse) {

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
        hinhTron.setDirection(gocXoay);
        customView1.setDirection(gocXoay);

    }

    public float getGocXoay() {
        return gocXoay;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
