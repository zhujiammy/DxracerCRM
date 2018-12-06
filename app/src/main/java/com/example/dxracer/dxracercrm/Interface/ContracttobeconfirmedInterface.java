package com.example.dxracer.dxracercrm.Interface;

public interface ContracttobeconfirmedInterface {

    //view
    interface View{
        void succeed();
        void failed();
        void onRefresh();
        void onLoadMore();
        void onNothingData();
    }

    interface presenter{
        void getDataListDepositratio();//获取定金比例
        void getDataLisDaysofpayment();//获取尾款天数


    }
}
