package com.example.dxracer.dxracercrm.Model;

import com.example.dxracer.dxracercrm.View.CollectionReceiptFragment;

import java.util.List;

public class CollectionReceiptFragmentModel {



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

    public List<CollectionReceipt>list;

    public static class  CollectionReceipt{
        private int id;
        private String contractNo;
        private String paymentCompany;
        private double contractFee;
        private String paymentType;
        private String transNo;
        private double actFee;
        private String receiveDate;
        private String paymentStatus;
        private String createDate;
        private String planReceiveDate;
        private String finalType;
        private String finalDay;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public String getPaymentCompany() {
            return paymentCompany;
        }

        public void setPaymentCompany(String paymentCompany) {
            this.paymentCompany = paymentCompany;
        }

        public double getContractFee() {
            return contractFee;
        }

        public void setContractFee(double contractFee) {
            this.contractFee = contractFee;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getTransNo() {
            return transNo;
        }

        public void setTransNo(String transNo) {
            this.transNo = transNo;
        }

        public double getActFee() {
            return actFee;
        }

        public void setActFee(double actFee) {
            this.actFee = actFee;
        }

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getPlanReceiveDate() {
            return planReceiveDate;
        }

        public void setPlanReceiveDate(String planReceiveDate) {
            this.planReceiveDate = planReceiveDate;
        }

        public String getFinalType() {
            return finalType;
        }

        public void setFinalType(String finalType) {
            this.finalType = finalType;
        }

        public String getFinalDay() {
            return finalDay;
        }

        public void setFinalDay(String finalDay) {
            this.finalDay = finalDay;
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

    public List<CollectionReceipt> getList() {
        return list;
    }

    public void setList(List<CollectionReceipt> list) {
        this.list = list;
    }
}
