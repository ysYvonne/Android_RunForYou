package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Order_state {
    private int order_state_id;//订单状态id
    private int order_id;//订单编号
    private int order_type;//1为金钱订单，2为积分订单
    private int user_id;//用户id
    private int delivery_id;//配送人id
    private int state;//订单状态，0未接单，1已接单，2正在配送，3到达地点，4订单完成，5评价完成。-1订单取消。
    private String start_time;//发起时间
    private String get_time;//得到物品，正在配送时间
    private String arrive_time;//到达时间
    private String over_time;//订单结束时间
}
