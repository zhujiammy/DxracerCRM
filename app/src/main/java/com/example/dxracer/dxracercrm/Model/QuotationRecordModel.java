package com.example.dxracer.dxracercrm.Model;

import java.util.List;

public class QuotationRecordModel {
    //报价记录实体类
    private int total; //条数
    private int lastPage;//页数
    private boolean  hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private int navigateLastPage;
    private int navigateFirstPage;
    private int pageNum;
    private int pages;

    private List<QuotationRecorBean> list;

    public static class QuotationRecorBean{

        public int id;
        public String oppoBillNo;
        public String oppoPriceNo;
        public String oppoConfirmDate;
        public String supplierCompanyName;
        public String supplierContactsPersonName;
        public String supplierContactsPersonMobile;
        public String supplierContactsPersonEmail;
        public String dealerCompanyName;
        public String dealerContactsPersonName;
        public String dealerContactsPersonMobile;
        public String dealerContactsPersonEmail;
        public double oppoProductTotalFee;
        public int oppoTransTotalFee;
        public int oppoOtherTotalFee;
        public double oppoBillTotalFee;
        public int oppoCreateUserId;
        public int discountPoint;
        public String oppoCreateTime;
        public String oppoCreateDate;
        public String oppoBillStatus;
        public String confirmTime;
        public int auditUserId;
        public String auditTime;
        public String cancelTime;
        public String auditPersonName;
        public String oppoCreatePersonName;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOppoBillNo() {
            return oppoBillNo;
        }

        public void setOppoBillNo(String oppoBillNo) {
            this.oppoBillNo = oppoBillNo;
        }

        public String getOppoPriceNo() {
            return oppoPriceNo;
        }

        public void setOppoPriceNo(String oppoPriceNo) {
            this.oppoPriceNo = oppoPriceNo;
        }

        public String getOppoConfirmDate() {
            return oppoConfirmDate;
        }

        public void setOppoConfirmDate(String oppoConfirmDate) {
            this.oppoConfirmDate = oppoConfirmDate;
        }

        public String getSupplierCompanyName() {
            return supplierCompanyName;
        }

        public void setSupplierCompanyName(String supplierCompanyName) {
            this.supplierCompanyName = supplierCompanyName;
        }

        public String getSupplierContactsPersonName() {
            return supplierContactsPersonName;
        }

        public void setSupplierContactsPersonName(String supplierContactsPersonName) {
            this.supplierContactsPersonName = supplierContactsPersonName;
        }

        public String getSupplierContactsPersonMobile() {
            return supplierContactsPersonMobile;
        }

        public void setSupplierContactsPersonMobile(String supplierContactsPersonMobile) {
            this.supplierContactsPersonMobile = supplierContactsPersonMobile;
        }

        public String getSupplierContactsPersonEmail() {
            return supplierContactsPersonEmail;
        }

        public void setSupplierContactsPersonEmail(String supplierContactsPersonEmail) {
            this.supplierContactsPersonEmail = supplierContactsPersonEmail;
        }

        public String getDealerCompanyName() {
            return dealerCompanyName;
        }

        public void setDealerCompanyName(String dealerCompanyName) {
            this.dealerCompanyName = dealerCompanyName;
        }

        public String getDealerContactsPersonName() {
            return dealerContactsPersonName;
        }

        public void setDealerContactsPersonName(String dealerContactsPersonName) {
            this.dealerContactsPersonName = dealerContactsPersonName;
        }

        public String getDealerContactsPersonMobile() {
            return dealerContactsPersonMobile;
        }

        public void setDealerContactsPersonMobile(String dealerContactsPersonMobile) {
            this.dealerContactsPersonMobile = dealerContactsPersonMobile;
        }

        public String getDealerContactsPersonEmail() {
            return dealerContactsPersonEmail;
        }

        public void setDealerContactsPersonEmail(String dealerContactsPersonEmail) {
            this.dealerContactsPersonEmail = dealerContactsPersonEmail;
        }

        public double getOppoProductTotalFee() {
            return oppoProductTotalFee;
        }

        public void setOppoProductTotalFee(double oppoProductTotalFee) {
            this.oppoProductTotalFee = oppoProductTotalFee;
        }

        public int getOppoTransTotalFee() {
            return oppoTransTotalFee;
        }

        public void setOppoTransTotalFee(int oppoTransTotalFee) {
            this.oppoTransTotalFee = oppoTransTotalFee;
        }

        public int getOppoOtherTotalFee() {
            return oppoOtherTotalFee;
        }

        public void setOppoOtherTotalFee(int oppoOtherTotalFee) {
            this.oppoOtherTotalFee = oppoOtherTotalFee;
        }

        public double getOppoBillTotalFee() {
            return oppoBillTotalFee;
        }

        public void setOppoBillTotalFee(double oppoBillTotalFee) {
            this.oppoBillTotalFee = oppoBillTotalFee;
        }

        public int getOppoCreateUserId() {
            return oppoCreateUserId;
        }

        public void setOppoCreateUserId(int oppoCreateUserId) {
            this.oppoCreateUserId = oppoCreateUserId;
        }

        public int getDiscountPoint() {
            return discountPoint;
        }

        public void setDiscountPoint(int discountPoint) {
            this.discountPoint = discountPoint;
        }

        public String getOppoCreateTime() {
            return oppoCreateTime;
        }

        public void setOppoCreateTime(String oppoCreateTime) {
            this.oppoCreateTime = oppoCreateTime;
        }

        public String getOppoCreateDate() {
            return oppoCreateDate;
        }

        public void setOppoCreateDate(String oppoCreateDate) {
            this.oppoCreateDate = oppoCreateDate;
        }

        public String getOppoBillStatus() {
            return oppoBillStatus;
        }

        public void setOppoBillStatus(String oppoBillStatus) {
            this.oppoBillStatus = oppoBillStatus;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public int getAuditUserId() {
            return auditUserId;
        }

        public void setAuditUserId(int auditUserId) {
            this.auditUserId = auditUserId;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getAuditPersonName() {
            return auditPersonName;
        }

        public void setAuditPersonName(String auditPersonName) {
            this.auditPersonName = auditPersonName;
        }

        public String getOppoCreatePersonName() {
            return oppoCreatePersonName;
        }

        public void setOppoCreatePersonName(String oppoCreatePersonName) {
            this.oppoCreatePersonName = oppoCreatePersonName;
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

    public List<QuotationRecorBean> getList() {
        return list;
    }

    public void setList(List<QuotationRecorBean> list) {
        this.list = list;
    }
}
