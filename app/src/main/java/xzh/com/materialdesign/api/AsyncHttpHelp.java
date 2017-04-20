package xzh.com.materialdesign.api;

import xzh.com.materialdesign.app.MaterialAplication;


/**
 * 获取一个httpClient
 */
public class AsyncHttpHelp {
    public final static String PRIVATE_TOKEN = "private_token";
    public final static String GITOSC_PRIVATE_TOKEN = "git@osc_token";


    /**
     * 获得UserAgent
     *
     * @return
     */
    private static String getUserAgent() {
        MaterialAplication appContext = MaterialAplication.getInstance();
        StringBuilder ua = new StringBuilder("Git@OSC.NET");
        ua.append('/' + appContext.getPackageInfo().versionName + '_' + appContext.getPackageInfo
                ().versionCode);//App版本
        ua.append("/Android");//手机系统平台
        ua.append("/" + android.os.Build.VERSION.RELEASE);//手机系统版本
        ua.append("/" + android.os.Build.MODEL); //手机型号
        ua.append("/" + MaterialAplication.getInstance().getAppId());//客户端唯一标识
        return ua.toString();
    }


}
