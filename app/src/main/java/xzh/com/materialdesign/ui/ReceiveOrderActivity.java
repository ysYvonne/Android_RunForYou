package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ypy.eventbus.EventBus;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.adapter.ReceiveOrderAdapter;
import xzh.com.materialdesign.api.SetTitleTool;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.utils.UIHelper;
import xzh.com.materialdesign.view.NavigationDrawerCallbacks;
import xzh.com.materialdesign.view.NavigationDrawerFragment;
import xzh.com.materialdesign.view.PullCallback;
import xzh.com.materialdesign.view.PullToLoadView;
import xzh.com.materialdesign.view.ThemeManager;

@SuppressLint("NewApi")//屏蔽android lint错误
public class ReceiveOrderActivity extends AppCompatActivity implements
        NavigationDrawerCallbacks {

    private Context mContext;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PullToLoadView mPullToLoadView;
    private ReceiveOrderAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;
    private int nextPage;
    private boolean on_off=false;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_topdrawer);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        mContext = ReceiveOrderActivity.this;

        init();
        ImageButton bt_dial = (ImageButton) findViewById(R.id.img_float_btn);
        bt_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(ReceiveOrderActivity.this,OrderActivity.class);
            }
        });
    }

    private void init() {
        initViews();
        initEvent();
    }


    private void initEvent() {
        RecyclerView mRecyclerView = mPullToLoadView.getRecyclerView();
        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ReceiveOrderAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        mPullToLoadView.isLoadMoreEnabled(true);
        mPullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                loadData(nextPage);
            }

            @Override
            public void onRefresh() {
                mAdapter.clear();
                isHasLoadedAll = false;
                loadData(1);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return isHasLoadedAll;
            }
        });

        mPullToLoadView.initLoad();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setBackgroundColor(ThemeManager.with(this).getCurrentColor());
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        DrawerLayout draw= (DrawerLayout) findViewById(R.id.drawer);
        mNavigationDrawerFragment.setDrawerLayout(draw);
        mPullToLoadView = (PullToLoadView) findViewById(R.id.pullToLoadView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_theme:
                UIHelper.setting(mContext);
                break;
            case R.id.action_about:
                UIHelper.about(mContext);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        setTitleName(position);
    }

    private void setTitleName(int position) {
       if(!on_off){
           mTitle="接收订单";
           on_off=true;
       }else{
           SetTitleTool.isSetTitleName(this,position);
       }
        if(mToolbar!=null)
            mToolbar.setTitle(mTitle);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    private void loadData(final int page) {
        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isHasLoadedAll) {
                    Toast.makeText(ReceiveOrderActivity.this, "没有更多数据了",
                            Toast.LENGTH_SHORT).show();
                }
                if (page > 10) {
                    isHasLoadedAll = true;
                    return;
                }
                for (int i = 0; i <= 15; i++) {
                    mAdapter.add(i + "");
                }
                mPullToLoadView.setComplete();
                isLoading = false;
                nextPage = page + 1;
            }
        }, 3000);
    }

    public void onEventMainThread(int color) {
        if (color!=-1){
            mToolbar.setBackgroundColor(color);
        }
    }
}
