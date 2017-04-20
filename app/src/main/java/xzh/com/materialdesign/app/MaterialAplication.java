package xzh.com.materialdesign.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.UUID;

import xzh.com.materialdesign.api.AppConfig;
import xzh.com.materialdesign.utils.FileManager;
import xzh.com.materialdesign.utils.ImageUtil;
import xzh.com.materialdesign.utils.StringUtils;

/**
 * Created by xiangzhihong on 2016/3/2 on 10:44.
 */
public class MaterialAplication extends Application{

   private static MaterialAplication appContext;
    public static final int PAGE_SIZE = 20;// 默认分页大小

    public static MaterialAplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
        init();
    }

    private void init() {
//        ImageUtil.init(this, FileManager.getHomeDir());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }


    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

}
