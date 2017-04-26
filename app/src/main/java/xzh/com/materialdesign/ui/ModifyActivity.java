package xzh.com.materialdesign.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;
import xzh.com.materialdesign.view.TitleBar;

/**
 * Created by botan on 2017/4/22.
 */

public class ModifyActivity extends BaseActivity {

    TitleBar titleBar;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_custom);
        ButterKnife.inject(this);
    }

}
