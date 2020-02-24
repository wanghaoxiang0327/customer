package com.pomelo.searchcustomer.visitingcard;

import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.CardSquareBean;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

import retrofit2.http.Field;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class EditVisitinCardPresenter extends BasePresenter<EditVisitingCardView> {
    public EditVisitinCardPresenter(EditVisitingCardView baseView) {
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

    public void editCard(String username, String mobile, String companyname,
                         String industrycategory, String position, String companyAddress,
                         String email, String department, String telwork,
                         String wechat, String companyurl, String cardstatus,
                         String cardhyid, String cardplacecode, String cardplace) {
        addDisposite(apiService.editMyCard(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), username, mobile, companyname, industrycategory, position, companyAddress
                , email, department, telwork, wechat, companyurl, cardstatus, cardhyid, cardplacecode, cardplace), new BaseObserver<BaseModel<CardSquareBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<CardSquareBean> o) {
                    baseView.editSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
