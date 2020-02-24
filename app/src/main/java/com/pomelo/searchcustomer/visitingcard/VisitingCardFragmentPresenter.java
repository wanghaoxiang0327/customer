package com.pomelo.searchcustomer.visitingcard;

import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class VisitingCardFragmentPresenter extends BasePresenter<VisitingCardFragmentView> {
    public VisitingCardFragmentPresenter(VisitingCardFragmentView baseView) {
        super(baseView);
    }


    public void getMyVisitingCard() {
        addDisposite(apiService.getMyVisitingCard(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<MyVisitinCardBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MyVisitinCardBean> o) {
                if (o != null && o.data != null) {
                    baseView.getMyVisitingCardInfo(o.data);
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
