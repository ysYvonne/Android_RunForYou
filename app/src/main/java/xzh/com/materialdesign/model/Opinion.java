package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Opinion implements IEntity {
    private int opinionId;//意见id
    private int userId;//用户id
    private String content;//意见内容
    private String opinionTime;//评价时间

    public void setOpinionId(int opinionId){
        opinionId = opinionId;
    }
    public void setUserId(int userId) {
        userId = userId;
    }
    public void setContent(String con) {
        content = con;
    }
    public void setOpinionTime(String time) {
        opinionTime = time;
    }

    public int getOpinionId() {
        return opinionId;
    }
    public int getUserId() {
        return userId;
    }
    public String getContent() {
        return content;
    }
    public String getOpinionTime() {
        return opinionTime;
    }
}
