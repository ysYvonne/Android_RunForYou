package xzh.com.materialdesign.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by botan on 2017/4/22.
 */
public class OrderActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        ButterKnife.inject(this);

        ImageView back = (ImageView) findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView modify = (TextView) findViewById(R.id.order_layout_modify_custom);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(OrderActivity.this,ModifyActivity.class);
            }
        });

    }


}
