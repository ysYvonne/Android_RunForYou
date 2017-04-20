package xzh.com.materialdesign.http;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.UnsupportedEncodingException;

import xzh.com.materialdesign.utils.SysUtil;


public class HttpClient {
    private static Context context;
    private static AsyncHttpClient asyncHttpClient;

    public static void init(Context ctx) {
        context = ctx;
        asyncHttpClient = new AsyncHttpClient();
        //below are not required
        asyncHttpClient.addHeader("x-api-version", "3");
        asyncHttpClient.addHeader("x-app-version", "2.5.4");
        asyncHttpClient.addHeader("x-device", SysUtil.getPhoneModel());
        asyncHttpClient.addHeader("x-os", "Android " + Build.VERSION.RELEASE);
        asyncHttpClient.addHeader("za", "OS=Android " + Build.VERSION.RELEASE + "&Platform=" + SysUtil.getPhoneModel());
    }

    public static void get(String url, TextHttpResponseHandler textHttpResponseHandler) {
        //        asyncHttpClient.addHeader("Authorization","");
        asyncHttpClient.get(context, url, textHttpResponseHandler);
    }


    public static void downloadFile(String url, BinaryHttpResponseHandler handler) {
        asyncHttpClient.get(context, url, handler);
    }

}
