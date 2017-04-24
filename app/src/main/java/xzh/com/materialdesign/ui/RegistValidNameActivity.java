package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Towyer_pic on 2017/4/24.
 */

public class RegistValidNameActivity extends AppCompatActivity {

    private Context mContext;
    private Button send,next;

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
                //ActivityHelper.startActivity(RegistValidNameActivity.this,MainActivity.class);
                //进入主界面啦~
            }
        });
    }
}

