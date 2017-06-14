package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.proxy.Command;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.model.*;

/**
 * Created by Towyer_pic on 2017/4/24.
 */

public class RegistValidNameActivity extends AppCompatActivity {

    private Context mContext;
    private Button send,next;
    private EditText nameText,emailText, validNum;
    private Spinner spinner;
    private List<String> data;
    private List<String> emailData;
    private TextView emailTextList;

    private String schoolName;
    Bundle mBundle;

    private ProgressDialog dialog;
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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_validname);

        send = (Button)findViewById(R.id.send_validationNum);
        next = (Button)findViewById(R.id.signin_button);
               ButterKnife.inject(this);
        //      EventBus.getDefault().register(this);
        mContext = RegistValidNameActivity.this;
        mBundle = getIntent().getExtras();

        nameText = (EditText)findViewById(R.id.RegisterRealname);
        emailText = (EditText)findViewById(R.id.RegisterEmail);
        validNum = (EditText)findViewById(R.id.validationNum_email);

        spinner = (Spinner)findViewById(R.id.RegisterSchool);

        emailTextList=(TextView)findViewById(R.id.email_suffix) ;

        init();
    }

    private void init() {

        spinner.setBackgroundColor(Color.parseColor("#B452CD"));
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        initData();
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);

        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将spinner 显示*/
                arg0.setVisibility(View.VISIBLE);
                schoolName = data.get(arg2);
                emailTextList.setText(emailData.get(arg2));
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                if(checkN() && checkE()){
                    next.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myPrimaryColor));
                    //发送验证码
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处添加判断验证函数
                if(checkV()){
                    register();
                }


            }
        });
    }

    private void register(){
        Log.v("ys", "start registing");
        dialog = new ProgressDialog(mContext);

        parameter=new JSONObject();
        try{
            //传入数据
            String name = nameText.getText().toString();
            String email = emailText.getText().toString()+emailTextList.getText().toString();
            String phone = mBundle.getString("phone");

            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPhoneNum(phone);
            user.setSchool(schoolName);

            parameter.put("type", "register");
            parameter.put("email", user.getEmail());
            parameter.put("name", user.getName());
            parameter.put("phoneNum", user.getPhoneNum());
            parameter.put("school", user.getSchool());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        //弹出processdialog,必须要使用线程，不然无法显示
        dialog.setTitle("提示");
        dialog.setMessage("正在登录，请稍后...");
        dialog.setCancelable(false);
        dialog.show();

        connect();
    }

    private void connect(){
        new Thread(){
            public void run() {

//                user=(User) Proxy.getWebData(StateCode.Register,parameter);
                user=(User)new Command().register(parameter);

                //错误产生一个是邮箱重复的话后段不会允许注册（手机号重复会允许，这应该也有问题）
                //另一个对proxy返回空值没有判断，如果返回-1就会返回空值

                Message msg = handler.obtainMessage();

                msg.obj = user;

                handler.handleMessage(msg); //通知handler我完事儿啦

            };
        }.start();
    }

    //连接完网络请求后需要做的事情
    private void connectFinish() {

        dialog.dismiss();
        if(user==null){
            new AlertDialog.Builder(mContext)

                    .setTitle("注册失败")

                    .setMessage(StateCode.UserIdNull)

                    .setPositiveButton("确定", null)

                    .show();
        }else{

            //写入sharedPreferences
            if (user.getUserId() < 0) {

                new AlertDialog.Builder(mContext)

                        .setTitle("警告")

                        .setMessage(StateCode.UserIdNull)

                        .setPositiveButton("确定", null)

                        .show();
            } else {
                ControlUser.addUser(user,mContext);
                finish();
                ActivityHelper.startActivity(mContext, MainActivity.class);

            }
        }

    }

    private boolean checkN(){
        if(nameText.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入姓名")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }

    private boolean checkE(){
        if(emailText.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入邮箱名")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }

    private boolean checkV(){
        if(validNum.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入验证码")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }


    private void initData() {
        data = new ArrayList<String>();
        data.add("北京交通大学");
        data.add("清华大学");
        data.add("北京理工大学");
        data.add("北京大学");
        emailData = new ArrayList<String>();
        emailData.add("@bjtu.edu.cn");
        emailData.add("@tsinghua.edu.cn");
        emailData.add("@beili");
        emailData.add("@pku.edu.cn");

    }
}

