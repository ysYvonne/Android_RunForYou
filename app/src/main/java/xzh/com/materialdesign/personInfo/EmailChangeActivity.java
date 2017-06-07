package xzh.com.materialdesign.personInfo;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.ui.*;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by Flora on 2017/4/24.
 */

public class EmailChangeActivity extends AppCompatActivity {

    @InjectView(R.id.email_ok_button)
    Button okButton;
    @InjectView(R.id.email_cancel_button)
    Button cancelButton;
    @InjectView(R.id.school_spinner)
    Spinner spinner;
    @InjectView(R.id.email_text)
    TextView email_text;
    @InjectView(R.id.email_editText)
    EditText email_editText;

    private List<String> data;
    private List<String> emailData;
    private ArrayAdapter<String> adapter;

    private Context mContext;

    Bundle pInfoBundle;

    JSONObject parameter;

    String oldemail, school, newemail;
    private String schoolName;

    int Flag = -1;

    User user;
    int code = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v("ys","EamilChangeActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_change);

        mContext = EmailChangeActivity.this;
        ButterKnife.inject(this);

        pInfoBundle = getIntent().getExtras();
        school = pInfoBundle.getString("School");
        oldemail = pInfoBundle.getString("Email");

        init();
    }

    private void init(){

        spinner.setBackgroundColor(Color.GRAY);
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        initData();
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);

        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中    
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub    
                /* 将spinner 显示*/
                arg0.setVisibility(View.VISIBLE);
                schoolName = data.get(arg2);
                email_text.setText(emailData.get(arg2));

                Log.v("DZ","监听 arg2:"+arg2+"  监听 arg3:"+arg3);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub    
                arg0.setVisibility(View.VISIBLE);
            }
        });


        /*下拉菜单弹出的内容选项触屏事件处理*/
        spinner.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub    
                /**
                 *
                 */
                return false;
            }
        });    
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        spinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub    

            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeSchool();

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

    private void changeSchool(){

        if(checkE()){
            Log.v("ys", "start 更改学校 :" + schoolName);

            parameter=new JSONObject();
            try{

                parameter.put("type", "updateInfomation");
                parameter.put("userId",pInfoBundle.getString("userId"));
                parameter.put("column","school");
                parameter.put("value", schoolName);

            }catch (JSONException e) {
                e.printStackTrace();
            }

            connect();
        }


    }

    private void changeEmail(){

        if(checkE()){
            newemail = email_editText.getText().toString()+email_text.getText().toString();
            Log.v("ys", "start 更改邮箱: " + newemail);

            parameter=new JSONObject();
            try{

                parameter.put("type", "updateInfomation");
                parameter.put("userId",pInfoBundle.getString("userId"));
                parameter.put("column","email");
                parameter.put("value", newemail);

            }catch (JSONException e) {
                e.printStackTrace();
            }

            Flag = 1;

            connect();
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
                EmailChangeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectFinish(code);
                    }
                });

            }
        }).start();
    }

    private void connectFinish(int code){

            if(code == 1 && Flag == 1){
                new AlertDialog.Builder(mContext)

                        .setTitle("提示")

                        .setMessage("更改成功")

//                        .setPositiveButton("确定", null)

                        .show();

                    ActivityHelper.startActivity(mContext,PersonalInfoActivity.class, pInfoBundle);
                    finish();

        }else if(code == 1 && Flag != 1){

                changeEmail();

        }else{
                new AlertDialog.Builder(mContext)

                        .setTitle("提示")

                        .setMessage("更改失败")

                        .setPositiveButton("确定", null)

                        .show();
            }

    }


    private boolean checkE(){

        if(email_editText.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入邮箱名")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }

        newemail = email_editText.getText().toString()+email_text.getText().toString();

        Log.v("ys", "旧邮箱为： " + oldemail + "  新邮箱为： " + newemail);

        if(newemail.equals(oldemail)){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("邮箱名未更改，请更改")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }

    private void initData() {
        data = new ArrayList<String>();
        data.add("北京交通大学");
        data.add("清华大学");
        data.add("北京理工大学");
        data.add("北京大学");
        emailData = new ArrayList<String>();
        emailData.add("@bjtu.edu.cn");
        emailData.add("@tsinghua.edu.cn");
        emailData.add("@beili");
        emailData.add("@pku.edu.cn");


    }
}
