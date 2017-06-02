package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.adapter.HomeAdapter;
import xzh.com.materialdesign.adapter.MyOrderAdapter;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.base.MyBaseActivity;
import xzh.com.materialdesign.model.LittleOrderBean;
import xzh.com.materialdesign.model.Money_order;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;


@SuppressLint("NewApi")//屏蔽android lint错误
public class MainActivity extends MyBaseActivity {

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
        super.setmAdapter(new HomeAdapter(this));
    }


    protected void loadList() {
        JSONObject parameter=new JSONObject();
        try {

            parameter.put("user_id", ControlUser.getUser(mContext).getUserId());
            parameter.put("type","loadOrders");
//            Log.v("dz","mainactiviy 发出user_id为"+ControlUser.getUser(mContext).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<LittleOrderBean> list;
        list= (List<LittleOrderBean>) Proxy.getWebData(StateCode.GetLittleOrder,parameter);
      for(LittleOrderBean lob:list){
          super.mAdapter.add(lob);
      }

    }
//
//    @Override
//    protected void createEventBus() {
//        EventBus.getDefault().register(this);
//    }


}
