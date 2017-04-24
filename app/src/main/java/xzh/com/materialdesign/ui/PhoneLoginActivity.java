package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Towyer_pic on 2017/4/24.
 */

public class PhoneLoginActivity extends AppCompatActivity {

    private Context mContext;
    Button btn,send;

    TextView register,phone,account;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone_login);

        btn = (Button)findViewById(R.id.signin_button);
        send = (Button)findViewById(R.id.send_validationNum);
        register = (TextView)findViewById(R.id.register_link) ;
        phone = (TextView)findViewById(R.id.phone_login);
        account = (TextView)findViewById(R.id.account_login);
        //       ButterKnife.inject(this);
        //      EventBus.getDefault().register(this);
        mContext = PhoneLoginActivity.this;

        init();
    }


    private void init() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                //加入咱们自己的主界面
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                //发送验证码
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
}

