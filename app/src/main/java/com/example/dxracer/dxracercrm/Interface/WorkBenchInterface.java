package com.example.dxracer.dxracercrm.Interface;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dxracer.dxracercrm.Model.HomeIconModel;

import java.util.List;

public interface WorkBenchInterface {
    //model
    interface  Model{

        String getText();
        Bitmap getBitmap();

    }


    //presenter
    interface Presenter{
        List<HomeIconModel>getdata();
        void intentActivity(android.view.View view , int position);
    }

}
