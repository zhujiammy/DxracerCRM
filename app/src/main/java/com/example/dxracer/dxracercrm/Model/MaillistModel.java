package com.example.dxracer.dxracercrm.Model;

import com.example.dxracer.dxracercrm.common.Abbreviated;
import com.example.dxracer.dxracercrm.common.ContactsUtils;

import java.util.List;

public class MaillistModel{

    //联系人实体类

    private int total; //条数
    private int lastPage;//页数
    private boolean  hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private int navigateLastPage;
    private int navigateFirstPage;
    private List<MaillistBean> list;

    public static class MaillistBean implements Abbreviated, Comparable<MaillistBean>{
        public int id;//
        public String leadNo;//线索编号
        public String mainPerson;//是否为默认联系人
        public final String personName;//联系人姓名
        public final String mobile;//手机号码
        public String wechat;//微信号码
        public String sex;//性别
        public String birthday;//生日
        public String nickName;//昵称
        public String position;//职位
        public String email;//电子邮箱
        public String businessCard;//名片
        public String createUserId;//创建人id
        public String createTime;//创建日期

        private final String mAbbreviation;
        private final String mInitial;

        public MaillistBean(String personName, String position,String mobile,String nickName,String email,String wechat,String birthday,String leadNo,String sex,int id,String mainPerson){
            this.personName = personName;
            this.mobile = mobile;
            this.position = position;
            this.nickName = nickName;
            this.email = email;
            this.wechat = wechat;
            this.birthday = birthday;
            this.leadNo = leadNo;
            this.sex = sex;
            this.id = id;
            this.mainPerson = mainPerson;
            this.mAbbreviation = ContactsUtils.getAbbreviation(personName);
            this.mInitial = mAbbreviation.substring(0, 1);
        }

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

        public String getMainPerson() {
            return mainPerson;
        }

        public void setMainPerson(String mainPerson) {
            this.mainPerson = mainPerson;
        }

        public String getPersonName() {
            return personName;
        }



        public String getMobile() {
            return mobile;
        }



        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBusinessCard() {
            return businessCard;
        }

        public void setBusinessCard(String businessCard) {
            this.businessCard = businessCard;
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

        @Override
        public String getInitial() {
            return mInitial;
        }

        @Override
        public int compareTo(MaillistBean r) {
            if (mAbbreviation.equals(r.mAbbreviation)) {
                return 0;
            }
            boolean flag;
            if ((flag = mAbbreviation.startsWith("#")) ^ r.mAbbreviation.startsWith("#")) {
                return flag ? 1 : -1;
            }
            return getInitial().compareTo(r.getInitial());
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

    public List<MaillistBean> getList() {
        return list;
    }

    public void setList(List<MaillistBean> list) {
        this.list = list;
    }
}
