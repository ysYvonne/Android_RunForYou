package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.ControlUser;
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

    TextView my_nickname, my_pwd,my_sex, my_name, my_age, my_school, my_email, my_phone;

    private Context mContext;

    Bundle mBundle,pInfoBundle;

    JSONObject parameter;
    User user;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Looper.prepare();
            connectFinish();
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

        pInfoBundle = new Bundle();

        ButterKnife.inject(this);

        my_nickname=(TextView)findViewById(R.id.my_nickname);
        my_pwd=(TextView)findViewById(R.id.my_pwd);
        my_sex=(TextView)findViewById(R.id.my_sex);
        my_name=(TextView)findViewById(R.id.my_name);
        my_age=(TextView)findViewById(R.id.my_age);
        my_school=(TextView)findViewById(R.id.my_school);
        my_email=(TextView)findViewById(R.id.my_email);
        my_phone=(TextView)findViewById(R.id.my_phone);

        init();
    }

    private void init(){
        Log.v("ys", "Start Personal Information");
        parameter=new JSONObject();
        try{

            parameter.put("type","getUser");
            parameter.put("userId",mBundle.getString("userId"));

        }catch (JSONException e) {
            e.printStackTrace();
        }

        connect();

        pInfoBundle.putString("userId", mBundle.getString("userId"));

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
                pInfoBundle.putString("nickName",user.getNickname());
                ActivityHelper.startActivity(mContext,NicknameActivity.class, pInfoBundle);
            }
        });

        pwdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getPassword().equals("未设置")){
                    pInfoBundle.putString("pwd",user.getPassword());
                    ActivityHelper.startActivity(mContext,PwdSetActivity.class, pInfoBundle);
                }
                else{
                    pInfoBundle.putString("pwd",user.getPassword());
                    ActivityHelper.startActivity(mContext,PwdChangeActivity.class, pInfoBundle);
                }
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
                pInfoBundle.putString("Name",user.getName());
                ActivityHelper.startActivity(mContext,NameChangeActivity.class, pInfoBundle);
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
                pInfoBundle.putString("Phone",user.getPhoneNum());
                ActivityHelper.startActivity(mContext,PhoneChangeActivity.class, pInfoBundle);
            }
        });
    }

    private void connect(){
        new Thread(){
            public void run() {

                user=(User) Proxy.getWebData(StateCode.PersonalInfo,parameter);

                Message msg = handler.obtainMessage();

                msg.obj = user;

                handler.handleMessage(msg); //通知handler我完事儿啦

            };
        }.start();
    }

    private void connectFinish(){

        if (user.getUserId() < 0) {

            new AlertDialog.Builder(mContext)

                    .setTitle("警告")

                    .setMessage(StateCode.UserIdNull)

                    .setPositiveButton("确定", null)

                    .show();
        } else {

            my_nickname.setText(my_nickname.getText() + "   " + user.getNickname());
            my_pwd.setText(my_pwd.getText() + "   ******");

            switch(user.getSex()) {
                case 2:
                    my_sex.setText(my_sex.getText() + "  ♂");
                    break;
                case 1:
                    my_sex.setText(my_sex.getText() + "  ♀");
                    break;
                default:
                    my_sex.setText(my_sex.getText()+ "  未设置");
                    break;
            }

            my_name.setText(my_name.getText() + "   " + user.getName());
            my_age.setText(my_age.getText() + "   " + user.getAge());
            my_school.setText(my_school.getText() + "   " + user.getSchool());
            my_email.setText(my_email.getText() + "   " + user.getEmail());
            my_phone.setText(my_phone.getText() + "   " + user.getPhoneNum());
        }

    }
}
