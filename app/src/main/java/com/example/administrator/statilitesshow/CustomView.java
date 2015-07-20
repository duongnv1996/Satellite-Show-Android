package com.example.administrator.statilitesshow;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsSatellite;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/7/2015.
 */
public class CustomView extends View {
    Bitmap bitmapVeTinh, bitVeTinh;
    Bitmap bitmapHinhTron = BitmapFactory.decodeResource(getResources(), R.drawable.laban);
    private float direction = 0;
    ArrayList<GpsSatellite> veTinh;
    Paint paint;

    public ArrayList<GpsSatellite> getVeTinh() {
        return veTinh;
    }

    public void setVeTinh(ArrayList<GpsSatellite> veTinh) {
        this.veTinh = veTinh;
    }

    public CustomView(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        //Xoay theo tam la ban
        canvas.rotate(direction, canvas.getWidth() / 2, canvas.getHeight() / 2);
        for (int i = 0; i < veTinh.size(); i++) {
            //tao hinh ve tinh
            xuLyDuLieu(veTinh.get(i).usedInFix(), veTinh.get(i).getPrn(), veTinh.get(i).getSnr());
            //ve hinh trong + ve tinh
            float mx = setViTri(canvas, bitmapHinhTron, veTinh.get(i).getElevation(), veTinh.get(i).getAzimuth())[0];
            float my = setViTri(canvas, bitmapHinhTron, veTinh.get(i).getElevation(), veTinh.get(i).getAzimuth())[1];
            //ve hinh ve tinh
            canvas.rotate(-direction, mx, my);          //xoay theo tam ve tinh
            canvas.drawBitmap(bitmapVeTinh, mx - bitmapVeTinh.getWidth() / 2, my - (bitmapVeTinh.getHeight() / 2), null);
            //Ve text
            paint.setTextSize(20);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText("" + veTinh.get(i).getPrn(), (mx - bitmapVeTinh.getWidth() / 2), (my - (bitmapVeTinh.getHeight() / 2) + 3 * bitmapVeTinh.getHeight() / 2), paint);
            canvas.rotate(direction, mx, my);   //Xoay nguoc lai theo tam ve tinh
        }
        invalidate();

    }

    public float[] setViTri(Canvas canvas, Bitmap bitmap, float mElevation, float mAzimuth) {
        float mR = bitmap.getWidth() / 2;       //Ban kinh hinh tron duoc luu tam thoi vao bien mR
        mR = (float) ((float) ((float) ((float) (90 - mElevation) / 10) * 1.1 * ((float) mR / 9.9)));
        float m[] = new float[2];
        float mX = (float) (mR * Math.sin(Math.PI * (mAzimuth) / 180));
        m[0] = (canvas.getWidth() / 2) + mX;
        float mY = (float) (mR * Math.cos(Math.PI * (mAzimuth) / 180));
        m[1] = (canvas.getHeight() / 2) - mY;
        return m;
    }

    public void xuLyDuLieu(boolean mIsUse, int mPrn, float mSnr) {
        if (mPrn >= 65) {
            //Lay ra ?nh
            bitVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinhnga);
            //Scale anh
            bitmapVeTinh = Bitmap.createScaledBitmap(bitVeTinh, bitVeTinh.getWidth() / 8, bitVeTinh.getHeight() / 8, true);
            if (mIsUse == false) {
                paint.setColor(Color.GRAY);
            } else if (mIsUse == true) {
                if (mSnr >= 0 && mSnr < 10) {
                    paint.setColor(Color.RED);
                } else if (mSnr >= 10 && mSnr < 20) {
                    paint.setColor(Color.rgb(255, 165, 0));
                } else if (mSnr >= 20 && mSnr < 30) {
                    paint.setColor(Color.YELLOW);
                } else if (mSnr >= 30 && mSnr < 50) {
                    paint.setColor(Color.rgb(181, 230, 29));
                } else if (mSnr >= 50) {
                    paint.setColor(Color.rgb(34, 177, 76));
                }
            }
        } else {
            bitVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinhmi);
            //Scale anh
            bitmapVeTinh = Bitmap.createScaledBitmap(bitVeTinh, bitVeTinh.getWidth() / 8, bitVeTinh.getHeight() / 8, true);
            if (mIsUse == false) {
                paint.setColor(Color.GRAY);
            } else if (mIsUse == true) {
                if (mSnr >= 0 && mSnr < 10) {
                    paint.setColor(Color.RED);
                } else if (mSnr >= 10 && mSnr < 20) {
                    paint.setColor(Color.rgb(255, 165, 0));
                } else if (mSnr >= 20 && mSnr < 30) {
                    paint.setColor(Color.YELLOW);
                } else if (mSnr >= 30 && mSnr < 50) {
                    paint.setColor(Color.rgb(181, 230, 29));
                } else if (mSnr >= 50) {
                    paint.setColor(Color.rgb(34, 177, 76));
                }
            }
        }
    }

    public void setDirection(float direction) {
        this.direction = -direction;
        this.invalidate();
    }

}

class HinhTron extends View {

    private float direction = 0;

    public HinhTron(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(direction, canvas.getWidth() / 2, canvas.getHeight() / 2);
        // R = bitmapHinhtron.getwidth/2
        Bitmap bitmapHinhTron = BitmapFactory.decodeResource(getResources(), R.drawable.laban);
        canvas.drawBitmap(bitmapHinhTron, (canvas.getWidth() / 2) - (bitmapHinhTron.getWidth() / 2), (canvas.getHeight() / 2) - (bitmapHinhTron.getHeight() / 2), null);
        invalidate();

    }

    public void setDirection(float direction) {
        this.direction = -direction;
        this.invalidate();
    }
}

class ThongSo extends View {

    Paint paint = new Paint();
    private int numberOfInView, numberOfInUse;
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snr);

    public void setNumberOfInUse(int numberOfInUse) {
        this.numberOfInUse = numberOfInUse;
    }

    public void setNumberOfInView(int numberOfInView) {
        this.numberOfInView = numberOfInView;
    }

    public ThongSo(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(30);
        Bitmap unscaledBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vetinhnga);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, unscaledBitmap.getWidth() / 8, unscaledBitmap.getHeight() / 8, true);
        // canvas.drawBitmap(scaledBitmap, canvas.getWidth()/2, canvas.getHeight()/2, null);
        canvas.drawText("In View: " + numberOfInView, 30, 30, paint);
        canvas.drawText("In Use: " + numberOfInUse, 30, 60, paint);
        canvas.drawText("SNR Bar :", 30, 3 * canvas.getHeight() / 4 + 30, paint);
        canvas.drawBitmap(bitmap, 30, 3 * canvas.getHeight() / 4 + 60, null);
    }
}