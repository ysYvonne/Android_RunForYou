package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.app.ProgressDialog;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.MySharedPreferences;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
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

import java.util.List;

/**
 * Created by yisheng on 2017/4/23.
 */

public class AccountLoginActivity extends AppCompatActivity {

    private Context mContext;
    Button btn;
    private ProgressDialog dialog;
    TextView register,phone,account;
    private String info;
    private TextView infotv;
    EditText username, password;
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("dz","AccountLoginActivity onCreate");
        //如果已经登录过直接跳到主页面，全部完成之后直接取消注释就行
        if((new MySharedPreferences("userId",this).getValue("userId"))!=null) {
//            ActivityHelper.startActivity(this, MainActivity.class);
//            finish();
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_login);
        btn = (Button)findViewById(R.id.signin_button);
        register = (TextView)findViewById(R.id.register_link) ;
        phone = (TextView)findViewById(R.id.phone_login);
        account = (TextView)findViewById(R.id.account_login);
        password = (EditText)findViewById(R.id.account) ;
        username = (EditText)findViewById(R.id.password) ;

        mContext = AccountLoginActivity.this;

        dialog = new ProgressDialog(mContext);

        init();
    }


    private void init() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(AccountLoginActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    if(check())logIn();
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

        /*
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        */
        return true;
    }
    private boolean check() {
        if(username.getText()==null){
            AlertDialog.Builder builder  = new AlertDialog.Builder(mContext);
            builder.setTitle("提示" ) ;
            builder.setMessage("请输入账号" ) ;
            builder.setPositiveButton("是" ,  null );
            builder.show();
        }else if(password.getText()==null){
            AlertDialog.Builder builder  = new AlertDialog.Builder(mContext);
            builder.setTitle("提示" ) ;
            builder.setMessage("请输入密码" ) ;
            builder.setPositiveButton("是" ,  null );
            builder.show();
        }
        return true;
    }
    private void logIn(){



        dialog.setTitle("提示");
        dialog.setMessage("正在登陆，请稍后...");
        dialog.setCancelable(false);
        dialog.show();
        JSONObject parameter=new JSONObject();
        try {
            parameter.put("username", username.getText());
            parameter.put("password", password.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        List list= Proxy.getWebData(new User().getClass(), StateCode.AccountLogin,parameter);

        dialog.dismiss();

        if(list.isEmpty()){
            dialog.setTitle("提示");
            dialog.setMessage("您输入的帐号或密码错误");
            dialog.setCancelable(false);
            dialog.show();
        }else{

            User user=(User)list.get(0);
            //写入sharedPreferences
            MySharedPreferences msp=new MySharedPreferences("userId",this);
            msp.commit("userId",String.valueOf(user.getUserId()));

            ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class,"user",user);


            finish();
        }

    }




}
