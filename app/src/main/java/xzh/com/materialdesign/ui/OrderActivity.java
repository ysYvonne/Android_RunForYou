package xzh.com.materialdesign.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;
import xzh.com.materialdesign.model.ModifyPerson;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by botan on 2017/4/22.
 */
public class OrderActivity extends BaseActivity {

    private Context mContext;
    TextView name,phone,btn_modify,btn_order,deliver_method;
    EditText short_info,long_info,des,shop,time,money,deliver;
    RadioButton change_method;
    RadioGroup method;
    ImageView back;
    String selectMethod;
    JSONObject parameter;
    private ProgressDialog dialog;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Looper.prepare();
            //connectFinish();
            Looper.loop();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v("tb","OrderActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        ButterKnife.inject(this);

        btn_modify = (TextView)findViewById(R.id.order_layout_modify_custom);
        btn_order = (TextView) findViewById(R.id.order_layout_confirm);
        name = (TextView) findViewById(R.id.order_layout_name);
        phone = (TextView) findViewById(R.id.order_layout_custom_phone);
        short_info = (EditText) findViewById(R.id.order_layout_short_info);
        long_info = (EditText) findViewById(R.id.order_layout_long_info);
        des = (EditText) findViewById(R.id.order_layout_des);
        shop = (EditText) findViewById(R.id.order_layout_shop);
        time = (EditText) findViewById(R.id.order_layout_time);
        money = (EditText) findViewById(R.id.order_layout_money);
        change_method = (RadioButton) findViewById(R.id.order_layout_choose_cash);
        deliver = (EditText) findViewById(R.id.order_layout_deliver);
        deliver_method = (TextView) findViewById(R.id.order_layout_deliver_method);
        back = (ImageView) findViewById(R.id.back_icon);
        method = (RadioGroup) findViewById(R.id.order_layout_choose);
        selectMethod = change_method.getText().toString();


        init();

        Intent intent = getIntent();
        ModifyPerson modifyInfo = (ModifyPerson) intent.getSerializableExtra("modify_info");
        name.setText(modifyInfo.getModify_name());
        phone.setText(modifyInfo.getModify_phone());

    }

    private void init() {
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(mContext,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    //if(check())
                    Order();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group,int checkedId){
                selectRadioBtn();
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(OrderActivity.this,ModifyActivity.class);
            }
        });

    }

    // 检测网络
    private boolean checkNetwork() {

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return true;
    }

    private void Order(){
        Log.v("dz","login");

        //完成对用户密码的包装
        parameter=new JSONObject();
        try {
            parameter.put("user_id",123456);
            parameter.put("type", "OrderPublish");
            parameter.put("name", name.getText());
            parameter.put("phone", phone.getText());
            parameter.put("short",short_info.getText());
            parameter.put("long",long_info.getText());
            parameter.put("des",des.getText());
            parameter.put("shop",shop.getText());
            parameter.put("time",time.getText());
            parameter.put("money",money.getText());
            parameter.put("deliver",deliver.getText());
            parameter.put("deliver_method",selectMethod);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog = new ProgressDialog(mContext);
        //弹出processdialog,必须要使用线程，不然无法显示
        dialog.setTitle("提示");
        dialog.setMessage("正在提交，请稍后...");
        dialog.setCancelable(false);
        dialog.show();

//        具体运行方法在handle属性中
        connect();

    }

    private void connect() {
        new Thread(){
            public void run() {
               // user=(User) Proxy.getWebData(StateCode.AccountLogin,parameter);
                Message msg = handler.obtainMessage();

               // msg.obj = user;
//                handler.sendEmptyMessage(0);
                handler.handleMessage(msg); //通知handler我完事儿啦,实际并没有接收msg只是一个信号，在属性user里完成了对user 的操作

            };
        }.start();
    }

    private void selectRadioBtn(){
        change_method = (RadioButton) findViewById(method.getCheckedRadioButtonId());
        selectMethod = change_method.getText().toString();
        if(selectMethod.equals("cash")){
            deliver_method.setText("元");
        }
        else
            deliver_method.setText("分");
    }

}
