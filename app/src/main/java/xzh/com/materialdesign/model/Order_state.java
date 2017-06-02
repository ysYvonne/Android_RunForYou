package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Order_state implements IEntity {
    private int orderStateId;//订单状态id
    private int orderId;//订单编号
    private int clientId;//下单人id
    private int deliveryId;//配送人id
    private int state;//订单状态，0未接单，1已接单，2正在配送，3到达地点，4订单完成，5评价完成。-1订单取消。
    private String startTime;//发起时间
    private String getTime;//得到物品，正在配送时间
    private String arriveTime;//到达时间
    private String overTime;//订单结束时间

    public void setOrderStateId(int orderStateId){
        orderStateId = orderStateId;
    }
    public void setOrderId(int orderId){
        orderId = orderId;
    }
    public void setClientId(int clientId){
        clientId = clientId;
    }
    public void setDeliveryId(int deliveryId){
        deliveryId = deliveryId;
    }
    public void setState(int state1){
        state = state1;
    }
    public void setOverTime(String overTime){
        overTime = overTime;
    }
    public void setStartTime(String startTime){
        startTime = startTime;
    }
    public void setArriveTime(String arriveTime){
        arriveTime = arriveTime;
    }
    public void setGetTime(String getTime){
        getTime = getTime;
    }

    public int getOrderStateId(){
        return orderStateId;
    }
    public int getOrderId( ){
        return orderId  ;
    }
    public int getClientId( ){
        return clientId  ;
    }
    public int getDeliveryId( ){
        return deliveryId  ;
    }
    public int getState( ){
        return state ;
    }
    public String getOverTime(){
        return overTime;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getArriveTime(){
        return arriveTime ;
    }
    public String getGetTime(){
        return getTime ;
    }
}
