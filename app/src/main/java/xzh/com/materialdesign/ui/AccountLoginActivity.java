package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.app.ProgressDialog;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.MySharedPreferences;
import xzh.com.materialdesign.utils.ActivityHelper;
import android.widget.Toast;
import android.view.Gravity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import android.app.Activity;
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
                    logInSuccess();
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

    private void logInSuccess(){


            dialog = new ProgressDialog(mContext);
            dialog.setTitle("提示");
            dialog.setMessage("正在登陆，请稍后...");
            dialog.setCancelable(false);
            dialog.show();

             dialog.dismiss();

            //写入sharedPreferences
            MySharedPreferences msp=new MySharedPreferences("userId",this);
            msp.commit("userId","123456");

            ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);


        finish();
        //加入咱们自己的主界面
    }
}
