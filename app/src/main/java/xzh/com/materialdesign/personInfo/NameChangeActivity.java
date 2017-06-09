package xzh.com.materialdesign.personInfo;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.ui.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Flora on 2017/4/24.
 */

public class NameChangeActivity extends AppCompatActivity {

    @InjectView(R.id.name_ok_button)
    Button okButton;
    @InjectView(R.id.name_cancel_button)
    Button cancelButton;

    Bundle pInfoBundle;

    private Context mContext;

    JSONObject parameter;

    String Name;
    EditText NameChange;

    User user;
    int code = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ys","NameCHange onCreate");
        setContentView(R.layout.activity_name_change);

        mContext = NameChangeActivity.this;
        ButterKnife.inject(this);

        pInfoBundle = getIntent().getExtras();
        Name = pInfoBundle.getString("Name");

        NameChange = (EditText)findViewById(R.id.name_editText);

        init();
    }

    private void init(){

        NameChange.setText(Name);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("ys", "start changing name");

                Name = NameChange.getText().toString();
                pInfoBundle.putString("Name", Name);

                parameter=new JSONObject();
                try{

                    parameter.put("type","updateInfomation");
                    parameter.put("userId",pInfoBundle.getString("userId"));
                    parameter.put("column","name");
                    parameter.put("value", Name);

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                connect();

                ActivityHelper.startActivity(mContext,PersonalInfoActivity.class);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pInfoBundle.putString("Name", Name);
                finish();
                ActivityHelper.startActivity(mContext,PersonalInfoActivity.class, pInfoBundle);
            }
        });
    }

    private void connect(){
        new Thread(){
            public void run() {

                user=(User) Proxy.getWebData(StateCode.PersonalInfo,parameter);
                code = user.getSex();

                // 在下面这个方法里可以做任何更新UI的操作
                NameChangeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectFinish(code);
                    }
                });


            };
        }.start();
    }

    private void connectFinish(int code){

        if(code == 1){
//            new AlertDialog.Builder(mContext)
//
//                    .setTitle("提示")
//
//                    .setMessage("更改成功")
//
//                    //.setPositiveButton("确定", null)
//
//                    .show();

            ControlUser.ChangeUser(StateCode.BROAD_NAME,Name,mContext);
            finish();

        }else{
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("更改失败")

                    .setPositiveButton("确定", null)

                    .show();
        }

    }
}
