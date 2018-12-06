package com.example.dxracer.dxracercrm.Model;

import java.io.Serializable;

public class FollowPersonModel implements Serializable {
    private int id;
    private String realName;

    public FollowPersonModel(){

    }
    public FollowPersonModel(int id ,String realName){
        super();
        this.id = id;
        this.realName = realName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 为什么要重写toString()呢？
     *
     * 因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
     */
    @Override
    public String toString() {
        return realName;

    }
}
