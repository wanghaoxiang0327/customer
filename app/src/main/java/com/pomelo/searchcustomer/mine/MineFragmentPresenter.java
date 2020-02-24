package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.UserInfoBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PhoneUtils;
import com.pomelo.searchcustomer.utils.PreferencesUtil;
import com.pomelo.searchcustomer.visitingcard.VisitingCardFragmentView;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class MineFragmentPresenter extends BasePresenter<MineFragmentView> {
    public MineFragmentPresenter(MineFragmentView baseView) {
        super(baseView);
    }

    public void getUserInfo() {
        addDisposite(apiService.getUserInfo(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<UserInfoBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<UserInfoBean> o) {
                if (o != null && o.data != null) {
                    baseView.getUserInfoSuccess(o.data);
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
