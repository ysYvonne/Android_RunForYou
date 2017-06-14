package xzh.com.materialdesign.http;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by dz on 2017/6/13.
 */

public class HttpHelper {
    public static String connectToServlet(String myUrl,JSONObject parameter ){

        String retSrc="";
        Log.v("Proxy.connectToServlet","myUrl is "+myUrl);
        HttpPost request = new HttpPost(myUrl);
// 先封装一个 JSON 对象
// 绑定到请求 Entry
        StringEntity se = null;
        HttpResponse httpResponse=null;

        Log.v("Proxy.connectToServlet","parameter is "+parameter.toString());

        try {
            se = new StringEntity(parameter.toString(), HTTP.UTF_8);
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
}
