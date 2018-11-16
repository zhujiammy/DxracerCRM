package com.example.dxracer.dxracercrm.Interface;

public interface AddRecordInterface {

    //view
    interface View{
        void succeed();
        void failed();
    }

    interface presenter{
        void loadgetDataListByType();//获取联系方式
        void loadgetDataListByPerson();//获取联系人
        void loadgetDatalistBycommunicateStage();//获取跟进阶段
    }
}
