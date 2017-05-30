package xzh.com.materialdesign.thread;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.Proxy;
import xzh.com.materialdesign.proxy.StateCode;


/**
 * Created by dz on 2017/5/30.
 */

public class AccountLoginThread extends AsyncTask<Integer, Integer, List> {

    private ProgressDialog dialog;
    //务必注意import的是android.os的
    Handler mHandler;
    JSONObject parameter;


    public AccountLoginThread(Handler handler , JSONObject parameter, ProgressDialog dialog) {
        super();
        this.dialog=dialog;
        this.mHandler=handler;
        this.parameter=parameter;
    }


    /**
     * 这里的Integer参数对应AsyncTask中的第一个参数
     * 这里的String返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */
    @Override
    protected List doInBackground(Integer... params) {
        try {
            //休眠2秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List list= Proxy.getWebData(new User().getClass(), StateCode.AccountLogin,parameter);

//        publishProgress();

        return list;
    }


    /**
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
     */
//    @Override
    protected void onPostExecute(List result) {
        Log.v("dz","thread execute over");
        Message msg = mHandler.obtainMessage();

        msg.obj = result;

        mHandler.sendMessage(msg);
    }


    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
    @Override
    protected void onPreExecute() {
        dialog.setTitle("提示");
        dialog.setMessage("正在登陆，请稍后...");
        dialog.show();
    }


    /**
     * 这里的Intege参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
//    @Override
//    protected void onProgressUpdate(Integer... values) {
//
//    }





}