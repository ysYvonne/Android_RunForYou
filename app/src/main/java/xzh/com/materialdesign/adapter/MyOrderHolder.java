package xzh.com.materialdesign.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.LittleOrderBean;
import xzh.com.materialdesign.proxy.StateCode;

/**
 * Created by dz on 2017/5/14 on 10:55.
 */
public class MyOrderHolder extends  RecyclerView.ViewHolder {

    @InjectView(R.id.myorder_item)
    protected LinearLayout itemView;
    @InjectView(R.id.myorder_title)
    protected TextView item_title;
    @InjectView(R.id.myorder_state)
    protected TextView item_state;
    @InjectView(R.id.myorder_time)
    protected TextView item_time;
    @InjectView(R.id.myorder_money)
    protected TextView item_money;
    @InjectView(R.id.myorder_shop)
    protected TextView item_shop;
    @InjectView(R.id.myorder_des)
    protected TextView item_des;



//    @InjectView(R.id.item_content)
        //TextView itemContent;
//    @InjectView(R.id.item_image)
//    CircleImageView itemImage;
//    @InjectView(R.id.item_count)
//    TextView itemCount;





    public MyOrderHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
    public void loadInfo(LittleOrderBean lob){
        item_des.setText(lob.getOrderAddress());
        switch (lob.getType()){
            case StateCode.OrderType_Money:
                item_money.setText(String.valueOf((int)lob.getOrderReward())+"元");
                break;
            case StateCode.OrderType_Score:
                item_money.setText(String.valueOf((int)lob.getOrderReward())+"分");
                break;

        }

        item_shop.setText(lob.getShop());
        item_time.setText(lob.getStartTime());
        item_title.setText(lob.getOrderItem());
        switch (lob.getState()){
            case StateCode.Order_Arrived:
                item_state.setText("已送达");
                break;
            case StateCode.Order_Cancel:
                item_state.setText("已取消");
                break;
            case StateCode.Order_Complete:
                item_state.setText("已完成");
                break;
            case StateCode.Order_Evaluated:
                item_state.setText("已评价");
                break;
            case StateCode.Order_Receive:
                item_state.setText("已接单");
                break;
            case StateCode.Order_Sending:
                item_state.setText("正在配送");
                break;
            case StateCode.Order_Waiting:
                item_state.setText("待接单");
                break;

        }

    }

}
