package xzh.com.materialdesign.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.view.CircleImageView;

/**
 * Created by dz on 2017/5/14 on 10:55.
 */
public class CellHolder extends RecyclerView.ViewHolder {
    //只能用ButterKnife注册控件id,但在构造器中只需要注入最外层布局的控件即可
    @InjectView(R.id.item_view)
    protected LinearLayout itemView;
    @InjectView(R.id.item_title)
    protected TextView itemTitle;


//    @InjectView(R.id.item_content)
        //TextView itemContent;
//    @InjectView(R.id.item_image)
//    CircleImageView itemImage;
//    @InjectView(R.id.item_count)
//    TextView itemCount;





    public CellHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }


}
