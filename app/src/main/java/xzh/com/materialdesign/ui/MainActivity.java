package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import xzh.com.materialdesign.adapter.HomeAdapter;
import xzh.com.materialdesign.adapter.MyOrderAdapter;
import xzh.com.materialdesign.base.MyBaseActivity;
import xzh.com.materialdesign.model.Money_order;



@SuppressLint("NewApi")//屏蔽android lint错误
public class MainActivity extends MyBaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
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
        Money_order order =new Money_order();
        order.setDestination("首页标题");
        super.mAdapter.add(order);
    }
//
//    @Override
//    protected void createEventBus() {
//        EventBus.getDefault().register(this);
//    }


}
