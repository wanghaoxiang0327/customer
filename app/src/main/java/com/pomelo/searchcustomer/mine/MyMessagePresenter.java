package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MyCollectionInfoBean;
import com.pomelo.searchcustomer.bean.MyMessageBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class MyMessagePresenter extends BasePresenter<MyMessageView> {
    public MyMessagePresenter(MyMessageView baseView) {
        super(baseView);
    }

    public void getMyMessage(String page) {
        addDisposite(apiService.getMyMessage(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), page, "10"), new BaseObserver<BaseModel<MyMessageBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MyMessageBean> o) {
                if (o != null && o.data != null) {
                    baseView.getMyMessageSuccess(o.data);
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
