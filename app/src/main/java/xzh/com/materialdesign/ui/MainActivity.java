package xzh.com.materialdesign.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ypy.eventbus.EventBus;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.adapter.HomeAdapter;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.utils.IntroUtils;
import xzh.com.materialdesign.utils.UIHelper;
import xzh.com.materialdesign.view.NavigationDrawerCallbacks;
import xzh.com.materialdesign.view.NavigationDrawerFragment;
import xzh.com.materialdesign.view.PullCallback;
import xzh.com.materialdesign.view.PullToLoadView;
import xzh.com.materialdesign.view.ThemeManager;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements
        NavigationDrawerCallbacks {

    private Context mContext;
    private Toolbar mToolbar;
    private CharSequence mTitle="知乎";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PullToLoadView mPullToLoadView;
    private HomeAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;
    private int nextPage;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_topdrawer);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        mContext = MainActivity.this;
        init();
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
        mAdapter = new HomeAdapter(mContext);
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
        switch (position) {
            case 0:
                mTitle = "首页";
                break;
            case 1:
                mTitle = "发现";
                break;
            case 2:
                mTitle = "关注";
                break;
            case 3:
                mTitle = "收藏";
                break;
            case 4:
                mTitle = "圆桌";
                break;
            case 5:
                mTitle = "私信";
                break;
            case 6:
                ActivityHelper.startActivity(this,ThemColorChangeActivity.class);
             break;
                default:
                mTitle="知乎";
                break;
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
                    Toast.makeText(MainActivity.this, "没有更多数据了",
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
