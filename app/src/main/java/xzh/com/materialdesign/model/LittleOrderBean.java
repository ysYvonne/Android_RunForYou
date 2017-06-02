package xzh.com.materialdesign.model;

/**
 * Created by dz on 2017/6/1.
 */

public class LittleOrderBean implements IEntity{
    private int orderId;// 订单id
    private String orderItem;// 商品类型（简称）
    private int orderType;//订单类型,积分1，现金2
    private float orderReward;// 悬赏价值（可以为积分或金钱）
    private String orderAddress;// 送货地址
    private String orderShop;//取货地址
    private String startTime;// 发起时间
    private int state;//订单状态，0未接单，1已接单，2正在配送，3到达地点，4订单完成，5评价完成。-1订单取消。

    public void setOrderId(int orderId) {
        orderId = orderId;
    }

    public void setOrderAddress(String address) {
        orderAddress = address;
    }

    public void setOrderReward(float reward) {
        orderReward = reward;
    }

    public void setOrderItem(String item) {
        orderItem = item;
    }
    public void setState(int state1){
        state = state1;
    }
    public void setStartTime(String startTime){
        startTime = startTime;
    }
    public void setType(int Type){
        orderType = Type;
    }
    public void setShop(String Shop){
        orderShop = Shop;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public float getOrderReward() {
        return orderReward;
    }

    public String getOrderItem() {
        return orderItem;
    }
    public int getState( ){
        return state ;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getShop( ){
        return orderShop ;
    }
    public int getType(){
        return orderType;
    }

}
