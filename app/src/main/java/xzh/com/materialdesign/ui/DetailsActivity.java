package xzh.com.materialdesign.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.Api;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.model.ModifyPerson;
import xzh.com.materialdesign.model.Orders;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.renjiade_model.DetailEntity;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.utils.JsonUtil;
import xzh.com.materialdesign.view.UWebView;

/**
 * Created by xiangzhihong on 2016/3/18 on 16:43.
 */
public class DetailsActivity extends AppCompatActivity {


    @InjectView(R.id.nav_back)
    ImageButton navBack;
    ImageButton receiveBtn;
    ImageView userImage;
    TextView title,name,reward,method,info,shop,des,time,money;
    JSONObject parameter;
    int code;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("dz","DetailsActivity onCreate");

        setContentView(R.layout.order_detail);
        ButterKnife.inject(this);

        title = (TextView) findViewById(R.id.order_nav_title);
        userImage = (ImageView) findViewById(R.id.order_detail_image);
        name = (TextView) findViewById(R.id.order_detail_name);
        reward = (TextView) findViewById(R.id.order_detail_money);
        method = (TextView) findViewById(R.id.order_detail_method);
        money = (TextView) findViewById(R.id.order_detail_sum);
        info = (TextView) findViewById(R.id.order_detail_info);
        shop = (TextView) findViewById(R.id.order_detail_shop);
        des = (TextView) findViewById(R.id.order_detail_des);
        time = (TextView) findViewById(R.id.order_detail_time);
        receiveBtn = (ImageButton) findViewById(R.id.order_img_float_btn);
        mContext = DetailsActivity.this;
        init();
    }

    private void init() {
        //navTitle.setText("详情");
        Intent intent = getIntent();

        final Orders ordersInfo = (Orders) intent.getSerializableExtra("order_info");
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

        }

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        receiveBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提醒").setMessage("确认接单？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定按钮的点击事件
                        parameter = new JSONObject();
                        try {
                            parameter.put("userId",1);
                            parameter.put("type", "OrderReceive");
                            parameter.put("orderId", ordersInfo.getOrderId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        connect();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //取消按钮的点击事件
                    }
                }).show();
            }
        });
       // getDetailData();
    }

    private void connect() {
        new Thread(){
            public void run() {
                code = (int) Proxy.getWebData(StateCode.OrderRecive,parameter);

                DetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectFinish();
                    }
                });

            };
        }.start();
    }

    private void connectFinish() {
        if (code == 0) {

            new android.app.AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("接单失败")

                    .setPositiveButton("确定", null)

                    .show();
        } else {

            //写入sharedPreferences
            if (code == -1) {
                new android.app.AlertDialog.Builder(mContext)

                        .setTitle("警告")

                        .setMessage("无法提交")

                        .setPositiveButton("确定", null)

                        .show();
            } else {
                ActivityHelper.startActivity(mContext, MainActivity.class);
                finish();
            }
        }
    }


}
