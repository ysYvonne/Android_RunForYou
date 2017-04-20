package xzh.com.materialdesign.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ypy.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.base.BaseActivity;
import xzh.com.materialdesign.utils.CommonUtils;
import xzh.com.materialdesign.view.ThemeManager;
import xzh.com.materialdesign.view.TitleBar;

/**
 * Created by xiangzhihong on 2016/3/31 on 14:58.
 */
public class ThemColorChangeActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.preview)
    ImageView preview;
    @InjectView(R.id.select_panel)
    LinearLayout selectPanel;
    @InjectView(R.id.select_panel_view)
    HorizontalScrollView selectPanelView;
    @InjectView(R.id.title_bar)
    TitleBar titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_change);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        titleBar.setTitle("主题更换");
        titleBar.setOnBackListener(new TitleBar.ITitleBackListener() {
            @Override
            public void onTitleBackClick() {
                finish();
            }
        });
        initDefault();
    }

    private void initDefault() {
        preview.setBackgroundColor(ThemeManager.with(this).getCurrentColor());
        for (int i = 0; i < ThemeManager.BACKGROUNDS.length; i++) {
            View view = new View(this);
            view.setBackgroundColor(ThemeManager.BACKGROUNDS[i]);
            view.setOnClickListener(this);

            int dp70 = (int) CommonUtils.dpToPx(this, 70);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp70, dp70);
            int margin = (int) CommonUtils.dpToPx(this, 10);
            params.setMargins(margin, margin, margin, margin);
            selectPanel.addView(view, params);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < selectPanel.getChildCount(); i++) {
            if (v == selectPanel.getChildAt(i)) {
                preview.setBackgroundColor(ThemeManager.BACKGROUNDS[i]);
                ThemeManager.with(this).saveColor(i);
                EventBus.getDefault().post(ThemeManager.with(this).getCurrentColor());
                break;
            }
        }
    }
}
