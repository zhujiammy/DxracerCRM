package com.example.dxracer.dxracercrm.Model;

import java.io.Serializable;

public class MineAndParentsModel implements Serializable {

    private String oppo_price_discount;
    private String user_id;


    public MineAndParentsModel(String oppo_price_discount,String user_id){
        this.oppo_price_discount = oppo_price_discount;
        this.user_id = user_id;
    }

    

    public String getOppo_price_discount() {
        return oppo_price_discount;
    }

    public void setOppo_price_discount(String oppo_price_discount) {
        this.oppo_price_discount = oppo_price_discount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * 为什么要重写toString()呢？
     *
     * 因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
     */
    @Override
    public String toString() {
        return oppo_price_discount;

    }
}
