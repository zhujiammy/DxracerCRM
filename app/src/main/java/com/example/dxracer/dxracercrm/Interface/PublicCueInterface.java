package com.example.dxracer.dxracercrm.Interface;

import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapters;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;

import java.util.List;

public interface PublicCueInterface {

    //view
    interface View{
        void succeed();
        void failed();
        void onRefresh();
        void onLoadMore();
        void onNothingData();
    }

    //presenter
    interface Presenter{
    }
}
