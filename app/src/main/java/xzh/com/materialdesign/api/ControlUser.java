package xzh.com.materialdesign.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import xzh.com.materialdesign.model.User;
import xzh.com.materialdesign.proxy.StateCode;

/**
 * Created by dz on 2017/5/31.
 */

public class ControlUser {
    static final String preferenceName="user_info";
    public static void addUser(User user,Context context){

        MySharedPreferences msp = new MySharedPreferences(preferenceName, context);
        msp.commit("userId", String.valueOf(user.getUserId()));

        msp.commit("name", String.valueOf(user.getName()));
        msp.commit("sex", String.valueOf(user.getSex()));
        msp.commit("age", String.valueOf(user.getAge()));
        msp.commit("nickName", String.valueOf(user.getNickname()));
        msp.commit("phoneNum", String.valueOf(user.getPhoneNum()));
        msp.commit("email", String.valueOf(user.getEmail()));
        msp.commit("school", String.valueOf(user.getSchool()));
        msp.commit("password", String.valueOf(user.getPassword()));
    }

    public static User getUser(Context context){
        User user=new User();
        MySharedPreferences msp = new MySharedPreferences(preferenceName, context);

        if(msp.getValue("userId").isEmpty())return null;

        user.setUserId(Integer.valueOf(msp.getValue("userId")));

        if(!msp.getValue("age").isEmpty())
        user.setAge(Integer.valueOf(msp.getValue("age")));
        if(!msp.getValue("email").isEmpty())
        user.setEmail(msp.getValue("email"));
        if(!msp.getValue("name").isEmpty())
        user.setName(msp.getValue("name"));
        if(!msp.getValue("nickName").isEmpty())
        user.setNickname(msp.getValue("nickName"));
        if(!msp.getValue("password").isEmpty())
        user.setPassword(msp.getValue("password"));
        if(!msp.getValue("phoneNum").isEmpty())
        user.setPhoneNum(msp.getValue("phoneNum"));
        if(!msp.getValue("school").isEmpty())
        user.setSchool(msp.getValue("school"));
        if(!msp.getValue("sex").isEmpty())
        user.setSex(Integer.valueOf(msp.getValue("sex")));
        return user;
    }

    public static void clearUser(Context context){
        new MySharedPreferences(preferenceName,context).clear();
    }

    public static void ChangeUser(String item,String value,Context context){

        Intent mIntent = new Intent(item);
        mIntent.putExtra(item,value);

        //发送广播
        context.sendBroadcast(mIntent);
        MySharedPreferences msp = new MySharedPreferences(item, context);
        msp.commit(item,value);
    }
}
