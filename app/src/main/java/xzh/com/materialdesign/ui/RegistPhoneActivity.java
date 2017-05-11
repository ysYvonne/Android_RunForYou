package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
    private EditText ev;
    Bundle mBundle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        send = (Button)findViewById(R.id.send_validationNum);
        next = (Button)findViewById(R.id.to_validname_button);

        //       ButterKnife.inject(this);
        //      EventBus.getDefault().register(this);
        mContext = RegistPhoneActivity.this;
        //绑定控件
        ev = (EditText) findViewById(R.id.RegisterPhoneAccount);

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
                String phone = ev.getText().toString();
                mBundle = new Bundle();
                mBundle.putString("phone",phone);
                ActivityHelper.startActivity(mContext,RegistValidNameActivity.class,mBundle);
            }
        });
    }
}
