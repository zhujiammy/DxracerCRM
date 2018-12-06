package com.example.dxracer.dxracercrm.Interface;

public interface AddQuotationInterface {

    //view
    interface View{
        void succeed();
        void failed();
    }

    interface presenter{
        void loadgetDataListByTypeperson();//获取采购方联系人
        void loadgetDataListgetMineAndChildren();//供应商联系人
        void loadgetDataListgetMineAndParents();//折扣率

    }
}
