package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class CardSquareBean implements Serializable {
    public List<Card> list;
    public int count;

    public class Card implements Serializable {
        public String id;
        public String nick_name;
        public String image;
        public String card_browse;
        public String card_collect;
        public String card_hy_id;
        public String card_reliable;
        public String card_place;
        public String card_place_code;
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
