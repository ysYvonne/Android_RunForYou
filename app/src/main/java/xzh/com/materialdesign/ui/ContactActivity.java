package xzh.com.materialdesign.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.proxy.Command;

public class ContactActivity extends AppCompatActivity {

    final int INSUCCESS=0;
    final int SUCCESS=1;
    private Context mContext;
    private EditText contactText;
    private Button submit;
    private Button cancel;

    private ProgressDialog dialog;
    JSONObject parameter;

    boolean success;

    Bundle mBundle;


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

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

//                success = (boolean)Proxy.getWebData(StateCode.ContactUs,parameter);
                success=(boolean)new Command().contactUs(parameter);

                // 在下面这个方法里可以做任何更新UI的操作
                ContactActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            connectFinish(success);
                    }
                });

            }
        }).start();
    }

    private void connectFinish(boolean s) {

        if(s){
            new AlertDialog.Builder(mContext)

                    .setTitle("提示")

                    .setMessage("意见提交成功，谢谢惠顾。")

                    .setPositiveButton("确定", null)

                    .show();

            finish();

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
