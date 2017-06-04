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
    private String contactName;
    private String contactPhone;



    private int orderId;//订单id
    private int orderType;//订单类型，记1为积分，2为金钱
    private String orderAddress;//送货地址
    private String orderDestination;//取货地址
    private float orderReward;//悬赏价值（可以为积分或金钱）
    private float orderPredict;//预测商品金额
    private String orderItem;//商品类型（简称）
    private String orderTime;//订单持续时间
    private String orderDescribe;//订单描述


    public void setOrderId(int orderId){
        orderId = orderId;
    }
    public void setOrderType(int type){
        orderType = type;
    }
    public void setOrderAddress(String address){
        orderAddress = address;
    }
    public void setOrderDestination(String destination){
        orderDestination = destination;
    }
    public void setOrderReward(float reward){
        orderReward = reward;
    }
    public void setOrderPredict(float predict){
        orderPredict = predict;
    }
    public void setOrderItem(String item){
        orderItem = item;
    }
    public void setOrderTime(String time){
        orderTime = time;
    }
    public void setOrderDescribe(String describe){
        orderDescribe = describe;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public int getOrderId(){
       return orderId;
    }
    public int getOrderType(){
        return orderType;
    }
    public String getOrderAddress( ){
        return orderAddress;
    }
    public String getOrderDestination(){
        return orderDestination;
    }
    public float getOrderReward(){
        return orderReward;
    }
    public float getOrderPredict(){
        return orderPredict;
    }
    public String getOrderItem(){
        return orderItem;
    }
    public String getOrderTime() {
        return orderTime;
    }
    public String getOrderDescribe() {
        return orderDescribe;
    }
    public String getContactName() {
        return contactName;
    }
    public String getContactPhone() {
        return contactPhone;
    }
}
