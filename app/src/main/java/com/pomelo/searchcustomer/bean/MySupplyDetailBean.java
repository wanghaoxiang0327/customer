package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-15.
 */

public class MySupplyDetailBean implements Serializable {
    public String id;
    public String title;
    public String content;
    public String createtime;
    public String click;
    public String mobile;
    public List<String> images;
}
