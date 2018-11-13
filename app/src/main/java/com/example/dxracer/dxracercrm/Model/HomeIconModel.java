package com.example.dxracer.dxracercrm.Model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.dxracer.dxracercrm.Interface.WorkBenchInterface;

public class HomeIconModel implements WorkBenchInterface.Model{

    public String text;
    public Bitmap bitmap;


    @Override
    public String getText() {
        return text;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
