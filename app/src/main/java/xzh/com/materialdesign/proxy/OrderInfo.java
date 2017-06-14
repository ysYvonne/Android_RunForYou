package xzh.com.materialdesign.proxy;

import org.json.JSONException;
import org.json.JSONObject;

import xzh.com.materialdesign.http.HttpHelper;
import xzh.com.materialdesign.model.Orders;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/6/14.
 */

public class OrderInfo implements ProxyCommand {
    @Override
    public Object getWebData(String url, JSONObject parameter) {
        Orders orders;
        String myUrl = url + "OrderServlet";
        String retSrc = HttpHelper.connectToServlet(myUrl, parameter);

        try {
            JSONObject result = new JSONObject(retSrc);
            if (result != null) {
                orders = JsonUtil.getEntity(result.getString("order"), Orders.class);
                return orders;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
