package xzh.com.materialdesign.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;
import xzh.com.materialdesign.model.ModifyPerson;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.view.TitleBar;

/**
 * Created by botan on 2017/4/22.
 */

public class ModifyActivity extends BaseActivity {

    @InjectView(R.id.modify_custom_name)
    EditText modify_name;
    @InjectView(R.id.modify_custom_phone)
    EditText modify_phone;
    @InjectView(R.id.modify_enter_validationNum)
    EditText validationNum;
    @InjectView(R.id.modify_confirm)
    Button modify_confirm;
    public String name,phone;
    ModifyPerson modify;
    Context mContext;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v("dz","Modify onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mContext=this;

        setContentView(R.layout.modify_custom);
        ButterKnife.inject(this);

        modify_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处需要添加修改OrderActivity的方法
                name = modify_name.getText().toString();
                phone = modify_phone.getText().toString();
                modify = new ModifyPerson(name,phone);
                Intent mIntent = new Intent(StateCode.BROAD_CHANGENAME);
                mIntent.putExtra(StateCode.BROAD_CHANGENAME,name);
                Intent sIntent = new Intent(StateCode.BROAD_CHANGEPHONE);
                sIntent.putExtra(StateCode.BROAD_CHANGEPHONE,phone);


                //发送广播
                mContext.sendBroadcast(mIntent);
                mContext.sendBroadcast(sIntent);
                ActivityHelper.startActivity(ModifyActivity.this,OrderActivity.class);
            }
        });
    }

}
