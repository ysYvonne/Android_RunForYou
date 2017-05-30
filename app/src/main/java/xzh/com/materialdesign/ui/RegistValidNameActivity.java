package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.model.*;

/**
 * Created by Towyer_pic on 2017/4/24.
 */

public class RegistValidNameActivity extends AppCompatActivity {

    private Context mContext;
    private Button send,next;
    private EditText nameText,emailText;
    private Spinner spinner;
    private List<String> data;
    private List<String> emailData;
    private TextView emailTextList;
    Bundle mBundle;


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
        spinner = (Spinner)findViewById(R.id.RegisterSchool);
        emailTextList=(TextView)findViewById(R.id.email_suffix) ;

        init();
    }

    private void init() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                //发送验证码
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处添加判断验证函数

                //传入数据
                String name = nameText.getText().toString();
                String email = emailText.getText().toString() + "@bjtu.edu.cn";
                String phone = mBundle.getString("phone");
                // String school = schoolText.getSelectedItem().toString();

                User user = new User();
                user.setEmail(email);
                //  user.setSchool(school);
                user.setName(name);
                user.setPhoneNum(mBundle.getString("phone"));

                ActivityHelper.startActivity(RegistValidNameActivity.this, AccountLoginActivity.class);
                //进入主界面啦~
            }
        });

        spinner.setBackgroundColor(Color.GRAY);
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
                emailTextList.setText(emailData.get(arg2));

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });
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

