package xzh.com.materialdesign.proxy;

import org.json.JSONObject;

/**
 * Created by dz on 2017/6/14.
 */

public class Command {

    AccountLogin _AccountLogin;
    PhoneValid _PhoneValid;
    PhoneLogin _PhoneLogin;
    Register _Register;
    GetLittleOrder _GetLittleOrder;
    OrderPublish _OrderPublish;
    GetCredit _GetCredit;
    PersonalInfo _PersonalInfo;
    OrderInfo _OrderInfo;
    ContactUs _ContactUs;
    OrderState _OrderState;
    OrderReceive _OrderReceive;
    OrderUpdate _OrderUpdate;
    OrderFinish _OrderFinish;
    OrderReview _OrderReview;
    GetReview _GetReview;
    OrderDrawback _OrderDrawback;

    public Command(){
        _AccountLogin=new AccountLogin();
        _PhoneValid =new PhoneValid();
        _PhoneLogin=new PhoneLogin();
        _Register=new Register();
        _GetLittleOrder=new GetLittleOrder();
        _OrderPublish=new OrderPublish();
        _GetCredit=new GetCredit();
        _PersonalInfo=new PersonalInfo();
        _OrderInfo=new OrderInfo();
        _ContactUs=new ContactUs();
        _OrderState=new OrderState();
        _OrderReceive=new OrderReceive();
        _OrderUpdate=new OrderUpdate();
        _OrderFinish=new OrderFinish();
        _OrderReview=new OrderReview();
        _GetReview=new GetReview();
        _OrderDrawback=new OrderDrawback();

    }

    public Object accountLogin(JSONObject parameter){
        return _AccountLogin.getWebData(parameter);
    }
    public Object phoneValid(JSONObject parameter){
        return _PhoneValid.getWebData(parameter);
    }
    public Object phoneLogin(JSONObject parameter){
        return _PhoneLogin.getWebData(parameter);
    }
    public Object register(JSONObject parameter){
        return _Register.getWebData(parameter);
    }
    public Object getLittleOrder(JSONObject parameter){
        return _GetLittleOrder.getWebData(parameter);
    }
    public Object orderPublish(JSONObject parameter){
        return _OrderPublish.getWebData(parameter);
    }
    public Object getCredit(JSONObject parameter){
        return _GetCredit.getWebData(parameter);
    }
    public Object personalInfo(JSONObject parameter){
        return _PersonalInfo.getWebData(parameter);
    }
    public Object orderInfo(JSONObject parameter){
        return _OrderInfo.getWebData(parameter);
    }
    public Object contactUs(JSONObject parameter){
        return _ContactUs.getWebData(parameter);
    }
    public Object orderState(JSONObject parameter){
        return _OrderState.getWebData(parameter);
    }
    public Object orderReceive(JSONObject parameter){
        return _OrderReceive.getWebData(parameter);
    }
    public Object orderUpdate(JSONObject parameter){
        return _OrderUpdate.getWebData(parameter);
    }
    public Object orderFinish(JSONObject parameter){
        return _OrderFinish.getWebData(parameter);
    }
    public Object orderReview(JSONObject parameter){
        return _OrderReview.getWebData(parameter);
    }
    public Object getReview(JSONObject parameter){
        return _GetReview.getWebData(parameter);
    }
    public Object orderDrawback(JSONObject parameter){
        return _OrderDrawback.getWebData(parameter);
    }

}
