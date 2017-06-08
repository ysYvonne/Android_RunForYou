package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.renderscript.Sampler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    final int VALID = 1;
    @InjectView(R.id.nickname_info)
    RelativeLayout nicknameInfo;
    @InjectView(R.id.pwd_info)
    RelativeLayout pwdInfo;
    @InjectView(R.id.sex_info)
    RelativeLayout sexInfo;
    @InjectView(R.id.my_sex_spinner)
    Spinner sexspinner;
    @InjectView(R.id.name_info)
    RelativeLayout nameInfo;
    @InjectView(R.id.age_info)
    RelativeLayout ageInfo;
    @InjectView(R.id.email_info)
    RelativeLayout emailInfo;
    @InjectView(R.id.phone_info)
    RelativeLayout phoneInfo;

    TextView my_nickname, my_pwd,my_sex, my_name, my_age, my_school, my_email, my_phone;

    private List<String> sexdata;
    private ArrayAdapter<String> adapter;

    private Context mContext;

    Bundle mBundle,pInfoBundle;

    JSONObject parameter;

    User user;
    int code = -1;

    int newSex;
    String newAge;

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
        registerBroadcastReceiver();
    }

    private void buttonEvent(){

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

//                finish();
                ActivityHelper.startActivity(mContext,NicknameActivity.class, pInfoBundle);

            }
        });

        pwdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pInfoBundle.putString("pwd",user.getPassword());

                if(user.getPassword().equals("未设置")){

//                    finish();
                    ActivityHelper.startActivity(mContext,PwdSetActivity.class, pInfoBundle);
                }
                else{

//                    finish();
                    ActivityHelper.startActivity(mContext,PwdChangeActivity.class, pInfoBundle);
                }
            }
        });

        initData();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sexdata);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexspinner.setAdapter(adapter);

        int i = 0;
        Log.v("ys", "当前性别为： "+ user.getSex());
        if(user.getSex() == -1){
            sexspinner.setSelection(2, true);
        }else{
            i = user.getSex();
            i--;
            sexspinner.setSelection(i, true);
        }
        sexspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg0.setVisibility(View.VISIBLE);
                newSex = arg2+1;
                changeSex();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        nameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pInfoBundle.putString("Name",user.getName());

 //               finish();
                ActivityHelper.startActivity(mContext,NameChangeActivity.class, pInfoBundle);
            }
        });

        ageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAge();
            }
        });

        emailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pInfoBundle.putString("School",user.getSchool());
                pInfoBundle.putString("Email",user.getEmail());

//                finish();
                ActivityHelper.startActivity(mContext,EmailChangeActivity.class, pInfoBundle);
            }
        });

        phoneInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pInfoBundle.putString("Phone",user.getPhoneNum());

//                finish();
                ActivityHelper.startActivity(mContext,PhoneChangeActivity.class, pInfoBundle);
            }
        });
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
    }

    private void changeSex(){
        user.setSex(newSex);

        Log.v("ys", "start 更改性别 :" + newSex);

        parameter=new JSONObject();
        try{

            parameter.put("type", "updateInfomation");
            parameter.put("userId",pInfoBundle.getString("userId"));
            parameter.put("column","sex");
            parameter.put("value", newSex);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        connect();

    }

    private void changeAge(){

        Log.v("ys", "start 更改年龄");


    }


    private void connect(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                user=(User) Proxy.getWebData(StateCode.PersonalInfo,parameter);

                // 在下面这个方法里可以做任何更新UI的操作
                PersonalInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(parameter.getString("type").equals("getUser")){
                                connectFinish();
                            }
                            else if(parameter.getString("type").equals("updateInfomation")){
                                code = user.getSex();
                                connectFinish(code);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
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

            my_name.setText(my_name.getText() + "   " + user.getName());
            my_age.setText(my_age.getText() + "   " + user.getAge());
            my_school.setText(my_school.getText() + "   " + user.getSchool());
            my_email.setText(my_email.getText() + "   " + user.getEmail());

            pInfoBundle = getIntent().getExtras();
//            my_phone.setText(my_phone.getText() + "   " + pInfoBundle.getString("Phone"));
            my_phone.setText(my_phone.getText() + "   " + user.getPhoneNum());

            buttonEvent();
        }

    }

    private void connectFinish(int code){

        if(code == 1){
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("更改成功")

                    .setPositiveButton("确定", null)

                    .show();

        }else{
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("更改失败")

                    .setPositiveButton("确定", null)

                    .show();
        }
    }

    private void initData() {
        sexdata = new ArrayList<String>();
        sexdata.add("男");   //男
        sexdata.add("女");   //女
        sexdata.add("未设置");
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {


            String action = intent.getAction();
            switch (action){
                case StateCode.BROAD_NICKNAME:
                    String nickName=intent.getStringExtra(StateCode.BROAD_NICKNAME);
                    Log.v("dz","接收到的nickname为："+nickName);
                    my_nickname.setText(nickName);
                    break;

                case StateCode.BROAD_EMAIL:
                    String email=intent.getStringExtra(StateCode.BROAD_EMAIL);
                    Log.v("dz","email："+email);
                    my_email.setText(email);
                    break;

                case StateCode.BROAD_NAME:
                    String name=intent.getStringExtra(StateCode.BROAD_NAME);
                    Log.v("dz","name："+name);
                    my_name.setText("真实姓名:"+name);
                    break;

                case StateCode.BROAD_PHONE:
                    String phone=intent.getStringExtra(StateCode.BROAD_PHONE);
                    Log.v("dz","phone："+phone);
                    my_phone.setText(phone);
                    break;

                case StateCode.BROAD_PWD:
                    String pwd=intent.getStringExtra(StateCode.BROAD_PWD);
                    Log.v("dz","pwd："+pwd);
                    my_pwd.setText(pwd);
                    break;

            }

        }

    };

    public void registerBroadcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(StateCode.BROAD_EMAIL);
        myIntentFilter.addAction(StateCode.BROAD_NAME);
        myIntentFilter.addAction(StateCode.BROAD_NICKNAME);
        myIntentFilter.addAction(StateCode.BROAD_PHONE);
        myIntentFilter.addAction(StateCode.BROAD_PWD);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
}
