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
import xzh.com.materialdesign.ui.DetailsActivity;
import xzh.com.materialdesign.ui.MyOrderActivity;
import xzh.com.materialdesign.ui.MyorderDetailAvtivity;
import xzh.com.materialdesign.utils.IntroUtils;

/**
 * Created by dz on 2017/5/2 on
 */
public class MyOrderAdapter extends RecyclerView.Adapter<CellHolder> {

   private Context context;
    private List<String> mList;

    public MyOrderAdapter(Context context) {
       this.context=context;
        mList = new ArrayList<>();
    }

    @Override
    public CellHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.myorder_initiate, viewGroup, false);
        return new CellHolder(view);
    }

    @Override
    public void onBindViewHolder(CellHolder cellHolder, int i) {

        initIntro(cellHolder,i);
       cellHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             context.startActivity(new Intent(context, MyorderDetailAvtivity.class));
           }
       });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(String s) {
        mList.add(s);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    private static final String INTRO_CARD = "material_intro";
    private void initIntro(CellHolder cellHolder, int i) {
        if(i==0)
        IntroUtils.showIntro((Activity) context,cellHolder.cardView,INTRO_CARD,"This is card! Hello There. You can set this text!");
    }


}
