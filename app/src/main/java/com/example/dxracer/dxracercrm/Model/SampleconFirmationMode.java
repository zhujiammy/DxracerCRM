package com.example.dxracer.dxracercrm.Model;

import java.util.List;

public class SampleconFirmationMode {
    private int total; //条数
    private int lastPage;//页数
    private boolean  hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private int navigateLastPage;
    private int navigateFirstPage;
    private int pageNum;
    private int pages;
    private List<SampleMode> list;


    public static class  SampleMode{

        public int id;
        public String oppoNo;//机会编号
        public String leadNo;//线索编号
        public String oppoStatus;//机会状态
        public int contactPersonNum;//联系人数量
        public int contactsCommunicateNum;//沟通次数
        public int oppoCreateUserId;//机会创建人id
        public String oppoCreatePersonName;//机会创建人
        public int oppoExistDays;//机会创建天数
        public String oppoCreateDate;//机会创建日期
        public String oppoCreateTime;//机会创建具体时间
        public String oppoStage;
        public String oppoStopOption;
        public String oppoStopNote;
        public String oppoStopTime;

        public Object lead;

        public Object getLead() {
            return lead;
        }

        public void setLead(Object lead) {
            this.lead = lead;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOppoNo() {
            return oppoNo;
        }

        public void setOppoNo(String oppoNo) {
            this.oppoNo = oppoNo;
        }

        public String getLeadNo() {
            return leadNo;
        }

        public void setLeadNo(String leadNo) {
            this.leadNo = leadNo;
        }

        public String getOppoStatus() {
            return oppoStatus;
        }

        public void setOppoStatus(String oppoStatus) {
            this.oppoStatus = oppoStatus;
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

        public int getOppoCreateUserId() {
            return oppoCreateUserId;
        }

        public void setOppoCreateUserId(int oppoCreateUserId) {
            this.oppoCreateUserId = oppoCreateUserId;
        }

        public String getOppoCreatePersonName() {
            return oppoCreatePersonName;
        }

        public void setOppoCreatePersonName(String oppoCreatePersonName) {
            this.oppoCreatePersonName = oppoCreatePersonName;
        }

        public int getOppoExistDays() {
            return oppoExistDays;
        }

        public void setOppoExistDays(int oppoExistDays) {
            this.oppoExistDays = oppoExistDays;
        }

        public String getOppoCreateDate() {
            return oppoCreateDate;
        }

        public void setOppoCreateDate(String oppoCreateDate) {
            this.oppoCreateDate = oppoCreateDate;
        }

        public String getOppoCreateTime() {
            return oppoCreateTime;
        }

        public void setOppoCreateTime(String oppoCreateTime) {
            this.oppoCreateTime = oppoCreateTime;
        }

        public String getOppoStage() {
            return oppoStage;
        }

        public void setOppoStage(String oppoStage) {
            this.oppoStage = oppoStage;
        }

        public String getOppoStopOption() {
            return oppoStopOption;
        }

        public void setOppoStopOption(String oppoStopOption) {
            this.oppoStopOption = oppoStopOption;
        }

        public String getOppoStopNote() {
            return oppoStopNote;
        }

        public void setOppoStopNote(String oppoStopNote) {
            this.oppoStopNote = oppoStopNote;
        }

        public String getOppoStopTime() {
            return oppoStopTime;
        }

        public void setOppoStopTime(String oppoStopTime) {
            this.oppoStopTime = oppoStopTime;
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

    public List<SampleMode> getList() {
        return list;
    }

    public void setList(List<SampleMode> list) {
        this.list = list;
    }
}
