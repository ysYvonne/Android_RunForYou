package xzh.com.materialdesign.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;

/**
 * Created by dz on 2017/5/14 on 10:55.
 */
public class MyOrderHolder extends  RecyclerView.ViewHolder {

    @InjectView(R.id.myorder_item)
    protected LinearLayout itemView;
    @InjectView(R.id.myorder_time)
    protected TextView myorder_text;
    @InjectView(R.id.myorder_money)
    protected TextView myorder_date_text;


//    @InjectView(R.id.item_content)
        //TextView itemContent;
//    @InjectView(R.id.item_image)
//    CircleImageView itemImage;
//    @InjectView(R.id.item_count)
//    TextView itemCount;





    public MyOrderHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

}
