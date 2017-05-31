package xzh.com.materialdesign.personInfo;
import xzh.com.materialdesign.ui.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Flora on 2017/4/24.
 */

public class NicknameActivity extends AppCompatActivity {

    @InjectView(R.id.nickname_ok_button)
    Button okButton;
    @InjectView(R.id.nickname_cancel_button)
    Button cancelButton;

    private Context mContext;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("dz","NickName onCreate");
        setContentView(R.layout.activity_nickname_change);
        mContext = NicknameActivity.this;
        ButterKnife.inject(this);
        init();
    }

    private void init(){

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityHelper.startActivity(mContext,PersonalInfoActivity.class);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                ActivityHelper.startActivity(mContext,PersonalInfoActivity.class);
            }
        });
    }
}
