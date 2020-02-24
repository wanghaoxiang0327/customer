package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-12.
 */

public class HomeIndexBean implements Serializable {
    public List<BannerBean> banner;
    public List<Source> source;
    public List<HotShop> hotShop;

    public class BannerBean implements Serializable {
        public String minimanage_path_id;
        public String image;
        public String link;
        public String id;
    }

    public class Source implements Serializable {
        public String id;
        public String image;
        public String name;
        public String versions;
        public String weigh;
        public String status;
    }

    public class HotShop implements Serializable {
        public String id;
        public String store_images;
        public String store_name;
        public String mobile;
        public String store_desc;
        public String uid;
    }
}
