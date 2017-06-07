package xzh.com.materialdesign.adapter;

import android.app.Activity;
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
import xzh.com.materialdesign.model.Money_order;
import xzh.com.materialdesign.model.Order_state;
import xzh.com.materialdesign.model.Orders;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;
import xzh.com.materialdesign.ui.DetailsActivity;
import xzh.com.materialdesign.ui.ReceiveDetailActivity;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.utils.IntroUtils;

/**
 * Created by dz on 2017/5/2 on
 */
public class ReceiveOrderAdapter extends RecyclerView.Adapter<ReceiveOrderHolder> implements BaseAdapterInterface{

    private Context context;
    private List<LittleOrderBean> mList;
    JSONObject parameter;
    Orders orders;
    Order_state order_state;

    public ReceiveOrderAdapter(Context context) {
        this.context=context;
        mList = new ArrayList<>();
    }

    @Override
    public ReceiveOrderHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.receive_order, viewGroup, false);
        return new ReceiveOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiveOrderHolder orderHolder, int i) {
        final Integer integer=mList.get(i).getOrderId();
        initIntro(orderHolder,i);
        orderHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if(check())
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
                orders = (Orders) Proxy.getWebData(StateCode.OrderInfo,parameter);
                order_state = (Order_state) Proxy.getWebData(StateCode.OrderState,parameter);
                Intent intent = new Intent(context, ReceiveDetailActivity.class);
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

    private void initIntro(ReceiveOrderHolder orderHolder, int i) {
        orderHolder.loadInfo(mList.get(i));

        orderHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });
    }


}
