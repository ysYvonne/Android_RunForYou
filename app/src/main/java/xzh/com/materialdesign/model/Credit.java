package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Credit implements IEntity {
    private int creditId;//积分id
    private int userId;//用户id
    private int orderNum;//发布订单数量
    private int deliveryNum;//接受订单数量
    private int credit;//用户积分数

    public void setCreditId(int Cid){
        creditId = Cid;
    }
    public void setUserId(int userId){
        userId = userId;
    }
    public void setOrderNum(int Onum){
        orderNum = Onum;
    }
    public void setDeliveryNum(int deliveryNum){
        deliveryNum = deliveryNum;
    }
    public void setCredit(int c){
        credit = c;
    }

    public int getCreditId(){
        return creditId;
    }
    public int getUserId(){
        return  userId;
    }
    public int getOrderNum(){
        return orderNum;
    }
    public int getDeliveryNum(){
        return deliveryNum;
    }
    public int getCredit(){
        return credit;
    }
}
