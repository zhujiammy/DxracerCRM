package com.example.dxracer.dxracercrm.Model;

import java.io.Serializable;

public class AccessChannelsModel implements Serializable {
    private String label;
    private String key;

    public AccessChannelsModel(){

    }
    public AccessChannelsModel(String label ,String key){
        super();
        this.label = label;
        this.key = key;

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 为什么要重写toString()呢？
     *
     * 因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
     */
    @Override
    public String toString() {
        return key;

    }
}
