package com.pomelo.searchcustomer.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-14.
 */

public class IndustryBean implements IPickerViewData {
    public String id;
    public String pid;
    public String name;
    public String type;
    public List<ChildrenBeanX> sub;

    public static class ChildrenBeanX {
        public String id;
        public String pid;
        public String name;
        public String type;
        public List<ChildrenBean> sub;

        public static class ChildrenBean {
            public String id;
            public String pid;
            public String name;
            public String type;
        }
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
