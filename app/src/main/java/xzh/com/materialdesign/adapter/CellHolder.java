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
 * Created by xiangzhihong on 2016/3/2 on 15:49.
 */
public class CellHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_view)
    LinearLayout itemView;
    @InjectView(R.id.item_cator)
    TextView itemCator;
    @InjectView(R.id.cardView)
    CardView cardView;
    @InjectView(R.id.item_content)
    TextView itemContent;
    @InjectView(R.id.item_image)
    CircleImageView itemImage;
    @InjectView(R.id.item_count)
    TextView itemCount;

    public CellHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
        itemView.setTag(this);
    }

    private void resetView() {

    }
}
