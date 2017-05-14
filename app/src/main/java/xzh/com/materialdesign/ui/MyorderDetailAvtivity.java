package xzh.com.materialdesign.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;

/**
 * Created by dz on 2017/5/10.
 */

public class MyorderDetailAvtivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_myorder_detail);
        ButterKnife.inject(this);

        ImageView back = (ImageView) findViewById(R.id.nav_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
