package xzh.com.materialdesign.personInfo;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.ui.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Flora on 2017/4/24.
 */

public class PwdChangeActivity extends AppCompatActivity {

    @InjectView(R.id.pwd_change_ok_button)
    Button okButton;
    @InjectView(R.id.pwd_change_cancel_button)
    Button cancelButton;

    EditText oldPwd, newPwd, newPwdConfirm;
    String oldPwds, newPwdS, newPwdConfirmS;

    Bundle pInfoBundle;

    JSONObject parameter;

    private Context mContext;

    User user;
    int code = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("dz","PwdChange onCreate");
        setContentView(R.layout.activity_pwd_change);

        mContext = PwdChangeActivity.this;
        ButterKnife.inject(this);

        pInfoBundle = getIntent().getExtras();

        oldPwd=(EditText)findViewById(R.id.old_pwd_editText) ;
        newPwd=(EditText)findViewById(R.id.new_pwd_editText) ;
        newPwdConfirm=(EditText)findViewById(R.id.check_pwd_editText) ;

        parameter=new JSONObject();

        oldPwds = pInfoBundle.getString("pwd");
        Log.v("ys", "旧密码是： " + oldPwds);

        okButton.setEnabled(false);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_400));

        newPwd.setEnabled(false);

        newPwdConfirm.setEnabled(false);

        init();
    }

    private void init(){

        oldPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!oldPwds.equals(oldPwd.getText().toString())){

                    String st = oldPwd.getText().toString();
//                    oldPwd.setText(st+ "        输入错误");
                    newPwd.setEnabled(false);
                    Log.v("ys", "与原密码不同，输入错误");

                }else{

//                    oldPwd.setText(oldPwds + "        输入正确");
                    newPwd.setEnabled(true);
                    Log.v("ys", "与原密码相同，输入正确");
                }

            }
        });

        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(newPwd.getText().length() >= 6){
                    newPwdConfirm.setEnabled(true);

                }else{
                    newPwd.setEnabled(false);
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        newPwdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {

                if(checkO() && checkP() && checkPC()){
                    okButton.setEnabled(true);
                    okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myPrimaryColor));
                }else{
                    okButton.setEnabled(false);
                    okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.selected_gray));
                }

            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changePwd();

//                ActivityHelper.startActivity(PwdChangeActivity.this,PersonalInfoActivity.class);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                ActivityHelper.startActivity(PwdChangeActivity.this,PersonalInfoActivity.class);
            }
        });
    }

    private void changePwd(){

        newPwdS = newPwd.getText().toString();
        newPwdConfirmS = newPwdConfirm.getText().toString();

        if(newPwdS.equals(newPwdConfirmS)){
            Log.v("ys", "start 更改密码");

            Log.v("ys", "更改密码为： "+ newPwd.getText().toString());
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

                user=(User) Proxy.getWebData(StateCode.PersonalInfo,parameter);
                code = user.getSex();

                // 在下面这个方法里可以做任何更新UI的操作
                PwdChangeActivity.this.runOnUiThread(new Runnable() {
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
            new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("更改成功")
                    //                   .setPositiveButton("确定", null)
                    .show();
            Intent mIntent = new Intent(StateCode.BROAD_PWD);
            mIntent.putExtra(StateCode.BROAD_PWD,newPwdS);

            //发送广播
            sendBroadcast(mIntent);
            finish();

        }else{
            new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("更改失败")
                    .setPositiveButton("确定", null)
                    .show();
        }
    }

    private boolean checkO(){
        if(oldPwd.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入旧密码")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
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
