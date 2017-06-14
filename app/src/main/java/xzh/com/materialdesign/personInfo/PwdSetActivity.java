package xzh.com.materialdesign.personInfo;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Command;
import xzh.com.materialdesign.proxy.StateCode;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;

/**
 * Created by Flora on 2017/4/24.
 */

public class PwdSetActivity extends AppCompatActivity {

    @InjectView(R.id.pwd_set_ok_button)
    Button okButton;
    @InjectView(R.id.pwd_set_cancel_button)
    Button cancelButton;

    EditText newPwd, newPwdConfirm;
    String newPwdS, newPwdConfirmS;

    Bundle pInfoBundle;

    JSONObject parameter;

    private Context mContext;

    User user;
    int code = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("dz","PwdSet onCreate");
        setContentView(R.layout.activity_pwd_set);

        mContext = PwdSetActivity.this;
        ButterKnife.inject(this);

        pInfoBundle = getIntent().getExtras();

        newPwd=(EditText)findViewById(R.id.new_pwd_editText) ;
        newPwdConfirm=(EditText)findViewById(R.id.check_pwd_editText) ;

        parameter=new JSONObject();

        okButton.setEnabled(false);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.selected_gray));
        newPwdConfirm.setEnabled(false);

        init();
    }

    private void init(){

        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(newPwd.getText().length() >= 6){

                    newPwdConfirm.setEnabled(true);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newPwdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(checkP() && checkPC()){


                    okButton.setEnabled(true);
                    okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myPrimaryColor));
                }

            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SetPwd();

//                ActivityHelper.startActivity(PwdSetActivity.this,PersonalInfoActivity.class);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                ActivityHelper.startActivity(PwdSetActivity.this,PersonalInfoActivity.class);
            }
        });
    }

    private void SetPwd(){

        newPwdS = newPwd.getText().toString();
        newPwdConfirmS = newPwdConfirm.getText().toString();

        if(newPwdS.equals(newPwdConfirmS)){
            Log.v("ys", "start 设定密码");

            Log.v("ys", "设定密码为： "+ newPwd);
            pInfoBundle.putString("SetPwd", newPwdS);

            parameter=new JSONObject();
            try{

                parameter.put("type","updateInfomation");
                parameter.put("userId",pInfoBundle.getString("userId"));
                parameter.put("column","password");
                parameter.put("value", newPwdS);

            }catch (JSONException e) {
                e.printStackTrace();
            }

            connect();

        }else{
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("两次密码不相同，请重新输入")

                    .setPositiveButton("确定", null)

                    .show();
        }
    }

    private void connect(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

//                user=(User) Proxy.getWebData(StateCode.PersonalInfo,parameter);
                user=(User)new Command().personalInfo(parameter);
                code = user.getSex();

                // 在下面这个方法里可以做任何更新UI的操作
                PwdSetActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectFinish(code);
                    }
                });

            }
        }).start();
    }

    private void connectFinish(int code){

        if(code == 1){
//            new AlertDialog.Builder(mContext)
//                    .setTitle("提示")
//                    .setMessage("设定成功")
//                    //                   .setPositiveButton("确定", null)
//                    .show();

//            Intent mIntent = new Intent(StateCode.BROAD_PWD);
//            mIntent.putExtra(StateCode.BROAD_PWD,newPwdS);
//
//            //发送广播
//            sendBroadcast(mIntent);
            ControlUser.ChangeUser(StateCode.BROAD_PWD,newPwdS,mContext);
            finish();

        }else{
            new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("设定失败")
                    .setPositiveButton("确定", null)
                    .show();
        }
    }

    private boolean checkP(){
        if(newPwd.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入新密码")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }

    private boolean checkPC(){
        if(newPwdConfirm.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入确认密码")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }


}
