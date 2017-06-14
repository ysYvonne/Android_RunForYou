package xzh.com.materialdesign.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.DialogInterface;

import org.json.JSONException;
import org.json.JSONObject;

import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.Order_state;
import xzh.com.materialdesign.model.Orders;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Command;


/**
 * Created by dz on 2017/5/10.
 */

public class ReceiveDetailActivity extends AppCompatActivity {
    ImageButton navBack,changeState,phone;
    TextView title,name,money,time,info,reward,method,shop,des,state;
    JSONObject parameter,updateParameter,finishParemeter,reviewParemeter,userParameter;
    Order_state order_state,newState;
    int code,stateNum,review;
    String phoneNum;
    private Context mContext;
    Orders ordersInfo;
    Order_state orderState;
    private RatingDialog ratingDialog;
    User user;

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
        phone = (ImageButton) findViewById(R.id.order_transport_detail_phone);

        Intent intent = getIntent();
        ordersInfo = (Orders) intent.getSerializableExtra("orderInfo");
        orderState = (Order_state) intent.getSerializableExtra("orderState");

        if(ordersInfo != null){
            title.setText(ordersInfo.getOrderItem());
            name.setText(ordersInfo.getContactName());
            reward.setText(String.valueOf((int)ordersInfo.getOrderReward()));

            if(ordersInfo.getOrderType() == 1){
                method.setText("分");
            }else
                method.setText("元");

            money.setText(String.valueOf((int)ordersInfo.getOrderPredict())+"元");
            info.setText(ordersInfo.getOrderDescribe());
            shop.setText(ordersInfo.getOrderDestination());
            des.setText(ordersInfo.getOrderAddress());
            time.setText(ordersInfo.getOrderTime());
            changeState(orderState.getState());
            stateNum = orderState.getState();
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

                if(stateNum == 3 ){
                    orderFinish();
                }else {
                    if(stateNum == 4 ){
//                        orderReview();
                        showDialog();
                    }else {
                        orderUpdate();
                    }
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

    private void getUser() {
        new Thread(){
            public void run() {
//                user = (User) Proxy.getWebData(StateCode.PersonalInfo,userParameter);
                user=(User)new Command().personalInfo(parameter);
                phoneNum = user.getPhoneNum();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phoneNum ));
                startActivity(phoneIntent);
            };
        }.start();
    }

    private void orderFinish(){
        new Thread(){
            public void run() {

                finishParemeter = new JSONObject();
                try {
                    finishParemeter.put("type","OrderFinish");
                    finishParemeter.put("orderId", ordersInfo.getOrderId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finishConnect();
            };
        }.start();
    }

    private void orderReview(){
        new Thread(){
            public void run() {
                reviewParemeter = new JSONObject();
                try {
                    reviewParemeter.put("type","OrderJudge");
                    reviewParemeter.put("orderId", ordersInfo.getOrderId());
                    reviewParemeter.put("review",review);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reviewConnect();
            };
        }.start();
    }

    private void orderUpdate() {
        new Thread(){
            public void run() {
//                order_state = (Order_state) Proxy.getWebData(StateCode.OrderState,parameter);
                order_state=(Order_state)new Command().orderState(parameter);
                stateNum = order_state.getState();
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

    private void finishConnect() {
        new Thread(){
            public void run() {
//                code = (int) Proxy.getWebData(StateCode.OrderFinish,finishParemeter);
                code=(int)new Command().orderFinish(parameter);
                connectFinish();
            };
        }.start();
    }

    private void updateConnect() {
        new Thread(){
            public void run() {
//                code = (int) Proxy.getWebData(StateCode.OrderUpdate,updateParameter);
                code=(int)new Command().orderUpdate(parameter);
                connectFinish();
            };
        }.start();
    }

    private void reviewConnect() {
        new Thread(){
            public void run() {
//                code = (int) Proxy.getWebData(StateCode.OrderReview,reviewParemeter);
                code=(int)new Command().orderReview(parameter);
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
//                        newState = (Order_state) Proxy.getWebData(StateCode.OrderState,parameter);
                        newState=(Order_state)new Command().orderState(parameter);
                        stateNum = newState.getState();
                        ReceiveDetailActivity.this.runOnUiThread(new Runnable() {
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

    private void showDialog() {

        ratingDialog = new RatingDialog.Builder(this)
                .ratingBarColor(R.color.base_color_text_black)
                .setPositiveButton(new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        ratingDialog.dismiss();
                        orderReview();
                    }
                }).setCallBackListener(new RatingDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(float msg) {//具体接口的实现
                            review = (int)msg;
                    }
                })
                .setNegativeButton(new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        review = -1;
                        ratingDialog.dismiss();
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
                state.setText("已送达");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_notice));
                break;
            }

            case 4: {
                state.setText("待评价");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_review));
                break;
            }

            case 5: {
                state.setText("已评价");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_review));
                changeState.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }

            case -1: {
                state.setText("已取消");
                changeState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_cancel));
                changeState.setBackgroundResource(R.drawable.fab_finish_bg);
                break;
            }
        }
    }
}
