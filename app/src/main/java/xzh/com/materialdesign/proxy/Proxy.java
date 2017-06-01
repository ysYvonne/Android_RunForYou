package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.model.*;
import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.utils.JsonUtil;
/**
 * Created by dz on 2017/5/24.
 */

public class Proxy {
    private static String url="http://112.74.124.48:8080/RunForYou/";
    private String retSrc;


    public static Object  getWebData(String methodName,JSONObject parameter){
//        JSONObject loginJson = new JSONObject();
//        try {
//            loginJson.put("method",methodName);
//            loginJson.put("parameter",parameter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        switch(methodName){
            case StateCode.AccountLogin:
                return AccountLogin(parameter);

            case StateCode.PhoneLogin:
                return PhoneLogin(parameter);
            case StateCode.GetLittleOrder:
                return GetLittleOrder(parameter);
        }
        return null;

    }

    private static String connectToServlet(String myUrl,JSONObject parameter ){
        String retSrc="";
        HttpPost request = new HttpPost(myUrl);
// 先封装一个 JSON 对象
// 绑定到请求 Entry
        StringEntity se = null;
        try {
            se = new StringEntity(parameter.toString());
            request.setEntity(se);
// 发送请求
            HttpResponse httpResponse = new DefaultHttpClient().execute(request);
// 得到应答的字符串，这也是一个 JSON 格式保存的数据
            retSrc = EntityUtils.toString(httpResponse.getEntity());
            Log.v("dz","接收到的http回应为：\n"+retSrc);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return retSrc;
        }
    }


    private static User AccountLogin(JSONObject parameter) {
//        String jsonReceive= "{\"destination\":\"首页标题测试8page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}\n{\"destination\":\"首页标题测试9page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}";
//        String[] jsonArray=jsonReceive.split("\n");
//        for(String s:jsonArray){
//            list.add(JsonUtil.getEntity(s,String.class));
//        }
        User user;
        String myUrl=url+"LoginServlet";
        Log.v("dz","听说你问我url是啥？"+myUrl);
        String retSrc=connectToServlet(myUrl,parameter);

// 生成 JSON 对象
        try{
            JSONObject result = new JSONObject(retSrc);
            if(result!=null){
               user = JsonUtil.getEntity(result.getString("user"),User.class);
                user.setUserId(1);
                Log.v("dz","测试用户id"+user.getUserId());
                return user;
                //Log.v("dz","成功吧，小宇宙！ "+user);
            }else{
                return null;
            }

        }  catch (JSONException e) {
            e.printStackTrace();
        }
//        User user=new User();
//        user.setUserId(123456);
        return null;
    }
    private static User PhoneLogin( JSONObject parameter) {

        User user=new User();
        user.setUserId(123456);
        return null;
    }

    private static List<LittleOrderBean> GetLittleOrder(JSONObject parameter) {
        return null;
    }




}
