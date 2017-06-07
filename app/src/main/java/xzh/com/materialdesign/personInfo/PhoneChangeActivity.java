package xzh.com.materialdesign.personInfo;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.ui.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Flora on 2017/4/24.
 */

public class PhoneChangeActivity extends AppCompatActivity {

    private static String APPKEY = "1cb10bf97fef0";
    private static String APPSECRET = "21af261eb64cf0f2fc73cbd9095d4ba8";

    final int VALID=1;
    final int INVALID=-1;

    int flag = -1;

    @InjectView(R.id.identify_button)
    Button sendButton;
    @InjectView(R.id.phone_ok_button)
    Button okButton;
    @InjectView(R.id.phone_cancel_button)
    Button cancelButton;

    Bundle pInfoBundle;

    private Context mContext;

    JSONObject parameter;

    String phone;
    EditText phoneChange, validCode;

    User user;
    int code = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ys","PhoneChange onCreate");
        setContentView(R.layout.activity_phone_change);

        mContext = PhoneChangeActivity.this;
        ButterKnife.inject(this);

        pInfoBundle = getIntent().getExtras();
        phone = pInfoBundle.getString("Phone");

        phoneChange = (EditText)findViewById(R.id.new_phone_editText);
        validCode = (EditText)findViewById(R.id.identify_editText);

        init();
    }

    private void init(){

        phoneChange.setText(phone);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myAccentColor));
                    // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                    //发送验证码

                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(mContext,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    if(check()){
                        okButton.setEnabled(true);
                        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myPrimaryColor));
                        checkPhoneExist();
                    }

                }
            }
        });

        okButton.setEnabled(false);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneChange();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

 //               pInfoBundle.putString("Phone", phone);
                finish();
                ActivityHelper.startActivity(PhoneChangeActivity.this,PersonalInfoActivity.class);
            }
        });
    }

    private void checkPhoneExist(){
        Log.v("ys", "phone check exist");

        //完成对手机号的封装
        phone = phoneChange.getText().toString();

        parameter=new JSONObject();
        try{
            parameter.put("type", "phoneExist");
            parameter.put("phoneNum", phone);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(){
            public void run() {

                boolean exist=(Boolean)Proxy.getWebData(StateCode.PhoneValid,parameter);
                if(exist){
                    //发送验证码 第三方
                    Log.v("ys","手机号存在");

                    PhoneChangeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(mContext)
                                    .setTitle("提示" )
                                    .setMessage("手机号已存在，请重新输入" )
                                    .setPositiveButton("确定", null)
                                    .show();
                        }
                    });

                }else{
                    Log.v("ys","手机号不存在");

                    PhoneChangeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sendValidCode();
                        }
                    });
                }


            };
        }.start();

        flag = 1;
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
                        } else {
                            Log.e("结果","验证失败");
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

        SMSSDK.getVerificationCode("86", phone);//发送短信验证码到手机号  86表示的是中国

    }

    private void phoneChange(){

        if(check() && checkV() && checkExist()){
            Log.v("ys", "start 更改手机");

            SMSSDK.submitVerificationCode("86", phone, validCode.getText().toString());//提交验证码

            phone = phoneChange.getText().toString();
            Log.v("ys", "更改后手机号码： "+ phone);
            pInfoBundle.putString("Phone", phone);

            parameter=new JSONObject();
            try{

                parameter.put("type","updateInfomation");
                parameter.put("userId",pInfoBundle.getString("userId"));
                parameter.put("column","phoneNum");
                parameter.put("value", phone);

            }catch (JSONException e) {
                e.printStackTrace();
            }

            connect();
        }

    }


    private void connect(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                user=(User) Proxy.getWebData(StateCode.PersonalInfo,parameter);
                code = user.getSex();

                // 在下面这个方法里可以做任何更新UI的操作
                PhoneChangeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectFinish(code);
                    }
                });

            }
        }).start();
    }

    private void connectFinish(int code){

        if(code == 1){
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("更改成功")

                    //                   .setPositiveButton("确定", null)

                    .show();

            ActivityHelper.startActivity(mContext,PersonalInfoActivity.class, pInfoBundle);
            finish();

        }else{
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("更改失败")

                    .setPositiveButton("确定", null)

                    .show();
        }

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

    private boolean check(){
        if(phoneChange.getText().toString().isEmpty()){
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
        if(validCode.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入验证码")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }

    private boolean checkExist(){

        if(flag == -1){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("未进行用手机号验证，请验证")

                    .setPositiveButton("确定", null)

                    .show();
            return false;

        }else if(flag == 1){

            return true;

        }else{

            return false;

        }
    }
}
