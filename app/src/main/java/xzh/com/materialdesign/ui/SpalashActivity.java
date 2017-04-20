package xzh.com.materialdesign.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.Api;
import xzh.com.materialdesign.utils.ImageUtil;
import xzh.com.materialdesign.utils.SysUtil;
import xzh.com.materialdesign.utils.UIHelper;

/**
 * Created by xiangzhihong on 2016/3/2 on 12:21.
 */
public class SpalashActivity extends AppCompatActivity {
    @InjectView(R.id.sd_splash)
    ImageView sd_splash;
    @InjectView(R.id.tv_author)
    TextView tv_author;

    private Context mContext;
    private Animation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);
        ButterKnife.inject(this);
        mContext = SpalashActivity.this;
        init();
    }

    private void init() {
        initAnimation();
    }


    private void initAnimation() {
   /*   animation= AnimationUtils.loadAnimation(this,R.anim.anim_splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/

        startActivity(new Intent(mContext, MainActivity.class));

        String dimen;
        int width = SysUtil.getScreenWidth(this);
        if (width >= 900) {
            dimen = "1080*1776";
        } else if (width >= 600 && width < 900) {
            dimen = "720*1184";
        } else if (width >= 400 && width < 600) {
            dimen = "480*728";
        } else {
            dimen = "320*432";
        }
        getImage(Api.URL_SPLASH_IMG + dimen);

    }

    private void getImage(String s) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(mContext,s,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, String content) {
                super.onSuccess(statusCode, content);
                bindView(content);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
            }
        });
    }

    private void bindView(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            String img = jsonObject.optString("img");
            String text = jsonObject.optString("text");
            ImageLoader.getInstance().displayImage(img,sd_splash);
            tv_author.setText(text);
            sd_splash.startAnimation(animation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
