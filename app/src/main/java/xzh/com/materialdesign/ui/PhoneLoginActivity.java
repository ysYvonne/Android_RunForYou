package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Command;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;



/**
 * Created by Towyer_pic on 2017/4/24.
 */

public class PhoneLoginActivity extends AppCompatActivity {
    private static String APPKEY = "1cb10bf97fef0";
    private static String APPSECRET = "21af261eb64cf0f2fc73cbd9095d4ba8";

//    private ProgressDialog dialog;
    private Context mContext;
    List list=new ArrayList();
    JSONObject parameter;

    AlertDialog.Builder builder;

    Button btn,send;
    User user;
    private AlertDialog.Builder alertDialog;

    TextView register,account;

    EditText phone,validationNum;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("dz","phoneLogin onCreate");

        setContentView(R.layout.activity_phone_login);

        btn = (Button)findViewById(R.id.signin_button);

        send = (Button)findViewById(R.id.send_validationNum);
        register = (TextView)findViewById(R.id.register_link) ;
        account = (TextView)findViewById(R.id.account_login);

        phone=(EditText)findViewById(R.id.account_phone);
        validationNum=(EditText)findViewById(R.id.validationNum_phone);

        //       ButterKnife.inject(this);
        //      EventBus.getDefault().register(this);
        mContext = PhoneLoginActivity.this;
        builder = new AlertDialog.Builder(mContext);

        init();
    }


    private void init() {

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                //发送验证码
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(mContext,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    if(checkPhone())
                        btn.setEnabled(true);
                        btn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myPrimaryColor));

                        checkPhoneExist();
                }
            }
        });

        btn.setEnabled(false);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                //加入咱们自己的主界面

                if(checkPhone() && checkValid()){

//                    SMSSDK.submitVerificationCode("86", phone.getText().toString(), validationNum.getText().toString());//提交验证码  在eventHandler里面查看验证结果

                    logIn();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(mContext,RegistPhoneActivity.class);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                ActivityHelper.startActivity(mContext,AccountLoginActivity.class);
            }
        });

    }
    // 检测网络
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
                            PhoneLoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    logIn();
                                }
                            });

                        } else {
                            Log.e("结果","验证失败");
                            PhoneLoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    builder.setTitle("提示" ) ;
                                    builder.setMessage("验证码错误" ) ;
                                    builder.setPositiveButton("确定" ,  null );
                                    builder.show();
                                }
                            });
                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.e("结果","获取验证成功");
                        } else {
                            Log.e("结果","获取验证失败");
                        }
                        break;
                }
            }
        });

        SMSSDK.getVerificationCode("86", phone.getText().toString());//发送短信验证码到手机号  86表示的是中国


    }



    private void checkPhoneExist(){
        Log.v("dz", "phone check exist");

        //完成对手机号的封装
        parameter=new JSONObject();
        try{
            parameter.put("type", "phoneExist");
            parameter.put("phoneNum", phone.getText());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(){
            public void run() {

//                boolean exist=(Boolean)Proxy.getWebData(StateCode.PhoneValid,parameter);
                boolean exist=(Boolean)new Command().phoneValid(parameter);
                if(exist){
                    Log.v("ys","手机号存在,开始发送验证码");
                    PhoneLoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            sendValidCode();
                        }
                    });

                }else{
                    PhoneLoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("ys","手机号不存在false");
                            builder.setTitle("提示" ) ;
                            builder.setMessage("手机号不存在，请重新输入" ) ;
                            builder.setPositiveButton("确定" ,  null );
                            builder.show();
                        }
                    });

                }


            };
        }.start();
    }

    private void logIn(){
        Log.v("ys","手机登录中");

//        dialog = new ProgressDialog(mContext);

        //完成对手机号和验证码的包装
        parameter=new JSONObject();
        try {
            parameter.put("type", "phoneLogin");
            parameter.put("phoneNum", phone.getText());

            //添加第三方 发送验证码，传不传后端都行，完全前端判断验证码是否符合
            parameter.put("password", validationNum.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //弹出processdialog,必须要使用线程，不然无法显示
//        dialog.setTitle("提示");
//        dialog.setMessage("正在登陆，请稍后...");
//        dialog.setCancelable(false);
//        dialog.show();


        connect();

    }
    private void connect(){
        new Thread(){
            public void run() {

//                user=(User)Proxy.getWebData(sc,parameter);
                user=(User)new Command().phoneLogin(parameter);
                PhoneLoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectFinish();
                    }
                });

            };
        }.start();
    }

    //连接完网络请求后需要做的事情
    private void connectFinish() {

//        dialog.dismiss();

        if (user==null) {

            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("手机号或验证码错误")

                    .setPositiveButton("确定", null)

                    .show();
        } else {

            //写入sharedPreferences
            if (user.getUserId() < 0) {

                new AlertDialog.Builder(mContext)

                        .setTitle("警告")

                        .setMessage(StateCode.UserIdNull)

                        .setPositiveButton("确定", null)

                        .show();
            } else {
                ControlUser.addUser(user,mContext);

                ActivityHelper.startActivity(mContext, MainActivity.class);
                finish();
            }
        }
    }

    private boolean checkPhone() {
        if(phone.getText().toString().isEmpty()){
            Log.v("dz","phone is empty");
            AlertDialog.Builder builder  = new AlertDialog.Builder(mContext);
            builder.setTitle("提示" ) ;
            builder.setMessage("请输入手机号") ;
            builder.setPositiveButton("是" ,  null );
            builder.show();
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
}

