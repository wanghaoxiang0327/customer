package com.pomelo.searchcustomer.visitingcard;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.VistingCardBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface VisitingCardListView extends BaseView {
    void getVisitingCardListSuccess(VistingCardBean vistingCardBean);
}
