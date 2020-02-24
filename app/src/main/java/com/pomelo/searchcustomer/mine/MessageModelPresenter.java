package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MessageModelBean;
import com.pomelo.searchcustomer.contract.Contanst;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class MessageModelPresenter extends BasePresenter<MessageModelView> {
    public MessageModelPresenter(MessageModelView baseView) {
        super(baseView);
    }

    public void getModeleList() {
        addDisposite(apiService.getMessageList(Contanst.TOKEN, Contanst.VERSION), new BaseObserver<BaseModel<MessageModelBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MessageModelBean> o) {
                if (o != null && o.data != null) {
                    baseView.getMessageListSuccess(o.data);
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
