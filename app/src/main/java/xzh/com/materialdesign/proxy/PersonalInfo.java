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

public class PersonalInfo implements ProxyCommand {
    @Override
    public Object getWebData(String url, JSONObject parameter) {
        User user = new User();
        int code = -1;
        String myUrl = url+"InformationServlet";
        String retSrc = HttpHelper.connectToServlet(myUrl, parameter);

        try{
            JSONObject result = new JSONObject(retSrc);

            if(parameter.getString("type").equals("getUser") && result !=null){
                user = JsonUtil.getEntity(result.getString("user"),User.class);
                Log.v("ys", "个人信息测试用户：  " + user.getName());
                return  user;
            }
            else if(parameter.getString("type").equals("updateInfomation") && result !=null){
                code = JsonUtil.getEntity(result.getString("code"),int.class);
                Log.v("ys", "个人信息测试更改是否成功：  " + code);
                user.setSex(code) ;
                return user;
            }
            else{
                return null;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
