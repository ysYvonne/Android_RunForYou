package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Credit implements IEntity {
    private int credit_id;//积分id
    private int user_id;//用户id
    private int order_num;//发布订单数量
    private int delivery_num;//接受订单数量
    private int credit;//用户积分数

    public void setCreditId(int Cid){
        credit_id = Cid;
    }
    public void setUserId(int userId){
        user_id = userId;
    }
    public void setOrderNum(int Onum){
        order_num = Onum;
    }
    public void setDeliveryNum(int deliveryNum){
        delivery_num = deliveryNum;
    }
    public void setCredit(int c){
        credit = c;
    }

    public int getCreditId(){
        return credit_id;
    }
    public int getUserId(){
        return  user_id;
    }
    public int getOrderNum(){
        return order_num;
    }
    public int getDeliveryNum(){
        return delivery_num;
    }
    public int getCredit(){
        return credit;
    }
}
