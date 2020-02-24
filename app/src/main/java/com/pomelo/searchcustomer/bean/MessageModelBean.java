package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-11.
 */

public class MessageModelBean implements Serializable {
    public List<MessageModel> datum;

    public class MessageModel implements Serializable {
        public String id;
        public String content;
        public String status;
        public String mid;
        public String token;
        public String reason;
        public String statusInfo;
    }
}
