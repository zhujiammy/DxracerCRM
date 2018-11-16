package com.example.dxracer.dxracercrm.Model;

import java.io.Serializable;

public class AddRecordContactModel implements Serializable {
    private String id;
    private String personName;

    public AddRecordContactModel(){

    }
    public AddRecordContactModel(String id ,String personName){
        super();
        this.id = id;
        this.personName = personName;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * 为什么要重写toString()呢？
     *
     * 因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
     */
    @Override
    public String toString() {
        return personName;

    }
}
