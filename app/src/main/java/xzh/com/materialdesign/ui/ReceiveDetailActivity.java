package xzh.com.materialdesign.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;

/**
 * Created by dz on 2017/5/10.
 */

public class ReceiveDetailActivity extends AppCompatActivity {
    ImageButton navBack;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_transport_detail);
        navBack=(ImageButton)findViewById(R.id.nav_back);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
