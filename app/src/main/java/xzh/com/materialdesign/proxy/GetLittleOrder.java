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
    public Object getWebData(String url, JSONObject parameter) {
        List<LittleOrderBean> list=new ArrayList<LittleOrderBean>();
        String myUrl=url+"OrderServlet";
        String retSrc= HttpHelper.connectToServlet(myUrl,parameter);
        Log.v("Proxy.GetLittleOrder ","retSrc is "+retSrc);
        //http请求结束

        try {

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
        return list;
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



    }
}
