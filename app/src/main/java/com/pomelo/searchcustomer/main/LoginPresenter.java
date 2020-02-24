package com.pomelo.searchcustomer.main;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PhoneUtils;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }

    public void login(String password, String mobile, String deviceIndex) {
        addDisposite(apiService.login(Contanst.TOKEN, Contanst.VERSION, password, mobile, PhoneUtils.getSystemModel(), PhoneUtils.getSystemVersion(), deviceIndex), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                PreferencesUtil.putBoolean("isLogin", true);
                PreferencesUtil.putString("utoken", o.utoken);
                baseView.loginSuccess();
            }

            @Override
            public void onError(String msg) {
                PreferencesUtil.putBoolean("isLogin", false);
                PreferencesUtil.putString("utoken", "");
                baseView.showError(msg);
            }
        });
    }
}
