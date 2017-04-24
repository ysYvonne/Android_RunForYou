package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Flora on 2017/4/24.
 */

public class PersonalInfoActivity extends AppCompatActivity {

    @InjectView(R.id.avatar_info)
    RelativeLayout avatarInfo;
    @InjectView(R.id.nickname_info)
    RelativeLayout nicknameInfo;
    @InjectView(R.id.pwd_info)
    RelativeLayout pwdInfo;
    @InjectView(R.id.sex_info)
    RelativeLayout sexInfo;
    @InjectView(R.id.name_info)
    RelativeLayout nameInfo;
    @InjectView(R.id.age_info)
    RelativeLayout ageInfo;
    @InjectView(R.id.email_info)
    RelativeLayout emailInfo;
    @InjectView(R.id.phone_info)
    RelativeLayout phoneInfo;

    private Context mContext;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_personal_info);
        mContext = PersonalInfoActivity.this;

        init();
    }

    private void init(){

        avatarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        nicknameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(PersonalInfoActivity.this,NicknameActivity.class);
            }
        });

        pwdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true){
                    ActivityHelper.startActivity(PersonalInfoActivity.this,PwdChangeActivity.class);
                }
                else
                    ActivityHelper.startActivity(PersonalInfoActivity.this,PwdSetActivity.class);
            }
        });

        sexInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        nameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(PersonalInfoActivity.this,NameChangeActivity.class);
            }
        });

        emailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(PersonalInfoActivity.this,EmailChangeActivity.class);
            }
        });

        phoneInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(PersonalInfoActivity.this,PhoneChangeActivity.class);
            }
        });
    }
}
