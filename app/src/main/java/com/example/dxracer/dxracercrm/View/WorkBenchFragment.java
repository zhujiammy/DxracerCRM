package com.example.dxracer.dxracercrm.View;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dxracer.dxracercrm.Adapter.MyAdapter;
import com.example.dxracer.dxracercrm.Model.HomeIconModel;
import com.example.dxracer.dxracercrm.R;

import java.util.ArrayList;
import java.util.List;

public class WorkBenchFragment extends Fragment {
    private View view;
    private RecyclerView home_rv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.workbenchfragment,container,false);
        initUI();
        return view;
    }

    private void initUI(){
        home_rv = (RecyclerView) view.findViewById(R.id.home_Rv);
        Resources res = this.getResources();
        String [] texts ={"线索管理","机会管理","档案管理"};
        Bitmap [] bitmaps ={BitmapFactory.decodeResource(res,R.mipmap.cuemanagement),BitmapFactory.decodeResource(res, R.mipmap.opportunity),BitmapFactory.decodeResource(res, R.mipmap.file)};
        List<HomeIconModel> dataList = new ArrayList<>();
        for (int i = 0;i < texts.length; i++){
            HomeIconModel homeIconModel = new HomeIconModel();
            homeIconModel.setText(texts[i]);
            homeIconModel.setBitmap(bitmaps[i]);
            dataList.add(homeIconModel);
        }



        final MyAdapter adapter = new MyAdapter(dataList);
        home_rv.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        home_rv.setLayoutManager(layoutManager);

    }
}
