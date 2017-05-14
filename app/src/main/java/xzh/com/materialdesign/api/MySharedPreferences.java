package xzh.com.materialdesign.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dz on 2017/5/14.
 */

public class MySharedPreferences {
    private String preferenceName;
    private Context context;

    public MySharedPreferences (String preferenceName, Context context ){
        this.preferenceName=preferenceName;
        this.context=context;
    }
    public void commit(String key,String value){
        SharedPreferences mySharedPreferences =context.getSharedPreferences(preferenceName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName,
                Activity.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;

    }

    public void clear(){
        SharedPreferences mySharedPreferences =context.getSharedPreferences(preferenceName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.clear().commit();
    }
}
