package xzh.com.materialdesign.proxy;

import org.json.JSONException;
import org.json.JSONObject;

import xzh.com.materialdesign.http.HttpHelper;
import xzh.com.materialdesign.model.Credit;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/6/14.
 */

public class GetCredit implements ProxyCommand {
    @Override
    public Object getWebData(String url, JSONObject parameter) {
        Credit credit;
        String myUrl = url+"InformationServlet";
        String retSrc = HttpHelper.connectToServlet(myUrl,parameter);

        try{
            JSONObject result = new JSONObject(retSrc);

            if(result !=null){
                credit = JsonUtil.getEntity(result.getString("credit"),Credit.class);
                return  credit;
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
