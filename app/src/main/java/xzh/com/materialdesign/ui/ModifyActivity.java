package xzh.com.materialdesign.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.view.TitleBar;

/**
 * Created by botan on 2017/4/22.
 */

public class ModifyActivity extends BaseActivity {

    TitleBar titleBar;

    @InjectView(R.id.modify_confirm)
    Button modify_confirm;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.modify_custom);
        ButterKnife.inject(this);
        modify_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处需要添加修改OrderActivity的方法
                finish();
            }
        });
    }

}
