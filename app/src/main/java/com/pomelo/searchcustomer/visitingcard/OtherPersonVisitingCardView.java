package com.pomelo.searchcustomer.visitingcard;


import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.CardSquareBean;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface OtherPersonVisitingCardView extends BaseView {
    void getMyVisitingCardInfo(MyVisitinCardBean myVisitinCardBean);

    void collectionCardSuccess();

    void cancalcollectionCardSuccess();

    void careReliableCardSuccess();
}
