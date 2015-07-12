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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/7/2015.
 */
public class CustomView extends View  {
    private float mAzimuth;
    private float mElevation;
    private int mPrn;
    private float mSnr;
    private boolean mIsUse;
    Bitmap bitmapVeTinh;
    Bitmap bitmapHinhTron = BitmapFactory.decodeResource(getResources(), R.drawable.hinhtronc);
    private float direction=0;

    public CustomView(Context context) {
        super(context);
    }
    public void setmAzimuth(float mAzimuth) {
        this.mAzimuth = mAzimuth;
    }

    public void setmElevation(float mElevation) {
        this.mElevation = mElevation;
    }

    public void setmPrn(int mPrn) {
        this.mPrn = mPrn;
    }

    public void setmSnr(float mSnr) {
        this.mSnr = mSnr;
    }

    public void setmIsUse(boolean mIsUse) {
        this.mIsUse = mIsUse;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //xoay theo sensor
        canvas.rotate(direction, canvas.getWidth() / 2, canvas.getHeight() / 2);
        //tao hinh ve tinh
        xuLyDuLieu();
        //ve hinh trong + ve tinh
        float mx = setViTri(canvas, bitmapHinhTron)[0];
        float my = setViTri(canvas, bitmapHinhTron)[1];
        //ve hinh ve tinh
        canvas.drawBitmap(bitmapVeTinh, mx - bitmapVeTinh.getWidth() / 4, my - (bitmapVeTinh.getHeight() / 2), null);
        //Ve text
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        canvas.drawText("" + mPrn, (mx - bitmapVeTinh.getWidth() / 2) + bitmapVeTinh.getWidth() / 2, (my - (bitmapVeTinh.getHeight() / 2) + 2 * bitmapVeTinh.getHeight()), paint);
        canvas.rotate(direction);
        invalidate();

    }
    public float[] setViTri(Canvas canvas, Bitmap bitmap) {
        float mR = bitmap.getWidth() / 2;
        mR = (float) ((float) mR - ((mElevation * mR / 4) / 22.5));
        float m[] = new float[2];
        float mX = (float) (mR * Math.cos(Math.PI * mAzimuth / 180));
        m[0] = (canvas.getWidth() / 2) - mX;
        float mY = (float) (mR * Math.sin(Math.PI * mAzimuth / 180));
        m[1] = (canvas.getHeight() / 2) - mY;
        return m;
    }
    public void xuLyDuLieu() {
        if (mPrn >= 65) {
            if (mIsUse == false) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_xam65);
            } else if (mSnr >= 0 && mSnr <= 10) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_do65);
            } else if (mSnr > 10 && mSnr <= 20) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_cam65);
            } else if (mSnr > 20 && mSnr <= 30) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_vang65);
            } else if (mSnr > 30 && mSnr <= 50) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_xanh65);
            } else if (mSnr > 50) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_xanhdam65);
            }
        } else {
            if (mIsUse == false) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_xam);
            } else if (mSnr >= 0 && mSnr <= 10) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_do);
            } else if (mSnr > 10 && mSnr <= 20) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_cam);
            } else if (mSnr > 20 && mSnr <= 30) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_vang);
            } else if (mSnr > 30 && mSnr <= 50) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_xanh);
            } else if (mSnr > 50) {
                bitmapVeTinh = BitmapFactory.decodeResource(getResources(), R.drawable.vetinh_xanhdam);
            }
        }
    }
    public void setDirection(int direction) {
        this.direction = direction;
        this.invalidate();
    }
    public void setDirection(float direction) {
        this.direction = direction;
        this.invalidate();
    }
}
class HinhTron extends View {

    private float direction=0;

    public HinhTron(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(direction, canvas.getWidth() / 2, canvas.getHeight() / 2);
        // R = bitmapHinhtron.getwidth/2
        Bitmap bitmapHinhTron = BitmapFactory.decodeResource(getResources(), R.drawable.hinhtronb);
        canvas.drawBitmap(bitmapHinhTron, (canvas.getWidth() / 2) - (bitmapHinhTron.getWidth() / 2), (canvas.getHeight() / 2) - (bitmapHinhTron.getHeight() / 2), null);
       invalidate();

    }
    public void setDirection(float direction) {
        this.direction = direction;
        this.invalidate();
    }
}

class ThongSo extends View {
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
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("In View: " + numberOfInView, 30, 30, paint);
        canvas.drawText("In Use: " + numberOfInUse, 30, 60, paint);
        canvas.drawText("SNR Bar :", 30, 3 * canvas.getHeight() / 4 + 30, paint);
        canvas.drawBitmap(bitmap, 30, 3 * canvas.getHeight() / 4 + 60, null);
    }
}