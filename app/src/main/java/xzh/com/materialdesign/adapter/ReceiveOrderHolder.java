package xzh.com.materialdesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;


/**
 * Created by dz on 2017/5/14 on 10:55.
 */
public class ReceiveOrderHolder extends  RecyclerView.ViewHolder {

    @InjectView(R.id.receive_view)
    protected RelativeLayout itemView;



    @InjectView(R.id.receive_time)
    protected TextView receive_date_text;
    @InjectView(R.id.receive_money)
    protected TextView receive_item_text;


//    @InjectView(R.id.item_content)
        //TextView itemContent;
//    @InjectView(R.id.item_image)
//    CircleImageView itemImage;
//    @InjectView(R.id.item_count)
//    TextView itemCount;





    public ReceiveOrderHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

}
