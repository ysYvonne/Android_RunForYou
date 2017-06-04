package xzh.com.materialdesign.proxy;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
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

            case StateCode.PhoneValid:
                return PhoneValid(parameter);

            case StateCode.PhoneLogin:
                return PhoneLogin(parameter);

            case StateCode.GetLittleOrder:
                return GetLittleOrder(parameter);

            case StateCode.OrderPublish:
                return OrderPublish(parameter);

            case StateCode.GetCredit:
                return GetCredit(parameter);
        }
        return null;

    }

    private static String connectToServlet(String myUrl,JSONObject parameter ){

        String retSrc="";
        Log.v("Proxy.connectToServlet","myUrl is "+myUrl);
        HttpPost request = new HttpPost(myUrl);
// 先封装一个 JSON 对象
// 绑定到请求 Entry
        StringEntity se = null;
        HttpResponse httpResponse=null;

        Log.v("Proxy.connectToServlet","parameter is "+parameter.toString());

        try {
            request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            se = new StringEntity(parameter.toString());
            request.setEntity(se);
// 发送请求
            DefaultHttpClient dfHttpClient=new DefaultHttpClient();

            httpResponse = dfHttpClient.execute(request);
// 得到应答的字符串，这也是一个 JSON 格式保存的数据
            retSrc = EntityUtils.toString(httpResponse.getEntity());
            Log.v("Proxy.connectToServlet","接收到的http回应为：\n"+retSrc);
        }catch (UnsupportedEncodingException e) {
            Log.v("Proxy.connectToServlet","StringEntity 转化出错");
            e.printStackTrace();
        } catch (IOException e) {
            Log.v("Proxy.connectToServlet","httpResponse 转化出错");
            e.printStackTrace();
        }

        return retSrc;

    }

    private static User AccountLogin(JSONObject parameter) {
//        String jsonReceive= "{\"destination\":\"首页标题测试8page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}\n{\"destination\":\"首页标题测试9page1\",\"moy_predict\":0.0,\"order_id\":0,\"money_reward\":0.0}";
//        String[] jsonArray=jsonReceive.split("\n");
//        for(String s:jsonArray){
//            list.add(JsonUtil.getEntity(s,String.class));
//        }
        User user;
        String myUrl=url+"LoginServlet";
        String retSrc=connectToServlet(myUrl,parameter);

// 生成 JSON 对象
        try{
            JSONObject result = new JSONObject(retSrc);

            if(result!=null){
               user = JsonUtil.getEntity(result.getString("user"),User.class);

                Log.v("dz","测试用户id"+user.getUserId());
                return user;

            }else{
                return null;
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Boolean PhoneValid(JSONObject parameter){

        int code;
        String myUrl=url+"LoginServlet";

        String retSrc=connectToServlet(myUrl,parameter);

        try {

            Log.v("dz","有这个就是成功"+retSrc);
            JSONObject result = new JSONObject(retSrc);

            if(result!=null){
                code = JsonUtil.getEntity(result.getString("code"),int.class);

                Log.v("dz","测试用户手机 code"+code);
                if(code == 1)
                    return true;
                else
                    return false;

            }else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
    private static User PhoneLogin(JSONObject parameter) {

        User user=new User();
        String myUrl=url+"LoginServlet";

        String retSrc=connectToServlet(myUrl,parameter);

        try {

            Log.v("dz","有这个就是成功"+retSrc);
            JSONObject result = new JSONObject(retSrc);

            if(result!=null){
                user = JsonUtil.getEntity(result.getString("user"),User.class);
                Log.v("dz","测试用户id"+user.getUserId());
                return user;

            }else{
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<LittleOrderBean> GetLittleOrder(JSONObject parameter) {
        List<LittleOrderBean> list=new ArrayList<LittleOrderBean>();
        String myUrl=url+"OrderServlet";
        String retSrc=connectToServlet(myUrl,parameter);
        Log.v("Proxy.GetLittleOrder ","retSrc is "+retSrc);
        //http请求结束

        try {

            Log.v("dz","有这个就是成功"+retSrc);
            JSONObject result = new JSONObject(retSrc);

            if(result!=null){
                JSONArray array=result.getJSONArray("orderList");
                for(int i=0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    list.add(JsonUtil.getEntity(object.toString(),LittleOrderBean.class));
                }
                return list;
            }else{
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//
//        for (int i = 0; i < array.length(); i++) {
//            try {
//                object = array.getJSONObject(i);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            String key = object.getString(Api.KEY);
//            String value = object.getString(Api.VALUE);
//            item = new MoreInfo(key, value);
//            items.add(item);
//        }
//        LittleOrderBean lob=new LittleOrderBean();
//        lob.setOrderAddress("16宿舍楼");
//        lob.setOrderId(123456);
//        lob.setOrderItem("零食");
//        lob.setOrderReward(100);
//        lob.setStartTime("6/2");
//        lob.setState(StateCode.Order_Waiting);
//        lob.setShop("下沉广场");
//        lob.setType(StateCode.OrderType_Money);
//
//        list.add(lob);
        return list;
    }

    private static int OrderPublish(JSONObject parameter){
        int code;
        String myUrl = url+"OrderServlet";
        String retSrc = connectToServlet(myUrl,parameter);

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

    private static Credit GetCredit(JSONObject parameter){
        Credit credit;
        String myUrl = url+"InformationServlet";
        String retSrc = connectToServlet(myUrl,parameter);

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
