package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import xzh.com.materialdesign.http.HttpHelper;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/6/14.
 */

public class ContactUs implements ProxyCommand {
    @Override
    public Object getWebData(String url, JSONObject parameter) {

        String myUrl=url+"InformationServlet";

        String retSrc = HttpHelper.connectToServlet(myUrl, parameter);

        try{
            JSONObject result = new JSONObject(retSrc);

            if(result != null){
                int code = -1;
                code = JsonUtil.getEntity(result.getString("code"),int.class);
                Log.v("ys","测试code "+code);

                if(code == 1)
                    return true;
                else
                    return false;
            }else{
                Log.v("ys", "上传失败");
                return false;
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
