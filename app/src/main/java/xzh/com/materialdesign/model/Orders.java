package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Orders implements IEntity {
    private int order_id;//订单id
    private int order_type;//订单类型，记1为积分，2为金钱
    private String order_address;//送货地址
    private String order_destination;//取货地址
    private float order_reward;//悬赏价值（可以为积分或金钱）
    private float order_predict;//预测商品金额
    private String order_item;//商品类型（简称）
    private String order_time;//订单持续时间
    private String order_describe;//订单描述

    public void setOrderId(int orderId){
        order_id = orderId;
    }
    public void setOrderType(int type){
        order_type = type;
    }
    public void setOrderAddress(String address){
        order_address = address;
    }
    public void setOrderDestination(String destination){
        order_destination = destination;
    }
    public void setOrderReward(float reward){
        order_reward = reward;
    }
    public void setOrderPredict(float predict){
        order_predict = predict;
    }
    public void setOrderItem(String item){
        order_item = item;
    }
    public void setOrderTime(String time){
        order_time = time;
    }
    public void setOrderDescribe(String describe){
        order_describe = describe;
    }

    public int getOrderId(){
       return order_id;
    }
    public int getOrderType(){
        return order_type;
    }
    public String getOrderAddress( ){
        return order_address;
    }
    public String getOrderDestination(){
        return order_destination;
    }
    public float getOrderReward(){
        return order_reward;
    }
    public float getOrderPredict(){
        return order_predict;
    }
    public String getOrderItem(){
        return order_item;
    }
    public String getOrderTime() {
        return order_time;
    }
    public String getOrderDescribe() {
        return order_describe;
    }
}
