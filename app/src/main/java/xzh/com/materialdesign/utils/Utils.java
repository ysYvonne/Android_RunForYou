package xzh.com.materialdesign.utils;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;

import xzh.com.materialdesign.R;
import xzh.com.materialdesign.model.NavigationItem;

public class Utils {
	private static Utils mDatas;
	private List<NavigationItem> mList;

	private Utils(Context context){
		mList=new ArrayList<NavigationItem>();
		mList.add(new NavigationItem("首页", context.getResources().getDrawable(R.drawable.ic_drawer_home_normal),Style.DEFAULT));
		mList.add(new NavigationItem("发现", context.getResources().getDrawable(R.drawable.ic_drawer_explore_normal),Style.DEFAULT));
		mList.add(new NavigationItem("关注", context.getResources().getDrawable(R.drawable.ic_drawer_follow_normal),Style.DEFAULT));
		mList.add(new NavigationItem("收藏", context.getResources().getDrawable(R.drawable.ic_drawer_collect_normal),Style.DEFAULT));
		mList.add(new NavigationItem("圆桌", context.getResources().getDrawable(R.drawable.ic_drawer_draft_normal),Style.DEFAULT));
		mList.add(new NavigationItem("私信", context.getResources().getDrawable(R.drawable.ic_drawer_register_normal),Style.HASLINE));
		mList.add(new NavigationItem("切换主题", null,Style.NO_ICON));
		mList.add(new NavigationItem("设置", null,Style.NO_ICON));
	}

	public static Utils getInstance(Context context){
		if(mDatas==null){
			synchronized (Utils.class) {
				if(mDatas==null){
					mDatas=new Utils(context);
				}
			}
		}
		return mDatas;

	}


	public  List<NavigationItem> getMenu(){
		return new ArrayList<NavigationItem>(mList);
	}

   //滚动事件
	public enum ScrollDirection {
		UP,
		DOWN,
		SAME
	}

	public enum Style{
		DEFAULT,HASLINE,NO_ICON;
	}

}
