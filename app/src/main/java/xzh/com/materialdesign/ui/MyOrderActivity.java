package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import xzh.com.materialdesign.adapter.MyOrderAdapter;
import xzh.com.materialdesign.api.ControlUser;
import xzh.com.materialdesign.base.MyBaseActivity;
import xzh.com.materialdesign.model.LittleOrderBean;
import xzh.com.materialdesign.model.Money_order;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;


@SuppressLint("NewApi")//屏蔽android lint错误
public class MyOrderActivity extends MyBaseActivity {
    JSONObject parameter;
    Boolean isLoadAll;
    List<LittleOrderBean> list;
    MyOrderAdapter mAdapter;



    protected void onCreate(Bundle savedInstanceState) {
        Log.v("dz","MyOrder onCreate");
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void setmTitle() {
        super.setmTitle("我的订单");
    }

    @Override
    protected void setmAdapter() {
        mAdapter=new MyOrderAdapter(this);
        super.setmAdapter(mAdapter);
    }

    @Override
    protected boolean loadMore(int orderId) {
        isLoadAll=false;
        parameter=new JSONObject();
        try {

            parameter.put("userId", ControlUser.getUser(mContext).getUserId());
            parameter.put("type","loadMoreOrders");
            parameter.put("order_id",String.valueOf(orderId));
//            Log.v("dz","mainactiviy 发出user_id为"+ControlUser.getUser(mContext).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 更新主线程ＵＩ
                list= (List<LittleOrderBean>) Proxy.getWebData(StateCode.GetLittleOrder,parameter);
                MyOrderActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(LittleOrderBean lob:list){
                            mAdapter.add(lob);
                        }

                    }
                });
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(list!=null && list.size()<StateCode.ListMax){
            Log.v("MyOrder.Thread","list size is "+list.size());
            isLoadAll =true;
        }
        return isLoadAll;
    }

    @Override
    protected boolean loadFirstTime() {
        parameter=new JSONObject();
        isLoadAll=false;
        try {

            parameter.put("userId", ControlUser.getUser(mContext).getUserId());
            parameter.put("type","loadMyOrders");
//            Log.v("dz","mainactiviy 发出user_id为"+ControlUser.getUser(mContext).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 更新主线程ＵＩ
                list= (List<LittleOrderBean>) Proxy.getWebData(StateCode.GetLittleOrder,parameter);
                MyOrderActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(LittleOrderBean lob:list){
                            mAdapter.add(lob);
                        }
                    }
                });
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(list!=null && list.size()<StateCode.ListMax){
            Log.v("MyOrder.Thread","list size is "+list.size());
            isLoadAll =true;
        }
        return isLoadAll;
    }


//    @Override
//    protected void createEventBus() {
//        EventBus.getDefault().register(this);
//    }


}
