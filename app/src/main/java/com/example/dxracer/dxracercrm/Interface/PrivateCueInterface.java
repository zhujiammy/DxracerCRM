package com.example.dxracer.dxracercrm.Interface;

public interface PrivateCueInterface  {

    //view
    interface View{
        void succeed();
        void failed();
        void onRefresh();
        void onLoadMore();
        void onNothingData();
    }
}
