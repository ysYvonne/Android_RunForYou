package xzh.com.materialdesign.api;

import android.content.Context;

import xzh.com.materialdesign.ui.MainActivity;
import xzh.com.materialdesign.ui.MyOrderActivity;
import xzh.com.materialdesign.ui.ReceiveOrderActivity;
import xzh.com.materialdesign.ui.ThemColorChangeActivity;
import xzh.com.materialdesign.utils.ActivityHelper;

/**
 * Created by dz on 2017/5/10.
 */

public class SetTitleTool {
    public static void isSetTitleName( Context context,int position){
        //修改这个方法之前需要调整Utils的内容的顺序
       

            switch (position) {
                case 0: {
                   // "首页";
                    ActivityHelper.startActivity(context,MainActivity.class);
                }
                break;
                case 1: {
                    //"我的订单";
                    ActivityHelper.startActivity(context,MyOrderActivity.class);
                }
                break;
                case 2:
                    //"接收订单";
                    ActivityHelper.startActivity(context,ReceiveOrderActivity.class);
                    break;
                case 3:
                    //"收藏";
                    break;
                case 4:
                    //"圆桌";
                    break;
                case 5:
                    //"私信";
                    break;
                case 6:
                    ActivityHelper.startActivity(context,ThemColorChangeActivity.class);
                    break;
                default:

                    break;
            }

    }
}
