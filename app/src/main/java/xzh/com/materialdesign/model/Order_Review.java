package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Order_Review implements IEntity {
    private int reviewId;//评价id
    private int orderId;//订单id
    private int reviewType;//评价类型，1为好评，2为中评，3为差评
    private String reviewTime;//评价时间

    public void setReviewId(int Rid){
        reviewId = Rid;
    }
    public void setOrderId(int Oid){
        orderId = Oid;
    }
    public void setReviewType(int Rtype){
        reviewType = Rtype;
    }
    public void setReviewTime(String Rtime){
        reviewTime = Rtime;
    }

    public int getReviewId(){
        return reviewId;
    }
    public int getOrderId(){
        return orderId;
    }
    public int getReviewType(){
        return reviewType;
    }
    public String getReviewTime(){
        return reviewTime;
    }
}
