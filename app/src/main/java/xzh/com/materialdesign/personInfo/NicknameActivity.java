package xzh.com.materialdesign.personInfo;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Command;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.ui.*;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
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

public class NicknameActivity extends AppCompatActivity {

    @InjectView(R.id.nickname_ok_button)
    Button okButton;
    @InjectView(R.id.nickname_cancel_button)
    Button cancelButton;

    Bundle pInfoBundle;

    private Context mContext;

    JSONObject parameter;

    String nickName;
    EditText nickNameChange;

    User user;
    int code = -1;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ys","NickName onCreate");
        setContentView(R.layout.activity_nickname_change);

        mContext = NicknameActivity.this;
        ButterKnife.inject(this);

        pInfoBundle = getIntent().getExtras();
        nickName = pInfoBundle.getString("nickName");

        nickNameChange = (EditText)findViewById(R.id.nickname_editText);

        init();
    }

    private void init(){

        nickNameChange.setText(nickName);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ys", "start changing nickname");

                nickName = nickNameChange.getText().toString();
                pInfoBundle.putString("nickName", nickName);

                parameter=new JSONObject();
                try{

                    parameter.put("type","updateInfomation");
                    parameter.put("userId",pInfoBundle.getString("userId"));
                    parameter.put("column","nickname");
                    parameter.put("value", nickName);

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                connect();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pInfoBundle.putString("nickName", nickName);
                finish();
                ActivityHelper.startActivity(mContext,PersonalInfoActivity.class, pInfoBundle);
            }
        });
    }

    private void connect(){
        new Thread(){
            public void run() {

//                user=(User)Proxy.getWebData(StateCode.PersonalInfo,parameter);
                user=(User)new Command().personalInfo(parameter);
                code = user.getSex();

                // 在下面这个方法里可以做任何更新UI的操作
                NicknameActivity.this.runOnUiThread(new Runnable() {
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
// //                   .setPositiveButton("确定", null)
//
//                    .show();

            ControlUser.ChangeUser(StateCode.BROAD_NICKNAME,nickName,mContext);
//            ActivityHelper.startActivity(mContext,PersonalInfoActivity.class, pInfoBundle);
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
