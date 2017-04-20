package xzh.com.materialdesign.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import xzh.com.materialdesign.ui.ThemColorChangeActivity;

/**
 * Created by xiangzhihong on 2016/3/2 on 10:25.
 */
public class UIHelper {

   //公共的跳转
    public static void openClass(Context context,Class tClass) {
        Intent intent = new Intent(context, tClass);
        context.startActivity(intent);
    }

   //设置
    public static void setting(Context context) {
        Intent intent = new Intent(context, ThemColorChangeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //关于
    public static void about(Context context) {
//        Intent intent = new Intent(context, Setting.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
        Toast.makeText(context,"关于",Toast.LENGTH_LONG).show();
    }


}
