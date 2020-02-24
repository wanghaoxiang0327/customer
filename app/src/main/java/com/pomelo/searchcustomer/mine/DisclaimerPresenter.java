package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.DisclaimerBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.ToastUtils;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class DisclaimerPresenter extends BasePresenter<DisclaimerView> {
    public DisclaimerPresenter(DisclaimerView baseView) {
        super(baseView);
    }

    public void getAgreement() {
        addDisposite(apiService.getDisclaimerAgreemnt(Contanst.TOKEN, Contanst.VERSION), new BaseObserver<BaseModel<DisclaimerBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<DisclaimerBean> o) {
                if (o != null && o.data != null) {
                    baseView.getAgreementSuccess(o.data);
                } else {
                    onError(o.info);
                }
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
