package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Order_Review implements IEntity {
    private int review_id;//评价id
    private int order_id;//订单id
    private int review_type;//评价类型，1为好评，2为中评，3为差评
    private String review_time;//评价时间

    public void setReviewId(int Rid){
        review_id = Rid;
    }
    public void setOrderId(int Oid){
        order_id = Oid;
    }
    public void setReviewType(int Rtype){
        review_type = Rtype;
    }
    public void setReviewTime(String Rtime){
        review_time = Rtime;
    }

    public int getReviewId(){
        return review_id;
    }
    public int getOrderId(){
        return order_id;
    }
    public int getReviewType(){
        return review_type;
    }
    public String getReviewTime(){
        return review_time;
    }
}
