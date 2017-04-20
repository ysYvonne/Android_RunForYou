package xzh.com.materialdesign.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

/**
 * Created by xiangzhihong on 2016/4/6 on 17:11.
 * 主要是一些模板
 */
public class IntroUtils {

    public static void showIntro(Activity context,View view, String usageId, String text){
        new MaterialIntroView.Builder(context)
                .enableDotAnimation(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .performClick(true)
//                .setInfoText(text)
                .setTarget(view)
                .setUsageId(usageId)
                .show();
    }
}
