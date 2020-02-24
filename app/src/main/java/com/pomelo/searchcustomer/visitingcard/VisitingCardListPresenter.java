package com.pomelo.searchcustomer.visitingcard;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.bean.VistingCardBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class VisitingCardListPresenter extends BasePresenter<VisitingCardListView> {
    public VisitingCardListPresenter(VisitingCardListView baseView) {
        super(baseView);
    }

    public void getVistingCardList(String keyword, String page) {
        addDisposite(apiService.getVisitinCardList(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), keyword, page, "10"), new BaseObserver<BaseModel<VistingCardBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<VistingCardBean> o) {
                if (o != null && o.data != null) {
                    baseView.getVisitingCardListSuccess(o.data);
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
