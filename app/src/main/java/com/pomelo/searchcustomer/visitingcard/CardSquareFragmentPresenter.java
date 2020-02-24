package com.pomelo.searchcustomer.visitingcard;

import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.CardSquareBean;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class CardSquareFragmentPresenter extends BasePresenter<CardSquareFragmentView> {
    public CardSquareFragmentPresenter(CardSquareFragmentView baseView) {
        super(baseView);
    }

    public void cardSquare(String page, String code, String cardhyId, String keyWord) {
        addDisposite(apiService.getCardSquare(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), page, code, keyWord, cardhyId), new BaseObserver<BaseModel<CardSquareBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<CardSquareBean> o) {
                if (o != null && o.data != null) {
                    baseView.getCardSquareSuccess(o.data);
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
