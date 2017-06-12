package xzh.com.materialdesign.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;
import xzh.com.materialdesign.model.Order_state;
import xzh.com.materialdesign.model.Orders;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;

/**
 * Created by dz on 2017/5/10.
 */

public class MyorderDetailAvtivity extends BaseActivity {
    ImageButton navBack,drawBack,phone;
    TextView title,name,money,time,info,reward,method,shop,des,state,orderReview;
    JSONObject parameter,drawParameter,userParameter;
    Order_state order_state,newState;
    int code,stateNum,review;
    String phoneNum;
    private Context mContext;
    Orders ordersInfo;
    Order_state orderState;
    User user;
    private RatingDialog ratingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_myorder_detail);
        ButterKnife.inject(this);

        navBack = (ImageButton)findViewById(R.id.nav_back);
        title = (TextView) findViewById(R.id.order_nav_title);
        money = (TextView) findViewById(R.id.order_myorder_detail_sum);
        time = (TextView) findViewById(R.id.order_myorder_detail_time);
        info = (TextView) findViewById(R.id.order_myorder_detail_info);
        reward = (TextView) findViewById(R.id.order_myorder_detail_money);
        method = (TextView) findViewById(R.id.order_myorder_detail_method);
        shop = (TextView) findViewById(R.id.order_myorder_detail_shop);
        des = (TextView) findViewById(R.id.order_myorder_detail_des);
        state = (TextView) findViewById(R.id.order_myorder_detail_state);
        drawBack = (ImageButton) findViewById(R.id.order_myorder_detail_cancel);
        orderReview = (TextView) findViewById(R.id.order_myorder_detail_review);
        phone = (ImageButton) findViewById(R.id.order_myorder_detail_phone);




        Intent intent = getIntent();
        ordersInfo = (Orders) intent.getSerializableExtra("orderInfo");
        orderState = (Order_state) intent.getSerializableExtra("orderState");

        parameter = new JSONObject();
        try {
            parameter.put("type","OrderInfo");
            parameter.put("orderId", ordersInfo.getOrderId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(ordersInfo != null){
            title.setText(ordersInfo.getOrderItem());
            reward.setText(String.valueOf((int)ordersInfo.getOrderReward()));

            if(ordersInfo.getOrderType() == 1){
                method.setText("分");
            }else
                method.setText("元");

            money.setText(String.valueOf((int)ordersInfo.getOrderPredict())+"元");
            info.setText(ordersInfo.getOrderDescribe());
            shop.setText(ordersInfo.getOrderDestination());
            des.setText(ordersInfo.getOrderAddress());
            time.setText(orderState.getStartTime());
            changeState(orderState.getState());
            stateNum = orderState.getState();
            if(stateNum == 5){
                getReview();
            }
        }

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        drawBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateNum == 0) {
                    drawParameter = new JSONObject();
                    try {
                        drawParameter.put("type", "OrderDrawback");
                        drawParameter.put("orderId", ordersInfo.getOrderId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    drawbackOrder();
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stateNum == 1 || stateNum == 2 || stateNum == 3 || stateNum == 4) {
                    userParameter = new JSONObject();
                    try {
                        userParameter.put("type", "getUser");
                        userParameter.put("userId", orderState.getDeliveryId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getUser();
                }
            }
        });

    }

    private void getReview() {
        new Thread(){
            public void run() {
                review = (int) Proxy.getWebData(StateCode.GetReview,parameter);
                MyorderDetailAvtivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderReview.setText(review+" 星");
                    }
                });
            };
        }.start();
    }

    private void getUser() {
        new Thread(){
            public void run() {
                user = (User) Proxy.getWebData(StateCode.PersonalInfo,userParameter);
                phoneNum = user.getPhoneNum();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phoneNum ));
                startActivity(phoneIntent);
            };
        }.start();
    }

    private void drawbackOrder() {
        new Thread(){
            public void run() {
                code = (int) Proxy.getWebData(StateCode.OrderDrawback,drawParameter);
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
                        stateNum = newState.getState();
                        MyorderDetailAvtivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                changeState(stateNum);
                            }
                        });
                    };
                }.start();
            }

        }
    }


    private void changeState(int stateNum){
        switch (stateNum){
            case 0: {
                state.setText("未接单");
                phone.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case 1: {
                state.setText("已接单");
                drawBack.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case 2: {
                state.setText("正在配送");
                drawBack.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case 3: {
                state.setText("已送达");
                drawBack.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case 4: {
                state.setText("待评价");
                drawBack.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case 5: {
                state.setText("已评价");
                phone.setBackgroundResource(R.drawable.fab_finish_bg);
                drawBack.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case -1: {
                state.setText("已取消");
                phone.setBackgroundResource(R.drawable.fab_finish_bg);
                drawBack.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_cancel));
                drawBack.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }
        }
    }

}
