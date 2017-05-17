package xzh.com.materialdesign.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import xzh.com.materialdesign.R;
import xzh.com.materialdesign.api.MySharedPreferences;
import xzh.com.materialdesign.api.SetTitleTool;
import xzh.com.materialdesign.ui.OrderActivity;
import xzh.com.materialdesign.ui.PersonalInfoActivity;
import xzh.com.materialdesign.utils.ActivityHelper;
import xzh.com.materialdesign.utils.UIHelper;
import xzh.com.materialdesign.view.NavigationDrawerCallbacks;
import xzh.com.materialdesign.view.NavigationDrawerFragment;
import xzh.com.materialdesign.view.PullCallback;
import xzh.com.materialdesign.view.PullToLoadView;
import xzh.com.materialdesign.view.ThemeManager;
import xzh.com.materialdesign.adapter.BaseAdapterInterface;

@SuppressLint("NewApi")//屏蔽android lint错误
public abstract class MyBaseActivity extends AppCompatActivity implements
        NavigationDrawerCallbacks {

    public Context mContext;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PullToLoadView mPullToLoadView;


    protected BaseAdapterInterface mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;
    private int nextPage;
    private boolean on_off = false;
    private String userId;


    @SuppressLint("NewApi")


    protected abstract void setmTitle();

    public void setmTitle(CharSequence mTitle) {
        this.mTitle = mTitle;
    }

    protected abstract void setmAdapter();

    public void setmAdapter(BaseAdapterInterface mAdapter) {
        this.mAdapter = mAdapter;
    }

//    protected abstract void loadData();

    protected abstract void loadList();
//    {
//        // 这个地方需要从服务器取数据生成一个list
//        Money_order order =new Money_order();
//        order.setDestination("如果是标题就对了");
//        mAdapter.add(order);
//    }

//    protected abstract   void createEventBus()  ;
//    {
//        EventBus.getDefault().register(this);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        mContext = this;
        MySharedPreferences msp = new MySharedPreferences("userId", this);
        userId = msp.getValue("userId");
//        Toast toast = Toast.makeText(this, userId, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_topdrawer);
        ButterKnife.inject(this);
//        createEventBus();

        init();
        ImageButton bt_dial = (ImageButton) findViewById(R.id.img_float_btn);
        bt_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(mContext, OrderActivity.class);
            }
        });


    }

    private void init() {
        setmTitle();
        setmAdapter();
        loadList();

        initViews();
        initEvent();
    }


    private void initEvent() {
//        设置个人信息修改跳转
        RelativeLayout v=(RelativeLayout) findViewById(R.id.navigationHeader);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHelper.startActivity(mContext, PersonalInfoActivity.class);
            }
        });
//        -------------------->
        RecyclerView mRecyclerView = mPullToLoadView.getRecyclerView();
        LinearLayoutManager manager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
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
        mToolbar.setBackgroundColor(ThemeManager.with(mContext).getCurrentColor());
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        DrawerLayout draw = (DrawerLayout) findViewById(R.id.drawer);
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
    final public void onNavigationDrawerItemSelected(int position) {
        setTitleName(position);
    }

    private void setTitleName(int position) {
        if (!on_off) {
            mTitle = "首页";
            on_off = true;
        } else {
            SetTitleTool.isSetTitleName(mContext, position);
        }

        if (mToolbar != null)
            mToolbar.setTitle(mTitle);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    protected void loadData(final int page){
        //        此处需要子类重写
        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isHasLoadedAll) {
                    Toast.makeText(mContext, "没有更多数据了",
                            Toast.LENGTH_SHORT).show();
                }
                if (page > 10) {
                    isHasLoadedAll = true;
                    return;
                }
//                for (int i = 0; i <= 15; i++) {
//                    mAdapter.add(i + "");
//                }
                loadList();
                mPullToLoadView.setComplete();
                isLoading = false;
                nextPage = page + 1;
            }
        }, 3000);

    }




}
