package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import xzh.com.materialdesign.http.HttpHelper;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/6/14.
 */

public class Register implements ProxyCommand {
    @Override
    public Object getWebData(String url, JSONObject parameter) {
        User user ;
        String myUrl=url+"LoginServlet";

        String retSrc = HttpHelper.connectToServlet(myUrl, parameter);

        try{
            JSONObject result = new JSONObject(retSrc);

            if(result != null){
                user = JsonUtil.getEntity(result.getString("user"),User.class);
                Log.v("dz","测试用户id"+user.getUserId());
                return user;
            }else{
                Log.v("ys", "注册失败");
                return null;
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
