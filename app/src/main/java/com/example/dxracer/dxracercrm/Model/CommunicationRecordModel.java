package com.example.dxracer.dxracercrm.Model;

import java.util.List;

public class CommunicationRecordModel {

    private int total; //条数
    private int lastPage;//页数
    private boolean  hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private int navigateLastPage;
    private int navigateFirstPage;

    private List<Recor> list;

    public static  class  Recor{
        private int id;//沟通记录id
        private String leadNo;//线索编号
        private int contactsPersonId;//联系人id
        private String createPersonName;//跟进人
        private String contactsPersonName;//联系人名字
        private String communicateType;//联系方式
        private String communicateTime;//联系日期
        private String communicateStage;//跟进阶段
        private String communicateResult;//跟进结果
        private String createUserId;//创建人id
        private String createTime;//创建日期
        private String communicateFile;//附件


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

        public int getContactsPersonId() {
            return contactsPersonId;
        }

        public void setContactsPersonId(int contactsPersonId) {
            this.contactsPersonId = contactsPersonId;
        }

        public String getCreatePersonName() {
            return createPersonName;
        }

        public void setCreatePersonName(String createPersonName) {
            this.createPersonName = createPersonName;
        }

        public String getContactsPersonName() {
            return contactsPersonName;
        }

        public void setContactsPersonName(String contactsPersonName) {
            this.contactsPersonName = contactsPersonName;
        }

        public String getCommunicateType() {
            return communicateType;
        }

        public void setCommunicateType(String communicateType) {
            this.communicateType = communicateType;
        }

        public String getCommunicateTime() {
            return communicateTime;
        }

        public void setCommunicateTime(String communicateTime) {
            this.communicateTime = communicateTime;
        }

        public String getCommunicateStage() {
            return communicateStage;
        }

        public void setCommunicateStage(String communicateStage) {
            this.communicateStage = communicateStage;
        }

        public String getCommunicateResult() {
            return communicateResult;
        }

        public void setCommunicateResult(String communicateResult) {
            this.communicateResult = communicateResult;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCommunicateFile() {
            return communicateFile;
        }

        public void setCommunicateFile(String communicateFile) {
            this.communicateFile = communicateFile;
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

    public List<Recor> getList() {
        return list;
    }

    public void setList(List<Recor> list) {
        this.list = list;
    }
}
