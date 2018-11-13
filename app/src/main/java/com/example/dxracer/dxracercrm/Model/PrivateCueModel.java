package com.example.dxracer.dxracercrm.Model;

import java.util.List;

public class PrivateCueModel {

    /**
     *这里要引用一个类里面的字段 {@link com.example.dxracer.dxracercrm.View.PublicCue } // 这里不区分字段是否是public 或者 static，都可以直接引用 <br/>
     * */


    private int total; //条数
    private int lastPage;//页数
    private boolean  hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private int navigateLastPage;
    private int navigateFirstPage;
    private List<Bean> list;

    public static class Bean {
        public int id;//id
        public String leadNo;//线索编号
        public int existDays;//创建天数
        public int contactPersonNum;//联系人数
        public int contactsCommunicateNum;//沟通次数
        public String leadGetDate;//获得日期
        public String leadSource;//获得渠道
        public String customerShortName;//客户简称
        public String customerFullName;//客户全称
        public String customerScale;//客户规模
        public String customerIndustry;//客户行业
        public String customerProvinceCode;//省代码
        public String customerCityCode;//市代码
        public String customerDistrictCode;//区代码
        public String customerProvinceName;//省名
        public String customerCityName;//市名
        public String customerDistrictName;//区名
        public String customerAddress;//详细地址
        public String customerIntroduce;//客户介绍
        public int leadCreateUserId;//创建人id
        public String createPersonName;//创建人
        public String leadCreateDate;//创建人日期

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLeadNo() {
            return leadNo;
        }

        public void setLeadNo(String leadNo) {
            this.leadNo = leadNo;
        }

        public int getExistDays() {
            return existDays;
        }

        public void setExistDays(int existDays) {
            this.existDays = existDays;
        }

        public int getContactPersonNum() {
            return contactPersonNum;
        }

        public void setContactPersonNum(int contactPersonNum) {
            this.contactPersonNum = contactPersonNum;
        }

        public int getContactsCommunicateNum() {
            return contactsCommunicateNum;
        }

        public void setContactsCommunicateNum(int contactsCommunicateNum) {
            this.contactsCommunicateNum = contactsCommunicateNum;
        }

        public String getLeadGetDate() {
            return leadGetDate;
        }

        public void setLeadGetDate(String leadGetDate) {
            this.leadGetDate = leadGetDate;
        }

        public String getLeadSource() {
            return leadSource;
        }

        public void setLeadSource(String leadSource) {
            this.leadSource = leadSource;
        }

        public String getCustomerShortName() {
            return customerShortName;
        }

        public void setCustomerShortName(String customerShortName) {
            this.customerShortName = customerShortName;
        }

        public String getCustomerFullName() {
            return customerFullName;
        }

        public void setCustomerFullName(String customerFullName) {
            this.customerFullName = customerFullName;
        }

        public String getCustomerScale() {
            return customerScale;
        }

        public void setCustomerScale(String customerScale) {
            this.customerScale = customerScale;
        }

        public String getCustomerIndustry() {
            return customerIndustry;
        }

        public void setCustomerIndustry(String customerIndustry) {
            this.customerIndustry = customerIndustry;
        }

        public String getCustomerProvinceCode() {
            return customerProvinceCode;
        }

        public void setCustomerProvinceCode(String customerProvinceCode) {
            this.customerProvinceCode = customerProvinceCode;
        }

        public String getCustomerCityCode() {
            return customerCityCode;
        }

        public void setCustomerCityCode(String customerCityCode) {
            this.customerCityCode = customerCityCode;
        }

        public String getCustomerDistrictCode() {
            return customerDistrictCode;
        }

        public void setCustomerDistrictCode(String customerDistrictCode) {
            this.customerDistrictCode = customerDistrictCode;
        }

        public String getCustomerProvinceName() {
            return customerProvinceName;
        }

        public void setCustomerProvinceName(String customerProvinceName) {
            this.customerProvinceName = customerProvinceName;
        }

        public String getCustomerCityName() {
            return customerCityName;
        }

        public void setCustomerCityName(String customerCityName) {
            this.customerCityName = customerCityName;
        }

        public String getCustomerDistrictName() {
            return customerDistrictName;
        }

        public void setCustomerDistrictName(String customerDistrictName) {
            this.customerDistrictName = customerDistrictName;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getCustomerIntroduce() {
            return customerIntroduce;
        }

        public void setCustomerIntroduce(String customerIntroduce) {
            this.customerIntroduce = customerIntroduce;
        }

        public int getLeadCreateUserId() {
            return leadCreateUserId;
        }

        public void setLeadCreateUserId(int leadCreateUserId) {
            this.leadCreateUserId = leadCreateUserId;
        }

        public String getCreatePersonName() {
            return createPersonName;
        }

        public void setCreatePersonName(String createPersonName) {
            this.createPersonName = createPersonName;
        }

        public String getLeadCreateDate() {
            return leadCreateDate;
        }

        public void setLeadCreateDate(String leadCreateDate) {
            this.leadCreateDate = leadCreateDate;
        }
    }


    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Bean> getList() {
        return list;
    }

    public void setList(List<Bean> list) {
        this.list = list;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }


    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }
}
