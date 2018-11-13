package com.example.dxracer.dxracercrm.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.WorkBenchInterface;
import com.example.dxracer.dxracercrm.Model.HomeIconModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.View.CueManagementActivity;

import java.util.ArrayList;
import java.util.List;

public class WorkBenchPresenter implements WorkBenchInterface.Presenter {
    private HomeIconModel model;
    private Context context;
    private  List<HomeIconModel> dataList;
    private Intent intent;
    
    public WorkBenchPresenter(Context context){
        this.model = new HomeIconModel();
        this.context = context;
    }

    @Override
    public List<HomeIconModel> getdata() {
        Resources res = context.getResources();
        String [] texts ={"线索管理","机会管理","档案管理"};
        Bitmap[] bitmaps ={BitmapFactory.decodeResource(res,R.mipmap.cuemanagement),BitmapFactory.decodeResource(res, R.mipmap.opportunity),BitmapFactory.decodeResource(res, R.mipmap.file)};
        dataList = new ArrayList<>();
        for (int i = 0;i < texts.length; i++){
            HomeIconModel homeIconModel = new HomeIconModel();
            homeIconModel.text=(texts[i]);
            homeIconModel.bitmap=(bitmaps[i]);
            dataList.add(homeIconModel);
        }
        return dataList;
    }

    //页面跳转
    @Override
    public void intentActivity(View view, int position) {

        if(dataList.get(position).text.equals("线索管理")){
            intent = new Intent(context,CueManagementActivity.class);
            context.startActivity(intent);
        }
    }
}
