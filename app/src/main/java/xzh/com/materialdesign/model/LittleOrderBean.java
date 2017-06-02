package xzh.com.materialdesign.model;

/**
 * Created by dz on 2017/6/1.
 */

public class LittleOrderBean implements IEntity{
    private int order_id;// 订单id
    private String order_item;// 商品类型（简称）
    private int order_type;//订单类型,积分1，现金2
    private float order_reward;// 悬赏价值（可以为积分或金钱）
    private String order_address;// 送货地址
    private String order_shop;//取货地址
    private String start_time;// 发起时间
    private int state;//订单状态，0未接单，1已接单，2正在配送，3到达地点，4订单完成，5评价完成。-1订单取消。

    public void setOrderId(int orderId) {
        order_id = orderId;
    }

    public void setOrderAddress(String address) {
        order_address = address;
    }

    public void setOrderReward(float reward) {
        order_reward = reward;
    }

    public void setOrderItem(String item) {
        order_item = item;
    }
    public void setState(int state1){
        state = state1;
    }
    public void setStartTime(String startTime){
        start_time = startTime;
    }
    public void setType(int Type){
        order_type = Type;
    }
    public void setShop(String Shop){
        order_shop = Shop;
    }

    public int getOrderId() {
        return order_id;
    }

    public String getOrderAddress() {
        return order_address;
    }

    public float getOrderReward() {
        return order_reward;
    }

    public String getOrderItem() {
        return order_item;
    }
    public int getState( ){
        return state ;
    }
    public String getStartTime(){
        return start_time;
    }
    public String getShop( ){
        return order_shop ;
    }
    public int getType(){
        return order_type;
    }

}
