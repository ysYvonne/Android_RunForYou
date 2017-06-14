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
    public Object getWebData( JSONObject parameter) {
        return new Proxy().GetCredit(parameter);
    }
}
