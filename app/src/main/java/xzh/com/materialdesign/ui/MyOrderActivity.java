package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import xzh.com.materialdesign.adapter.MyOrderAdapter;
import xzh.com.materialdesign.base.MyBaseActivity;
import xzh.com.materialdesign.model.Money_order;



@SuppressLint("NewApi")//屏蔽android lint错误
public class MyOrderActivity extends MyBaseActivity {
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
        super.setmAdapter(new MyOrderAdapter(this));
    }


    protected void loadList() {
        Money_order order =new Money_order();
        order.setDestination("我的订单配送内容");
        super.mAdapter.add(order);
    }
//
//    @Override
//    protected void createEventBus() {
//        EventBus.getDefault().register(this);
//    }


}
