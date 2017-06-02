package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


import xzh.com.materialdesign.adapter.HomeAdapter;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.base.MyBaseActivity;
import xzh.com.materialdesign.model.LittleOrderBean;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;


@SuppressLint("NewApi")//屏蔽android lint错误
public class MainActivity extends MyBaseActivity {
    List<LittleOrderBean> list;
    HomeAdapter mAdapter;
    JSONObject parameter;
//
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            for(LittleOrderBean lob:list){
//                mAdapter.add(lob);
//            }
//        }
//    };


    protected void onCreate(Bundle savedInstanceState) {
        Log.v("dz","Main onCreate");
        super.onCreate(savedInstanceState);



    }
    //设置标题
    @Override
    protected void setmTitle() {
        super.setmTitle("首页");
    }

    //设置使用的构适配器，此处适配器在父类中用的baseAdapterInterface
    @Override
    protected void setmAdapter() {
        mAdapter=new HomeAdapter(this);
        super.setmAdapter(mAdapter);
    }


    protected void loadList() {
      parameter=new JSONObject();
        try {

            parameter.put("user_id", ControlUser.getUser(mContext).getUserId());
            parameter.put("type","loadOrders");
//            Log.v("dz","mainactiviy 发出user_id为"+ControlUser.getUser(mContext).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
            // TODO Auto-generated method stub
             // 更新主线程ＵＩ
                list= (List<LittleOrderBean>) Proxy.getWebData(StateCode.GetLittleOrder,parameter);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(LittleOrderBean lob:list){
                            mAdapter.add(lob);
                        }
                    }
                });
            }
        }).start();
//        new Thread(){
//            public void run() {
//                list= (List<LittleOrderBean>) Proxy.getWebData(StateCode.GetLittleOrder,parameter);
//                Message msg = handler.obtainMessage();
//
//                msg.obj = "";
////              handler.sendEmptyMessage(0);
//                handler.handleMessage(msg); //通知handler我完事儿啦,实际并没有接收msg只是一个信号，在属性user里完成了对user 的操作
//
//            };
//        }.start();



    }
//
//    @Override
//    protected void createEventBus() {
//        EventBus.getDefault().register(this);
//    }


}
