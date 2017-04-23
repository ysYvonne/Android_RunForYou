package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ypy.eventbus.EventBus;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;

/**
 * Created by yisheng on 2017/4/23.
 */

public class LoginActivity extends AppCompatActivity {

    private Context mContext;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_login);
 //       ButterKnife.inject(this);
  //      EventBus.getDefault().register(this);
        mContext = LoginActivity.this;

        init();
    }

    private void init() {
    }
}
