package xzh.com.materialdesign.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetworkUtil {
    private static ConnectivityManager conManager;
    private static NetworkUtil instance;
    private static Context context;

    private NetworkUtil(Context context) {
        NetworkUtil.conManager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
    }

    public static void init(Context ctx) {
        NetworkUtil.context = ctx;
    }

    public static NetworkUtil getInstance() {
        if (NetworkUtil.instance == null) {
            NetworkUtil.instance = new NetworkUtil(context);
        }
        return NetworkUtil.instance;
    }

    public boolean checkNetworkAvailable() {
        return checkMobileActive() || checkWifiActive();
    }

    public boolean isNetworkRoaming(Context context) {
        if (conManager != null) {
            NetworkInfo info = conManager.getActiveNetworkInfo();
            if (info != null
                    && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.isNetworkRoaming()) {
                    Log.d("Tag", "network is roaming");
                    return true;
                } else {
                    Log.d("Tag", "network is not roaming");
                }
            }
        }
        return false;
    }

    public boolean checkWifiActive() {
        return conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED;
    }
    public boolean checkMobileActive() {
        return conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED;
    }
}
