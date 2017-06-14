package xzh.com.materialdesign.proxy;

import org.json.JSONObject;

/**
 * Created by dz on 2017/6/14.
 */

public interface ProxyCommand {
    public Object getWebData(String url,JSONObject parameter);
}
