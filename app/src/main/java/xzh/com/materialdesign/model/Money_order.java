package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Money_order {
    private int order_id;//订单id
    private String address;//送货地址
    private String destination;//取货地址
    private float money_reward;//悬赏金额
    private float moy_predict;//预测商品金额
    private String item;//商品类型（简称）
    private String order_time;//订单持续时间
    private String describe;//订单描述

    public float getMoy_predict() {
        return moy_predict;
    }

    public void setMoy_predict(float moy_predict) {
        this.moy_predict = moy_predict;
    }


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getMoney_reward() {
        return money_reward;
    }

    public void setMoney_reward(float money_reward) {
        this.money_reward = money_reward;
    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
