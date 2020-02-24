package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.DisclaimerBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface DisclaimerView extends BaseView {
    void getAgreementSuccess(DisclaimerBean disclaimerBean);
}
