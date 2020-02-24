package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class VistingCardBean implements Serializable {
    public List<VistingCard> list;
    public int count;

    public class VistingCard implements Serializable {
        public String createtime;
        public String card_id;
        public String image;
        public CardInfo card_info;

    }

    public class CardInfo implements Serializable {
        public String name;
        public String industry_category;
        public String position;
        public String company_name;
        public String mobile;
        public String email;
        public String company_address;
        public String wechat;
        public String tel_work;
        public String department;
        public String company_url;
    }
}
