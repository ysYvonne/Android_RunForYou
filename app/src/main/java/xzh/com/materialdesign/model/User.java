package xzh.com.materialdesign.model;

import android.os.Parcel;
import android.util.Log;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class User implements IEntity {
    private int user_id = -1;//用户id
    private String name;//真实姓名
    private int sex;//姓名，记1为男，2为女，0为未设置
    private int age;//年龄，记-1为未设置
    private String nickname;//昵称
    private String phoneNum;//电话
    private String email;//邮箱
    private String school;//学校
    private String password;//密码

    public User(){
        sex = 0;
        age = -1;
        password = "未设置";
    }

    public void setUserId(int userId){
        user_id = userId;
    }
    public void setName(String name1){
        if(!name1.isEmpty()){
            name = name1;
            nickname = name.substring(0,1) + "同学";
        }

    }
    public void setSex(int sex1){
        sex = sex1;
    }
    public void setAge(int age1){
        age = age1;
    }
    public void setNickname(String nickname1){
        nickname = nickname1;
    }
    public void setPhoneNum(String phone){
        phoneNum = phone;
    }
    public void setEmail(String email1){
        email = email1;
    }
    public void setSchool(String school1){
        school = school1;
    }
    public void setPassword(String password1){
        password = password1;
    }

    public int getUserId(){
        return user_id;
    }
    public String getName(){
        return name;
    }
    public int getSex(){
        return sex ;
    }
    public int getAge( ){
        return age;
    }
    public String getNickname( ){
        return nickname ;
    }
    public String getPhoneNum(){
        return phoneNum ;
    }
    public String getEmail(){
        return email;
    }
    public String getSchool(){
        return school;
    }
    public String getPassword(){
        return password ;
    }


}
