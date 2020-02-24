package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.contract.Contanst;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class UploadApplyPresenter extends BasePresenter<UploadApplyView> {
    public UploadApplyPresenter(UploadApplyView baseView) {
        super(baseView);
    }


    public void addMessageModel(String content) {
        addDisposite(apiService.addMessageModel(Contanst.TOKEN, Contanst.VERSION, content), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.addModelSuccess(o.info);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
