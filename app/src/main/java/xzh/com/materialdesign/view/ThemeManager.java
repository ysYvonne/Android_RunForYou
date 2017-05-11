package xzh.com.materialdesign.view;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;

import xzh.com.materialdesign.utils.SharedUtils;

public class ThemeManager {

    public final static String KEY = "cur_color";

    private static ThemeManager self;
    private Context mContext;
    private ArrayList<IThemeListener> iThemeListeners;

    public static int BACKGROUNDS[] = {Color.rgb(180,82,205)};

    private ThemeManager(Context context) {
        this.mContext = context;
    }

    public static ThemeManager with(Context ctx) {
        if (self == null) {
            self = new ThemeManager(ctx);
        } else {
            self.mContext = ctx;
        }

        return self;
    }

    public int getCurrentColor() {
        return SharedUtils.getInt(mContext, KEY, BACKGROUNDS[0]);
    }

    public void saveColor(int index) {
        SharedUtils.saveInt(mContext, KEY, BACKGROUNDS[index]);
        notifyThemeChange();
    }

    public void registerListener(IThemeListener listener) {
        if (iThemeListeners == null) {
            iThemeListeners = new ArrayList<>();
        }
        iThemeListeners.add(listener);
    }

    public void notifyThemeChange() {
        if (iThemeListeners == null) return;
        int curColor = getCurrentColor();
        Iterator<IThemeListener> iterator = iThemeListeners.iterator();
        while (iterator.hasNext()) {
            IThemeListener next = iterator.next();
            if (next == null) {
                iterator.remove();
            } else {
                next.onThemeChange(curColor);
            }
        }
    }

    public interface IThemeListener {
        void onThemeChange(int color);
    }

}
