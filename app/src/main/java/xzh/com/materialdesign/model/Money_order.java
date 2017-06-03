package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Money_order {
    private int orderId;//订单id
    private String address;//送货地址
    private String destination;//取货地址
    private float moneyReward;//悬赏金额
    private float moyPredict;//预测商品金额
    private String item;//商品类型（简称）
    private String orderTime;//订单持续时间
    private String describe;//订单描述

    public float getMoy_predict() {
        return moyPredict;
    }

    public void setMoy_predict(float moy_predict) {
        this.moyPredict = moy_predict;
    }


    public int getOrder_id() {
        return orderId;
    }

    public void setOrder_id(int order_id) {
        this.orderId = order_id;
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
        return moneyReward;
    }

    public void setMoney_reward(float money_reward) {
        this.moneyReward = money_reward;
    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getOrder_time() {
        return orderTime;
    }

    public void setOrder_time(String order_time) {
        this.orderTime = order_time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
