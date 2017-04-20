package xzh.com.materialdesign.view.zdepth.shadow;

import android.graphics.Canvas;

import xzh.com.materialdesign.view.zdepth.ZDepthParam;


public interface Shadow {
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom);
    public void onDraw(Canvas canvas);
}
