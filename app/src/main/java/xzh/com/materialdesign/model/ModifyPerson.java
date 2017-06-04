package xzh.com.materialdesign.model;

/**
 * Created by botan on 2017/6/1.
 */
import java.io.Serializable;

public class ModifyPerson implements IEntity{
    private String modify_name;
    private String modify_phone;

    public ModifyPerson(String name,String phone) {
        this.modify_name = name;
        this.modify_phone = phone;
    }

    public String getModifyname() {
        return modify_name;
    }

    public void setModifyname(String modify_name) {
        this.modify_name = modify_name;
    }

    public String getModifyphone() {
        return modify_phone;
    }

    public void setModifyphone(String modify_phone) {
        this.modify_phone = modify_phone;
    }


}
