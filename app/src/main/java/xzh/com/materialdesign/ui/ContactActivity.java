package xzh.com.materialdesign.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.utils.ActivityHelper;

public class ContactActivity extends AppCompatActivity {

    final int INSUCCESS=0;
    final int SUCCESS=1;
    private Context mContext;
    private EditText contactText;
    private Button submit;
    private Button cancel;

    private ProgressDialog dialog;
    JSONObject parameter;
    User user;

    Bundle mBundle;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Looper.prepare();
            connectFinish(msg.what);
            Looper.loop();
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        Log.v("dz","ContactActivity onCreate");
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        setContentView(R.layout.activity_contact_us);

        mContext = ContactActivity.this;

        mBundle = getIntent().getExtras();
        Log.v("ys", "传入id: "+ mBundle.getString("userId"));

        contactText = (EditText)findViewById(R.id.contact_editText);
        submit = (Button)findViewById(R.id.contact_submit);
        cancel = (Button)findViewById(R.id.contact_cancel);

        init();
    }

    private void init(){

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check()){
                    submitContact();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void submitContact(){
        Log.v("ys", "start submitting");

        parameter=new JSONObject();
        try{

            parameter.put("type", "placeOpinion");
            parameter.put("userId",mBundle.getString("userId"));
            parameter.put("content",contactText.getText().toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        connect();

    }

    private void connect(){
        new Thread(){
            public void run() {

                boolean success = (boolean)Proxy.getWebData(StateCode.ContactUs,parameter);
                Message msg = handler.obtainMessage();
                if(success){
                    Log.v("ys", "联系上传成功");
                    msg.what = SUCCESS;
                }else{
                    Log.v("ys", "联系上传失败");
                    msg.what = INSUCCESS;
                }
                handler.handleMessage(msg); //通知handler我完事儿啦

            };
        }.start();
    }

    private void connectFinish(final int s) {

        if(s == 1){
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("意见提交成功，谢谢惠顾。")

                    .setPositiveButton("确定", null)

                    .show();
        }else{
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("你问我我也很无奈啊。")

                    .setPositiveButton("确定", null)

                    .show();
        }


    }


    private boolean check(){

        if(contactText.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)

                    .setTitle("提示")

                    .setMessage("请输入姓名")

                    .setPositiveButton("确定", null)

                    .show();
            return false;
        }
        return true;
    }
}
