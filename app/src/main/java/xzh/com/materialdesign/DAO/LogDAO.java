package xzh.com.materialdesign.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import xzh.com.materialdesign.DAO.DBManager;
import xzh.com.materialdesign.model.*;

/**
 * Created by Towyer_pic on 2017/5/9.
 */

public class LogDAO implements IDAO {

    private DBManager sql;
    private int recordNum=0;
    private int pageNum=0;

    public LogDAO(){
        sql = DBManager.createInstance();
        sql.connectDB();
    }

    public boolean AddEntity(IEntity entity) {

        boolean succ=true;
        User user=(User)entity;

        // 获取Sql查询语句
        try{
            String sqlGetNum="select COUNT(user_id) from User";
            ResultSet res;
            res = sql.executeQuery(sqlGetNum);
            if(res.next()){
                this.recordNum=res.getInt(1);
            }
            String Sql = "insert into student values("+(recordNum+1)+
                                                       ",'"+ user.getName()+
                                                       "',"+user.getSex()+
                                                       ","+user.getAge()+
                                                       ",'"+user.getNickname()+
                                                       "','"+user.getPhoneNum()+
                                                       "','"+user.getEmail()+
                                                       "','"+user.getSchool()+
                                                       "','"+user.getPassword()+"') ";
            // 操作DB对象
            int rs = sql.executeUpdate(Sql);
            if (rs != 0) {
                sql.closeDB();
                succ = true;
            }
            sql.closeDB();
            succ = false;

        }catch (Exception e) {
            succ=false;
            e.printStackTrace();
        }
        return succ;
    }
    /*
    public Boolean register(String username, String password) {

        // 获取Sql查询语句
        String regSql = "insert into student values('"+ username+ "','"+ password+ "') ";

        // 获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        int ret = sql.executeUpdate(regSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();

        return false;
    }
*/
}
