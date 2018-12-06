package com.example.dxracer.dxracercrm.Interface;

public interface ProductInterface {

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
