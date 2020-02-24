package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.contract.Contanst;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class FeedBackPresenter extends BasePresenter<FeedBackView> {
    public FeedBackPresenter(FeedBackView baseView) {
        super(baseView);
    }

    public void feedbackSubmit(String name, String content, String mobile) {
        addDisposite(apiService.feedBackSubmit(Contanst.TOKEN, Contanst.VERSION, name, content, mobile), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                if (o != null && o.data != null) {
                    baseView.subitSuccess();
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
