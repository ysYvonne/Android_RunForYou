package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import xzh.com.materialdesign.http.HttpHelper;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/6/14.
 */

public class PhoneValid implements ProxyCommand {
    @Override
    public Object getWebData(String url, JSONObject parameter) {
        int code;
        String myUrl=url+"LoginServlet";

        String retSrc= HttpHelper.connectToServlet(myUrl,parameter);

        try {

            JSONObject result = new JSONObject(retSrc);

            if(result!=null){
                code = JsonUtil.getEntity(result.getString("code"),int.class);

                Log.v("dz","测试用户手机 code"+code);
                if(code == 1)
                    return true;
                else
                    return false;

            }else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
}
