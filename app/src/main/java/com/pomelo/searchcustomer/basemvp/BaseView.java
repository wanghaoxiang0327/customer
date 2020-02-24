package com.pomelo.searchcustomer.basemvp;

/**
 * Created by wanghaoxiang on 2019-07-20.
 * 定义接口常用的接口
 */

public interface BaseView {
    /**
     * 显示进度框
     */
    void showLoading();

    /**
     * 隐藏进度框
     */
    void hideLoading();

    /**
     * 显示错误信息
     *
     * @param msg 错误信息
     */
    void showError(String msg);
}
