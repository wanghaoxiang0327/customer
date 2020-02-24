package com.pomelo.searchcustomer.visitingcard;


import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.CardSquareBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface CardSquareFragmentView extends BaseView {
    void getCardSquareSuccess(CardSquareBean cardSquareBean);
}
