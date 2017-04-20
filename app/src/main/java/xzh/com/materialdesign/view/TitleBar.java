package xzh.com.materialdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xzh.com.materialdesign.R;
import xzh.com.materialdesign.utils.CommonUtils;
import xzh.com.materialdesign.utils.Utils;
import xzh.com.materialdesign.view.zdepth.ZDepthShadowLayout;

public class TitleBar extends ZDepthShadowLayout
        implements ThemeManager.IThemeListener {

    private Context context;
    private RelativeLayout mTitleBar;
    private ImageView mLeftView;
    private TextView mTitleText;

    public TitleBar(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init() {
        mTitleBar = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.topbar_layout, null);
        mLeftView = (ImageView) mTitleBar.findViewById(R.id.nav_back);
        mTitleText = (TextView) mTitleBar.findViewById(R.id.nav_title);
        float height = CommonUtils.dpToPx(context,50f);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)height);
        addView(mTitleBar, params);

        mLeftView.setVisibility(INVISIBLE);
        mTitleBar.setBackgroundColor(ThemeManager.with(getContext()).getCurrentColor());

        ThemeManager.with(getContext()).registerListener(this);
    }

    public void setOnBackListener(final ITitleBackListener listener) {
        mLeftView.setVisibility(View.VISIBLE);
        if (listener != null) {
            mLeftView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTitleBackClick();
                }
            });
        }
    }

    public void setTitle(String text) {
        mTitleText.setText(text);
    }

    @Override
    public void onThemeChange(int color) {
        mTitleBar.setBackgroundColor(color);
    }

    public interface ITitleBackListener {
        void onTitleBackClick();
    }

}
