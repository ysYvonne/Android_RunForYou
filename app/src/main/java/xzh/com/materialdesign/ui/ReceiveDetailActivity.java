package xzh.com.materialdesign.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.Order_state;
import xzh.com.materialdesign.model.Orders;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;


/**
 * Created by dz on 2017/5/10.
 */

public class ReceiveDetailActivity extends AppCompatActivity {
    ImageButton navBack,changeState;
    TextView title,name,money,time,info,reward,method,shop,des,state;
    JSONObject parameter,updateParameter,newParameter;
    Order_state order_state,newState;
    int code;
    private Context mContext;
    Orders ordersInfo;
    Order_state orderState;

    private static final String TAG = MainActivity.class.getSimpleName();

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_transport_detail);

        navBack = (ImageButton)findViewById(R.id.nav_back);
        title = (TextView) findViewById(R.id.order_nav_title);
        name = (TextView) findViewById(R.id.order_transport_detail_name);
        money = (TextView) findViewById(R.id.order_transport_detail_sum);
        time = (TextView) findViewById(R.id.order_transport_detail_time);
        info = (TextView) findViewById(R.id.order_transport_detail_info);
        reward = (TextView) findViewById(R.id.order_transport_detail_money);
        method = (TextView) findViewById(R.id.order_transport_detail_method);
        shop = (TextView) findViewById(R.id.order_transport_detail_shop);
        des = (TextView) findViewById(R.id.order_transport_detail_des);
        state = (TextView) findViewById(R.id.order_transport_detail_state);
        changeState = (ImageButton) findViewById(R.id.order_transport_detail_change);

        Intent intent = getIntent();
        ordersInfo = (Orders) intent.getSerializableExtra("orderInfo");
        orderState = (Order_state) intent.getSerializableExtra("orderState");

        if(ordersInfo != null){
            title.setText(ordersInfo.getOrderItem());
            name.setText(ordersInfo.getContactName());
            reward.setText(String.valueOf(ordersInfo.getOrderReward()));

            if(ordersInfo.getOrderType() == 1){
                method.setText("分");
            }else
                method.setText("元");

            money.setText(String.valueOf(ordersInfo.getOrderPredict())+"元");
            info.setText(ordersInfo.getOrderDescribe());
            shop.setText(ordersInfo.getOrderDestination());
            des.setText(ordersInfo.getOrderAddress());
            time.setText(ordersInfo.getOrderTime());
            changeState(orderState.getState());
        }

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parameter = new JSONObject();
                try {
                    parameter.put("type","OrderInfo");
                    parameter.put("orderId", ordersInfo.getOrderId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(orderState.getState() == 4 ){

                }else {
                    connect();
                }


            }
        });
    }

    private void connect() {
        new Thread(){
            public void run() {
                order_state = (Order_state) Proxy.getWebData(StateCode.OrderState,parameter);

                updateParameter = new JSONObject();
                try {
                    updateParameter.put("type","OrderUpdate");
                    updateParameter.put("orderId", ordersInfo.getOrderId());
                    updateParameter.put("state",order_state.getState()+1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateConnect();
            };
        }.start();
    }

    private void updateConnect() {
        new Thread(){
            public void run() {
                code = (int) Proxy.getWebData(StateCode.OrderUpdate,updateParameter);
                connectFinish();
            };
        }.start();
    }

    private void connectFinish() {
        if (code == 0) {

            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("操作失败")

                    .setPositiveButton("确定", null)

                    .show();
        } else {

            //写入sharedPreferences
            if (code == -1) {
                new AlertDialog.Builder(mContext)

                        .setTitle("警告")

                        .setMessage("操作失败")

                        .setPositiveButton("确定", null)

                        .show();
            } else {
                new Thread(){
                    public void run() {
                        newState = (Order_state) Proxy.getWebData(StateCode.OrderState,parameter);
                        ReceiveDetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                changeState(newState.getState());
                            }
                        });
                    };
                }.start();
                }


            }
        }

    private void showDialog() {

        RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .session(3)
                .threshold(3)
                .ratingBarColor(R.color.base_color_text_black)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {
                        Log.i(TAG,"Feedback:" + feedback);
                    }
                })
                .build();

        ratingDialog.show();
    }

    private void changeState(int stateNum){
        switch (stateNum){
            case 0: {
                state.setText("未接单");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_right));
                break;
            }

            case 1: {
                state.setText("已接单");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_shop));
                break;
            }

            case 2: {
                state.setText("正在配送");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_deliver));
                break;
            }

            case 3: {
                state.setText("到达地点");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_notice));
                break;
            }

            case 4: {
                state.setText("订单完成");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_review));
                break;
            }

            case 5: {
                state.setText("评价完成");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_review));
                changeState.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case -1: {
                state.setText("订单取消");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_cancel));
                changeState.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }
        }
    }
}
