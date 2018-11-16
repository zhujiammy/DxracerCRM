package com.example.dxracer.dxracercrm.Interface;

public interface MaillistInterface {
    //view
    interface View{
        void succeed();
        void failed();
        void onRefresh();
        void onLoadMore();
        void onNothingData();
    }

}
