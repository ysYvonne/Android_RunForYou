package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Order_state implements IEntity {
    private int order_state_id;//订单状态id
    private int order_id;//订单编号
    private int client_id;//下单人id
    private int delivery_id;//配送人id
    private int state;//订单状态，0未接单，1已接单，2正在配送，3到达地点，4订单完成，5评价完成。-1订单取消。
    private String start_time;//发起时间
    private String get_time;//得到物品，正在配送时间
    private String arrive_time;//到达时间
    private String over_time;//订单结束时间

    public void setOrderStateId(int orderStateId){
        order_state_id = orderStateId;
    }
    public void setOrderId(int orderId){
        order_id = orderId;
    }
    public void setClientId(int clientId){
        client_id = clientId;
    }
    public void setDeliveryId(int deliveryId){
        delivery_id = deliveryId;
    }
    public void setState(int state1){
        state = state1;
    }
    public void setOverTime(String overTime){
        over_time = overTime;
    }
    public void setStartTime(String startTime){
        start_time = startTime;
    }
    public void setArriveTime(String arriveTime){
        arrive_time = arriveTime;
    }
    public void setGetTime(String getTime){
        get_time = getTime;
    }

    public int getOrderStateId(){
        return order_state_id;
    }
    public int getOrderId( ){
        return order_id  ;
    }
    public int getClientId( ){
        return client_id  ;
    }
    public int getDeliveryId( ){
        return delivery_id  ;
    }
    public int getState( ){
        return state ;
    }
    public String getOverTime(){
        return over_time;
    }
    public String getStartTime(){
        return start_time;
    }
    public String getArriveTime(){
        return arrive_time ;
    }
    public String getGetTime(){
        return get_time ;
    }
}
