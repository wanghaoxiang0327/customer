package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class SullierBean implements Serializable {
    public List<Sullier> library;
    public int count;
    public class Sullier implements Serializable {
        public String id;
        public String title;
        public String content;
        public String createtime;
        public String click;
        public String image;
        public String uid;
        public String nick_name;
        public String card_place;
        public String card_place_code;
        public CardInfo card_info;
        public List<String> images;
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
