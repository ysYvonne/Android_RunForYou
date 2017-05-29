package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.model.Money_order;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/5/24.
 */

public class Proxy {
    private static String LogService="10.10.42.3/LogService";
    static List  list=new ArrayList ();

    public static List  getWebData(Class c,String methodName,JSONObject parameter){
//        JSONObject loginJson = new JSONObject();
//        try {
//            loginJson.put("method",methodName);
//            loginJson.put("parameter",parameter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        list.clear();
        switch(methodName){
            case StateCode.AccountLogin:
                AccountLogin(c,parameter);
                break;
        }
        return list;

    }

    private static void AccountLogin(Class c,JSONObject parameter) {
        String jsonReceive= "{\"destination\":\"首页标题测试8page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}\n{\"destination\":\"首页标题测试9page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}";
        String[] jsonArray=jsonReceive.split("\n");
        for(String s:jsonArray){
            list.add(JsonUtil.getEntity(s,String.class));
        }
    }


}
