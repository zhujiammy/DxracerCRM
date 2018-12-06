package com.example.dxracer.dxracercrm.Model;

import java.util.List;

public class ContractFormationModel {

    private int total; //条数
    private int lastPage;//页数
    private boolean  hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private int navigateLastPage;
    private int navigateFirstPage;
    private int pageNum;
    private int pages;

    private List<FormationBean> list;

    public static class FormationBean{
        public int id ;
        public String leadNo;
        public String oppoBillNo;
        public String opppPriceNo;
        public String contractNo;
        public String contractStatus;
        public double contractFee;
        public double paymentDeposit;
        public double paymentFinal;
        public int createUserId;
        public String supplierName;
        public String createPerson;
        public String createDate;
        public String createTime;
        public String genDate;
        public String sendDate;
        public String signDate;
        public String excuteDate;
        public String finishedDate;
        public String closeDate;
        public String closeNote;
        public String sendLogistic;
        public String sendExpressNo;
        public String contractManagePerson;
        public String contractManagePlace;


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

        public String getOppoBillNo() {
            return oppoBillNo;
        }

        public void setOppoBillNo(String oppoBillNo) {
            this.oppoBillNo = oppoBillNo;
        }

        public String getOpppPriceNo() {
            return opppPriceNo;
        }

        public void setOpppPriceNo(String opppPriceNo) {
            this.opppPriceNo = opppPriceNo;
        }

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public String getContractStatus() {
            return contractStatus;
        }

        public void setContractStatus(String contractStatus) {
            this.contractStatus = contractStatus;
        }

        public double getContractFee() {
            return contractFee;
        }

        public void setContractFee(double contractFee) {
            this.contractFee = contractFee;
        }

        public double getPaymentDeposit() {
            return paymentDeposit;
        }

        public void setPaymentDeposit(double paymentDeposit) {
            this.paymentDeposit = paymentDeposit;
        }

        public double getPaymentFinal() {
            return paymentFinal;
        }

        public void setPaymentFinal(double paymentFinal) {
            this.paymentFinal = paymentFinal;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getCreatePerson() {
            return createPerson;
        }

        public void setCreatePerson(String createPerson) {
            this.createPerson = createPerson;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGenDate() {
            return genDate;
        }

        public void setGenDate(String genDate) {
            this.genDate = genDate;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public String getExcuteDate() {
            return excuteDate;
        }

        public void setExcuteDate(String excuteDate) {
            this.excuteDate = excuteDate;
        }

        public String getFinishedDate() {
            return finishedDate;
        }

        public void setFinishedDate(String finishedDate) {
            this.finishedDate = finishedDate;
        }

        public String getCloseDate() {
            return closeDate;
        }

        public void setCloseDate(String closeDate) {
            this.closeDate = closeDate;
        }

        public String getCloseNote() {
            return closeNote;
        }

        public void setCloseNote(String closeNote) {
            this.closeNote = closeNote;
        }

        public String getSendLogistic() {
            return sendLogistic;
        }

        public void setSendLogistic(String sendLogistic) {
            this.sendLogistic = sendLogistic;
        }

        public String getSendExpressNo() {
            return sendExpressNo;
        }

        public void setSendExpressNo(String sendExpressNo) {
            this.sendExpressNo = sendExpressNo;
        }

        public String getContractManagePerson() {
            return contractManagePerson;
        }

        public void setContractManagePerson(String contractManagePerson) {
            this.contractManagePerson = contractManagePerson;
        }

        public String getContractManagePlace() {
            return contractManagePlace;
        }

        public void setContractManagePlace(String contractManagePlace) {
            this.contractManagePlace = contractManagePlace;
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

    public List<FormationBean> getList() {
        return list;
    }

    public void setList(List<FormationBean> list) {
        this.list = list;
    }
}
