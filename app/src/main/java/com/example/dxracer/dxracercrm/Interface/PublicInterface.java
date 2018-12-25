package com.example.dxracer.dxracercrm.Interface;

public interface PublicInterface {

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
