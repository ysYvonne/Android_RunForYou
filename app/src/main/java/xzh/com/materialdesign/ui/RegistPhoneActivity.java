package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ypy.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.view.NavigationDrawerFragment;
import xzh.com.materialdesign.view.PullToLoadView;
import xzh.com.materialdesign.view.ThemeManager;

/**
 /**
 * Created by Towyer_pic on 2017/4/24.
 */

public class RegistPhoneActivity extends AppCompatActivity {
    private static String APPKEY = "1cb10bf97fef0";
    private static String APPSECRET = "21af261eb64cf0f2fc73cbd9095d4ba8";
    private boolean FLAG=false;
    private Context mContext;
    private Button send,next;
    private EditText phoneAccount,validationNum;
    private JSONObject parameter;
    Bundle mBundle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        send = (Button)findViewById(R.id.send_validationNum);
        next = (Button)findViewById(R.id.to_validname_button);

        //       ButterKnife.inject(this);
        //      EventBus.getDefault().register(this);
        mContext = RegistPhoneActivity.this;
        //绑定控件
        phoneAccount = (EditText) findViewById(R.id.RegisterPhoneAccount);
        validationNum=(EditText) findViewById(R.id.phone_validationNum_enter);

        init();
    }

    private void init() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check()){
                    next.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myAccentColor));
                    // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                    //发送验证码
                    if (!checkNetwork()) {
                        Toast toast = Toast.makeText(mContext,"网络未连接", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        checkPhoneExist();
                        next.setEnabled(true);
                        next.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myAccentColor));
                    }
                }

            }
        });
        next.setEnabled(false);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(check())

                    if(checkV() && checkValid()){

                        SMSSDK.submitVerificationCode("86", phoneAccount.getText().toString(), validationNum.getText().toString());//提交验证码  在eventHandler里面查看验证结果
                        //                   logIn();
                    }


            }
        });
    }

    private boolean check(){
        if(phoneAccount.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入手机号")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }

    private boolean checkV(){
        if(validationNum.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入验证码")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }
    private boolean checkValid(){
        if(validationNum.getText().toString().isEmpty()){
            AlertDialog.Builder builder  = new AlertDialog.Builder(mContext);
            builder.setTitle("提示" ) ;
            builder.setMessage("请输入验证码" ) ;
            builder.setPositiveButton("是" ,  null );
            builder.show();
            return false;
        }
        return true;
    }
    private boolean checkNetwork() {

        /*
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        */
        return true;
    }
    private void sendValidCode(){
        Log.v("ys", "发送验证码啦！！！！！");

        SMSSDK.initSDK(this, APPKEY, APPSECRET);
//注册短信回调
//EventHandler 在这个handler中可以查看到短信验证的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                switch (event) {
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.e("结果","验证成功");
                            RegistPhoneActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String phone = phoneAccount.getText().toString();
                                    ActivityHelper.startActivity(mContext,RegistValidNameActivity.class,"phone",phone);
                                }
                            });

                        } else {
                            Log.e("结果","验证失败");
                            RegistPhoneActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(mContext)

                                            .setTitle("提示")

                                            .setMessage("手机号或验证码错误")

                                            .setPositiveButton("确定", null)

                                            .show();
                                }
                            });
                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.e("结果","获取验证成功");
                            RegistPhoneActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(mContext,"获取验证成功,请稍候", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                }
                            });
                        } else {
                            Log.e("结果","获取验证失败");
                            RegistPhoneActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(mContext,"获取验证失败", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                }
                            });
                        }
                        break;
                }
            }
        });

        SMSSDK.getVerificationCode("86", phoneAccount.getText().toString());//发送短信验证码到手机号  86表示的是中国


    }
    private void checkPhoneExist(){
        Log.v("dz", "phone check exist");

        //完成对手机号的封装
        parameter=new JSONObject();
        try{
            parameter.put("type", "phoneExist");
            parameter.put("phoneNum", phoneAccount.getText());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(){
            public void run() {

                boolean exist=(Boolean) Proxy.getWebData(StateCode.PhoneValid,parameter);
                if(!exist){
                    Log.v("ys","手机号不存在,可以注册");
                    RegistPhoneActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sendValidCode();
                        }
                    });

                }else{
                    new AlertDialog.Builder(mContext)

                            .setTitle("提示")

                            .setMessage("手机号已存在请直接登录")

                            .setPositiveButton("确定", null)

                            .show();
                }


            };
        }.start();
    }

}
