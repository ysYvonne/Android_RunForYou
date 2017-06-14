package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.http.HttpHelper;
import xzh.com.materialdesign.model.LittleOrderBean;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/6/14.
 */

public class GetLittleOrder implements ProxyCommand {
    @Override
    public Object getWebData( JSONObject parameter) {
        return new Proxy().GetLittleOrder(parameter);
    }
}
