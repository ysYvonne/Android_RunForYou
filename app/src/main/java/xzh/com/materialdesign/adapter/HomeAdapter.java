package xzh.com.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.Money_order;
import xzh.com.materialdesign.ui.DetailsActivity;
import xzh.com.materialdesign.utils.IntroUtils;
import xzh.com.materialdesign.view.CircleImageView;

/**
 * Created by xiangzhihong on 2016/3/2 on 15:41.
 */
public class HomeAdapter extends RecyclerView.Adapter<CellHolder> implements BaseAdapterInterface{

   private Context context;
    private List<Object> mList;

    public HomeAdapter(Context context) {
       this.context=context;
        mList = new ArrayList<>();
    }

//    在任何ViewHolder被实例化的时候，OnCreateViewHolder将会被触发：
    @Override
    public CellHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.home_item, viewGroup, false);
        return new CellHolder(view);
    }

    //将list中的信息与每一个cellholder绑定在一起，cellholder就是一个view的封装，三个构造器都需要
    //不同的cellholder
    @Override
    public void onBindViewHolder(CellHolder cellHolder, int i) {

        initIntro(cellHolder,i);
       cellHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    //给第i个view绑定什么信息，主要从已经在外面注入的List获取对象的信息，并塞到cellholder内
    private void initIntro(CellHolder cellHolder, int i) {
        Money_order order = (Money_order) mList.get(i);
        cellHolder.itemTitle.setText(order.getDestination());

    }


}
