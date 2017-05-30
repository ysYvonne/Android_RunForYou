package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.model.*;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.utils.JsonUtil;

/**
 * Created by dz on 2017/5/24.
 */

public class Proxy {
    private static String LogService="10.10.42.3/LogService";


    public static Object  getWebData(Class c,String methodName,JSONObject parameter){
//        JSONObject loginJson = new JSONObject();
//        try {
//            loginJson.put("method",methodName);
//            loginJson.put("parameter",parameter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        switch(methodName){
            case StateCode.AccountLogin:
                return AccountLogin(c,parameter);

            case StateCode.PhoneLogin:
                return PhoneLogin(c,parameter);
        }
        return null;

    }
    private static User AccountLogin(Class c,JSONObject parameter) {
//        String jsonReceive= "{\"destination\":\"首页标题测试8page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}\n{\"destination\":\"首页标题测试9page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}";
//        String[] jsonArray=jsonReceive.split("\n");
//        for(String s:jsonArray){
//            list.add(JsonUtil.getEntity(s,String.class));
//        }
        User user=new User();
        user.setUserId(123456);
        return user;
    }
    private static User PhoneLogin(Class c, JSONObject parameter) {

        User user=new User();
        user.setUserId(123456);
        return null;
    }


}
