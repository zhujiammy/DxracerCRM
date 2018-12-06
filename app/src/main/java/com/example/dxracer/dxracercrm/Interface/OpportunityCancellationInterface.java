package com.example.dxracer.dxracercrm.Interface;

public interface OpportunityCancellationInterface {

    //view
    interface View{
        void succeed();
        void failed();
    }


    interface presenter{
        void loadgetDataListByType();//获取作废原因
    }
}
