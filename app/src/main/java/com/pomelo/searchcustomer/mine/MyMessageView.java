package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.MessageModelBean;
import com.pomelo.searchcustomer.bean.MyMessageBean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface MyMessageView extends BaseView {
    void getMyMessageSuccess(MyMessageBean myMessageBean);


}
