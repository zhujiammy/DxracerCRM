package com.example.dxracer.dxracercrm.Interface;

public interface AddPublicCueInterface {

    //view
    interface View{
        void succeed();
        void failed();
    }


    interface presenter{
        void loadgetDataListByType();//获取渠道
        void loadgetDataListByCustomerScale();//客户规模
        void loadgetDataListBycustomerindustry();//客户行业
    }
}
