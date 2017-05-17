package xzh.com.materialdesign.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Flora on 2017/4/24.
 */

public class PwdChangeActivity extends AppCompatActivity {

    @InjectView(R.id.pwd_change_ok_button)
    Button okButton;
    @InjectView(R.id.pwd_change_cancel_button)
    Button cancelButton;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pwd_change);
        mContext = PwdChangeActivity.this;
        ButterKnife.inject(this);
        init();
    }

    private void init(){

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityHelper.startActivity(PwdChangeActivity.this,PersonalInfoActivity.class);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                ActivityHelper.startActivity(PwdChangeActivity.this,PersonalInfoActivity.class);
            }
        });
    }
}
