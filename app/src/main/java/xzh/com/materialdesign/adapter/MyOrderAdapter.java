package xzh.com.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.LittleOrderBean;
import xzh.com.materialdesign.model.Order_state;
import xzh.com.materialdesign.model.Orders;
import xzh.com.materialdesign.proxy.Command;
import xzh.com.materialdesign.ui.DetailsActivity;
import xzh.com.materialdesign.ui.MyorderDetailAvtivity;

/**
 * Created by dz on 2017/5/2 on
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderHolder> implements BaseAdapterInterface{

    private Context context;
    private List<LittleOrderBean> mList;
    JSONObject parameter;
    Orders orders;
    Order_state order_state;

    public MyOrderAdapter(Context context) {
        this.context=context;
        mList = new ArrayList<>();
    }

    @Override
    public MyOrderHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.myorder_item, viewGroup, false);
        return new MyOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderHolder orderHolder, int i) {
        final Integer integer=mList.get(i).getOrderId();
        initIntro(orderHolder,i);
        orderHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(integer);
            }
        });

    }

    private void showDetail(int orderId){
        Log.v("tb","showDetail");

        //完成对用户密码的包装
        parameter=new JSONObject();
        try {
            parameter.put("type","OrderInfo");
            parameter.put("orderId", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        connect();

    }

    private void connect() {
        new Thread(){
            public void run() {
//                orders = (Orders) Proxy.getWebData(StateCode.OrderInfo,parameter);
                orders = (Orders)new Command().orderInfo(parameter);
                order_state = (Order_state)new Command().orderState(parameter);
//                        order_state = (Order_state) Proxy.getWebData(StateCode.OrderState,parameter);
                Intent intent = new Intent(context, MyorderDetailAvtivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderInfo", orders);
                bundle.putSerializable("orderState", order_state);
                intent.putExtras(bundle);
                context.startActivity(intent);
            };
        }.start();
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(Object s) {
        mList.add((LittleOrderBean) s);
        notifyDataSetChanged();
    }

    @Override
    public int getLastOrderId() {
        return mList.get(mList.size()-1).getOrderId();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    private void initIntro(MyOrderHolder orderHolder, int i) {
        orderHolder.loadInfo(mList.get(i));

        orderHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });
    }


}
