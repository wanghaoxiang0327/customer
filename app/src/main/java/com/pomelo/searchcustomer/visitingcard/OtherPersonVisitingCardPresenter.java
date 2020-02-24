package com.pomelo.searchcustomer.visitingcard;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class OtherPersonVisitingCardPresenter extends BasePresenter<OtherPersonVisitingCardView> {
    public OtherPersonVisitingCardPresenter(OtherPersonVisitingCardView baseView) {
        super(baseView);
    }

    public void getOtherPersonDetail(String id) {
        addDisposite(apiService.getOtherPersonVisitingCard(Contanst.TOKEN, Contanst.VERSION, id), new BaseObserver<BaseModel<MyVisitinCardBean>>(baseView) {
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

    public void collectionCard(String id) {
        addDisposite(apiService.collectionCard(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), id), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.collectionCardSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    public void cancleCollectionCard(String id) {
        addDisposite(apiService.cancleCollectionCard(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), id), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.cancalcollectionCardSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    public void careReliableCard(String id) {
        addDisposite(apiService.careReliableCard(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), id), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.careReliableCardSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
