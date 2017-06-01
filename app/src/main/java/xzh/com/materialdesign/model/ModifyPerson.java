package xzh.com.materialdesign.model;

/**
 * Created by botan on 2017/6/1.
 */
import java.io.Serializable;

public class ModifyPerson implements Serializable{
    private String modify_name;
    private String modify_phone;

    public ModifyPerson(String name,String phone) {
        this.modify_name = name;
        this.modify_phone = phone;
    }

    public String getModify_name() {
        return modify_name;
    }

    public void setModify_name(String modify_name) {
        this.modify_name = modify_name;
    }

    public String getModify_phone() {
        return modify_phone;
    }

    public void setModify_phone(String modify_phone) {
        this.modify_phone = modify_phone;
    }


}
