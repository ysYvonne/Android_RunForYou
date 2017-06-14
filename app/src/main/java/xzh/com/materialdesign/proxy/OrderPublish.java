package xzh.com.materialdesign.proxy;

import org.json.JSONException;
import org.json.JSONObject;

import xzh.com.materialdesign.http.HttpHelper;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/6/14.
 */

public class OrderPublish implements ProxyCommand {
    @Override
    public Object getWebData(String url, JSONObject parameter) {
        int code;
        String myUrl = url+"OrderServlet";
        String retSrc = HttpHelper.connectToServlet(myUrl,parameter);

        try{
            JSONObject result = new JSONObject(retSrc);

            if(result !=null){
                code = JsonUtil.getEntity(result.getString("code"),int.class);
                return  code;
            }
            else{
                return -1;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return -1;
    }
}
