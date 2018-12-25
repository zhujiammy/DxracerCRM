package com.example.dxracer.dxracercrm.Model;

import java.util.List;

public class InstallationInformationModel {

    /**
     *这里要引用一个类里面的字段 {@link com.example.dxracer.dxracercrm.View.PublicCue } // 这里不区分字段是否是public 或者 static，都可以直接引用 <br/>
     * */


    private int total; //条数
    private int lastPage;//页数
    private boolean  hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private int navigateLastPage;
    private int navigateFirstPage;
    private int pageNum;
    private int pages;

    public List<installBean> list;
    public static class  installBean{
        private int id;
        private String installNo;
        private String orderNo;
        private int productSkuId;
        private int quantity;
        private String createDate;
        private int installPersonId;
        private String planTime;
        private String installAddr;
        private String status;
        private String installImg;
        private double installFee;
        private String finishedTime;
        private String extendStr1;
        private String extendStr2;
        private String extendStr3;
        private String extendStr4;
        private String extendStr5;
        private String extendStr6;

        private String fcno;
        private String skuName;
        private String skuMainImg;
        private String supplierPersonName;
        private String supplierMobile;


        public String getExtendStr2() {
            return extendStr2;
        }

        public void setExtendStr2(String extendStr2) {
            this.extendStr2 = extendStr2;
        }

        public String getExtendStr3() {
            return extendStr3;
        }

        public void setExtendStr3(String extendStr3) {
            this.extendStr3 = extendStr3;
        }

        public String getExtendStr4() {
            return extendStr4;
        }

        public void setExtendStr4(String extendStr4) {
            this.extendStr4 = extendStr4;
        }

        public String getExtendStr5() {
            return extendStr5;
        }

        public void setExtendStr5(String extendStr5) {
            this.extendStr5 = extendStr5;
        }

        public String getExtendStr6() {
            return extendStr6;
        }

        public void setExtendStr6(String extendStr6) {
            this.extendStr6 = extendStr6;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInstallNo() {
            return installNo;
        }

        public void setInstallNo(String installNo) {
            this.installNo = installNo;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getProductSkuId() {
            return productSkuId;
        }

        public void setProductSkuId(int productSkuId) {
            this.productSkuId = productSkuId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getInstallPersonId() {
            return installPersonId;
        }

        public void setInstallPersonId(int installPersonId) {
            this.installPersonId = installPersonId;
        }

        public String getPlanTime() {
            return planTime;
        }

        public void setPlanTime(String planTime) {
            this.planTime = planTime;
        }

        public String getInstallAddr() {
            return installAddr;
        }

        public void setInstallAddr(String installAddr) {
            this.installAddr = installAddr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getInstallImg() {
            return installImg;
        }

        public void setInstallImg(String installImg) {
            this.installImg = installImg;
        }

        public double getInstallFee() {
            return installFee;
        }

        public void setInstallFee(double installFee) {
            this.installFee = installFee;
        }

        public String getFinishedTime() {
            return finishedTime;
        }

        public void setFinishedTime(String finishedTime) {
            this.finishedTime = finishedTime;
        }

        public String getExtendStr1() {
            return extendStr1;
        }

        public void setExtendStr1(String extendStr1) {
            this.extendStr1 = extendStr1;
        }

        public String getFcno() {
            return fcno;
        }

        public void setFcno(String fcno) {
            this.fcno = fcno;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public String getSkuMainImg() {
            return skuMainImg;
        }

        public void setSkuMainImg(String skuMainImg) {
            this.skuMainImg = skuMainImg;
        }

        public String getSupplierPersonName() {
            return supplierPersonName;
        }

        public void setSupplierPersonName(String supplierPersonName) {
            this.supplierPersonName = supplierPersonName;
        }

        public String getSupplierMobile() {
            return supplierMobile;
        }

        public void setSupplierMobile(String supplierMobile) {
            this.supplierMobile = supplierMobile;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<installBean> getList() {
        return list;
    }

    public void setList(List<installBean> list) {
        this.list = list;
    }
}
