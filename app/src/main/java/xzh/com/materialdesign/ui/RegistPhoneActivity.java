package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ypy.eventbus.EventBus;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.view.NavigationDrawerFragment;
import xzh.com.materialdesign.view.PullToLoadView;
import xzh.com.materialdesign.view.ThemeManager;

/**
 /**
 * Created by Towyer_pic on 2017/4/24.
 */

public class RegistPhoneActivity extends AppCompatActivity {

    private Context mContext;
    private Button send,next;
    private EditText phoneAccount,validationNum;
    Bundle mBundle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        send = (Button)findViewById(R.id.send_validationNum);
        next = (Button)findViewById(R.id.to_validname_button);
        next.setEnabled(false);

        //       ButterKnife.inject(this);
        //      EventBus.getDefault().register(this);
        mContext = RegistPhoneActivity.this;
        //绑定控件
        phoneAccount = (EditText) findViewById(R.id.RegisterPhoneAccount);
        validationNum=(EditText) findViewById(R.id.phone_validationNum_enter);

        init();
    }

    private void init() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.setEnabled(true);
                next.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myAccentColor));
                // ActivityHelper.startActivity(AccountLoginActivity.this,MainActivity.class);
                //发送验证码
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(check())
                String phone = phoneAccount.getText().toString();
                mBundle = new Bundle();
                mBundle.putString("phone",phone);
                ActivityHelper.startActivity(mContext,RegistValidNameActivity.class,mBundle);
            }
        });
    }

    private boolean check(){
        if(phoneAccount.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入手机号")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }else if(validationNum.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入验证码")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }
}
