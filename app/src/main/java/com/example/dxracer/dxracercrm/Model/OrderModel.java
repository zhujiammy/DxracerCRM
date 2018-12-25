package com.example.dxracer.dxracercrm.Model;

import java.util.List;

public class OrderModel {

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

    private List<Beanorder> list;

    public static  class  Beanorder{
        private int id;
        private String customerFullName;
        private String leadNo;
        private String orderNo;
        private String oppoNo;
        private String contractNo;
        private String orderStatus;
        private String orderCreateDate;
        private double orderTotalFee;
        private String logistic;
        private String expressNo;
        private String logisticMoney;
        private String deliveryTime;
        private String closeTime;
        private String dmsOrderNo;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCustomerFullName() {
            return customerFullName;
        }

        public void setCustomerFullName(String customerFullName) {
            this.customerFullName = customerFullName;
        }

        public String getLeadNo() {
            return leadNo;
        }

        public void setLeadNo(String leadNo) {
            this.leadNo = leadNo;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOppoNo() {
            return oppoNo;
        }

        public void setOppoNo(String oppoNo) {
            this.oppoNo = oppoNo;
        }

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderCreateDate() {
            return orderCreateDate;
        }

        public void setOrderCreateDate(String orderCreateDate) {
            this.orderCreateDate = orderCreateDate;
        }

        public double getOrderTotalFee() {
            return orderTotalFee;
        }

        public void setOrderTotalFee(double orderTotalFee) {
            this.orderTotalFee = orderTotalFee;
        }

        public String getLogistic() {
            return logistic;
        }

        public void setLogistic(String logistic) {
            this.logistic = logistic;
        }

        public String getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(String expressNo) {
            this.expressNo = expressNo;
        }

        public String getLogisticMoney() {
            return logisticMoney;
        }

        public void setLogisticMoney(String logisticMoney) {
            this.logisticMoney = logisticMoney;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getCloseTime() {
            return closeTime;
        }

        public void setCloseTime(String closeTime) {
            this.closeTime = closeTime;
        }

        public String getDmsOrderNo() {
            return dmsOrderNo;
        }

        public void setDmsOrderNo(String dmsOrderNo) {
            this.dmsOrderNo = dmsOrderNo;
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

    public List<Beanorder> getList() {
        return list;
    }

    public void setList(List<Beanorder> list) {
        this.list = list;
    }
}
