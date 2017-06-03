package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.app.ProgressDialog;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.api.MySharedPreferences;
import xzh.com.materialdesign.model.LittleOrderBean;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.thread.AccountLoginThread;
import xzh.com.materialdesign.utils.ActivityHelper;
import android.widget.Toast;
import android.view.Gravity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yisheng on 2017/4/23.
 */

public class AccountLoginActivity extends AppCompatActivity {
    private Context mContext;
    Button btn;
    private ProgressDialog dialog;
    TextView register,phone;
    private String info;
    private TextView infotv;
    EditText userEmail, password;
    JSONObject parameter;
    User user;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("dz","AccountLoginActivity onCreate");
        mContext = AccountLoginActivity.this;
        //如果已经登录过直接跳到主页面，全部完成之后直接取消注释就行
        if(ControlUser.getUser(mContext)!=null){
//            Log.v("dz","AccountLogin userId is "+new MySharedPreferences("userId",this).getValue("userId"));
//            ActivityHelper.startActivity(this, MainActivity.class);
//            finish();
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_login);
        btn = (Button)findViewById(R.id.signin_button);
        register = (TextView)findViewById(R.id.register_link);
        phone = (TextView)findViewById(R.id.phone_login);

        userEmail = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password) ;

        init();
    }


    private void init() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Log.v("dz","w网络未连接");
                    Toast toast = Toast.makeText(mContext,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
//                    if(check())
                        logIn();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(AccountLoginActivity.this,RegistPhoneActivity.class);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                ActivityHelper.startActivity(AccountLoginActivity.this,PhoneLoginActivity.class);
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
    private boolean check() {
        if(userEmail.getText().toString().isEmpty()){
            Log.v("dz","username is empty");
            AlertDialog.Builder builder  = new AlertDialog.Builder(mContext);
            builder.setTitle("提示" ) ;
            builder.setMessage("请输入邮箱号") ;
            builder.setPositiveButton("是" ,  null );
            builder.show();
            return false;
        }
        if(password.getText().toString().isEmpty()){
            AlertDialog.Builder builder  = new AlertDialog.Builder(mContext);
            builder.setTitle("提示" ) ;
            builder.setMessage("请输入密码" ) ;
            builder.setPositiveButton("是" ,  null );
            builder.show();
            return false;
        }
        return true;
    }

    private void logIn(){
        Log.v("dz","login");

        //完成对用户密码的包装
        parameter=new JSONObject();
        try {

            parameter.put("type","emailLogin");
//            parameter.put("email", userEmail.getText());
//            parameter.put("password", password.getText());

            parameter.put("email", "123456@bjtu.edu.cn");
            parameter.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog = new ProgressDialog(mContext);
        //弹出processdialog,必须要使用线程，不然无法显示
        dialog.setTitle("提示");
        dialog.setMessage("正在登陆，请稍后...");
        dialog.setCancelable(false);
        dialog.show();

        connect();

    }

    private void connect() {
//        new Thread(){
//            public void run() {
//                user=(User)Proxy.getWebData(StateCode.AccountLogin,parameter);
//                Message msg = handler.obtainMessage();
//
//                msg.obj = user;
////              handler.sendEmptyMessage(0);
//                handler.handleMessage(msg); //通知handler我完事儿啦,实际并没有接收msg只是一个信号，在属性user里完成了对user 的操作
//
//            };
//        }.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                user=(User)Proxy.getWebData(StateCode.AccountLogin,parameter);
                // 在下面这个方法里可以做任何更新UI的操作
                AccountLoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectFinish();
                    }
                });
            }
        }).start();
    }


    //连接完网络请求后需要做的事情
    private void connectFinish() {
        dialog.dismiss();
        if (user==null) {

            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("用户名或密码错误")

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



}
