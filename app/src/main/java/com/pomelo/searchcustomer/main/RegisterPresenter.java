package com.pomelo.searchcustomer.main;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PhoneUtils;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {
    public RegisterPresenter(RegisterView baseView) {
        super(baseView);
    }

    public void getMessageCode(String phoneNumber) {
        addDisposite(apiService.getMessageCode(Contanst.TOKEN, Contanst.VERSION, phoneNumber), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.getMessageCodeSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    public void register(String password, String mobile, String truepassword, String code, String deviceIndex) {
        addDisposite(apiService.register(Contanst.TOKEN, Contanst.VERSION, password, mobile, truepassword, code, PhoneUtils.getSystemModel(), PhoneUtils.getSystemVersion(), deviceIndex), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.registerSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
