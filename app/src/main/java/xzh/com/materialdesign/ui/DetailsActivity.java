package xzh.com.materialdesign.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.Api;
import xzh.com.materialdesign.model.DetailEntity;
import xzh.com.materialdesign.utils.JsonUtil;
import xzh.com.materialdesign.view.UWebView;

/**
 * Created by xiangzhihong on 2016/3/18 on 16:43.
 */
public class DetailsActivity extends AppCompatActivity {

    @InjectView(R.id.sd_news_img)
    ImageView sdNewsImg;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_img_source)
    TextView tvImgSource;
    @InjectView(R.id.block_story_img)
    FrameLayout blockStoryImg;
    @InjectView(R.id.block_recommenders)
    LinearLayout blockRecommenders;
    @InjectView(R.id.webview)
    UWebView webview;
    @InjectView(R.id.nav_back)
    ImageButton navBack;
    @InjectView(R.id.nav_title)
    TextView navTitle;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        ButterKnife.inject(this);
        mContext = DetailsActivity.this;
        init();
    }

    private void init() {
        navTitle.setText("详情");
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDetailData();
    }

    private void getDetailData() {
        String uri = Api.DETAIL_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mContext, uri, new AsyncHttpResponseHandler() {
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

    private void bindView(String data) {
        DetailEntity news = JsonUtil.getEntity(data, DetailEntity.class);
        if (!TextUtils.isEmpty(news.image)) {
            ImageLoader.getInstance().displayImage(news.image, sdNewsImg);
        } else {
            blockStoryImg.setVisibility(View.GONE);
        }
        tvTitle.setText(news.title);
        tvImgSource.setText(news.image_source);
        if (news.recommenders == null) {
            blockRecommenders.setVisibility(View.GONE);
        } else {
            blockRecommenders.removeViews(1, blockRecommenders.getChildCount() - 1);
            for (DetailEntity.Recommender rec : news.recommenders) {
                ImageView avatar = (ImageView) View.inflate(mContext, R.layout.item_recommender, null);
                String avertUri = rec.avatar;
                ImageLoader.getInstance().displayImage(avertUri, avatar);
                blockRecommenders.addView(avatar);
            }
        }

        //build a html content and load it with webview
        String css = "";
        for (String css_url : news.css) {
            css += "<link rel=\"stylesheet\" href=" + css_url + ">\n";
        }
        String js = "";
        for (String js_url : news.js) {
            js += "<script src=" + js_url + "/>\n";
        }
        String body = news.body.replaceAll("<div class=\"img-place-holder\"></div>", "");

        StringBuilder builder = new StringBuilder();
        builder.append("<html>\n")
                .append("<head>\n")
                .append(css).append(js)
                .append("</head>\n")
                .append("<body>")
                .append(body)
                .append("</body>\n")
                .append("</html>");
        webview.loadData(builder.toString(), "text/html;charset=UTF-8", "UTF-8");
    }
}
