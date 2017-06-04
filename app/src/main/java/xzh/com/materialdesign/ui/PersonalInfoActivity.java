package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.personInfo.*;

/**
 * Created by Flora on 2017/4/24.
 */

public class PersonalInfoActivity extends AppCompatActivity {


    @InjectView(R.id.nickname_info)
    RelativeLayout nicknameInfo;
    @InjectView(R.id.pwd_info)
    RelativeLayout pwdInfo;
    @InjectView(R.id.sex_info)
    RelativeLayout sexInfo;
    @InjectView(R.id.name_info)
    RelativeLayout nameInfo;
    @InjectView(R.id.age_info)
    RelativeLayout ageInfo;
    @InjectView(R.id.email_info)
    RelativeLayout emailInfo;
    @InjectView(R.id.phone_info)
    RelativeLayout phoneInfo;

    private Context mContext;

    Bundle mBundle;

    JSONObject parameter;
    User user;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Looper.prepare();
//            connectFinish();
            Looper.loop();
        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        mContext = PersonalInfoActivity.this;
        mBundle = getIntent().getExtras();

        ButterKnife.inject(this);
        init();
    }

    private void init(){
        Log.v("ys", "Start Personal Information");


        ImageView back = (ImageView) findViewById(R.id.nav_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nicknameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(mContext,NicknameActivity.class);
            }
        });

        pwdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true){
                    ActivityHelper.startActivity(mContext,PwdChangeActivity.class);
                }
                else
                    ActivityHelper.startActivity(mContext,PwdSetActivity.class);
            }
        });

        sexInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        nameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(mContext,NameChangeActivity.class);
            }
        });

        emailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(mContext,EmailChangeActivity.class);
            }
        });

        phoneInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(mContext,PhoneChangeActivity.class);
            }
        });
    }

    private void connect(){
        new Thread(){
            public void run() {

                user=(User) Proxy.getWebData(StateCode.PersonalInfo,parameter);

                //错误产生一个是邮箱重复的话后段不会允许注册（手机号重复会允许，这应该也有问题）
                //另一个对proxy返回空值没有判断，如果返回-1就会返回空值

                Message msg = handler.obtainMessage();

                msg.obj = user;

                handler.handleMessage(msg); //通知handler我完事儿啦

            };
        }.start();
    }
}
