package xzh.com.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.Money_order;
import xzh.com.materialdesign.ui.DetailsActivity;
import xzh.com.materialdesign.utils.IntroUtils;

/**
 * Created by dz on 2017/5/2 on
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderHolder> implements BaseAdapterInterface{

    private Context context;
    private List<Object> mList;

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

        initIntro(orderHolder,i);
        orderHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(Object s) {
        mList.add(s);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    private void initIntro(MyOrderHolder orderHolder, int i) {
        Money_order order = (Money_order) mList.get(i);

        orderHolder.myorder_text.setText(order.getDestination());
        orderHolder.myorder_date_text.setText("2017/5/14");

        orderHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });
    }


}
