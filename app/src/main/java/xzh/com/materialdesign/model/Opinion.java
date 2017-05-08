package xzh.com.materialdesign.model;

/**
 * Created by Towyer_pic on 2017/4/30.
 */

public class Opinion implements IEntity {
    private int opinion_id;//意见id
    private int user_id;//用户id
    private String content;//意见内容
    private String opinion_time;//评价时间

    public void setOpinionId(int opinionId){
        opinion_id = opinionId;
    }
    public void setUserId(int userId) {
        user_id = userId;
    }
    public void setContent(String con) {
        content = con;
    }
    public void setOpinionTime(String time) {
        opinion_time = time;
    }

    public int getOpinionId() {
        return opinion_id;
    }
    public int getUserId() {
        return user_id;
    }
    public String getContent() {
        return content;
    }
    public String getOpinionTime() {
        return opinion_time;
    }
}
