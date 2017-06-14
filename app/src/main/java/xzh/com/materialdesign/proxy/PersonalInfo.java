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
    public Object getWebData( JSONObject parameter) {
        return new Proxy().PersonalInfo(parameter);
    }
}
