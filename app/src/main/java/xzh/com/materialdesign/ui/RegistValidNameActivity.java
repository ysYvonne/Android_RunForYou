package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private Spinner schoolText;
    Bundle mBundle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_validname);

        send = (Button)findViewById(R.id.send_validationNum);
        next = (Button)findViewById(R.id.signin_button);
        //       ButterKnife.inject(this);
        //      EventBus.getDefault().register(this);
        mContext = RegistValidNameActivity.this;
        mBundle = getIntent().getExtras();

        nameText = (EditText)findViewById(R.id.RegisterRealname);
        emailText = (EditText)findViewById(R.id.RegisterEmail);
        schoolText = (Spinner)findViewById(R.id.RegisterSchool);

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
                String phone  =mBundle.getString("phone");
                // String school = schoolText.getSelectedItem().toString();

                User user = new User();
                user.setEmail(email);
                //  user.setSchool(school);
                user.setName(name);
                user.setPhoneNum(mBundle.getString("phone"));

                ActivityHelper.startActivity(RegistValidNameActivity.this,AccountLoginActivity.class);
                //进入主界面啦~
            }
        });
    }
}

