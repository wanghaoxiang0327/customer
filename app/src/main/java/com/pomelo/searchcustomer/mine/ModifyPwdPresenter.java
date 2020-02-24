package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PhoneUtils;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class ModifyPwdPresenter extends BasePresenter<ModifyPwdView> {
    public ModifyPwdPresenter(ModifyPwdView baseView) {
        super(baseView);
    }

    public void modifyPwd(String password, String truePassword, String code, String mobile, String deviceIndex) {
        addDisposite(apiService.modifypwd(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), password, truePassword, code, mobile, PhoneUtils.getSystemModel(), PhoneUtils.getSystemVersion(), deviceIndex), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.modifySuccess();
            }

            @Override
            public void onError(String msg) {
                PreferencesUtil.putBoolean("isLogin", false);
                PreferencesUtil.putString("utoken", "");
                baseView.showError(msg);
            }
        });
    }

    public void getMessageCode(String phoneNumber) {
        addDisposite(apiService.getModifyPwdMessageCode(Contanst.TOKEN, Contanst.VERSION, phoneNumber), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.sendSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
