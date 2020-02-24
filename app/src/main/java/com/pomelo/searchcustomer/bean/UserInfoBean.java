package com.pomelo.searchcustomer.bean;

import java.io.Serializable;

/**
 * Created by wanghaoxiang on 2020-01-12.
 */

public class UserInfoBean implements Serializable {
    public String id;
    public String user_id;
    public String uid;
    public String nick_name;
    public String image;
    public String ali_user_id;
    public String openid;
    public String baidu_openid;
    public String mobile;
    public String usenum;
    public String card_info;
    public String if_mobile;
    public String is_merchant;
    public Merchant merchant;

    public class Merchant implements Serializable {
        public String mid;
        public String status;
        public String expiretime;
        public String source_times;
        public String supply_times;
        public String need_times;
        public String mobile;
        public String message_num;
        public String issue_num;
    }
}
