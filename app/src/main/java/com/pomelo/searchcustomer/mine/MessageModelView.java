package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.MessageModelBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface MessageModelView extends BaseView {
    void getMessageListSuccess(MessageModelBean messageModelBeans);
}
