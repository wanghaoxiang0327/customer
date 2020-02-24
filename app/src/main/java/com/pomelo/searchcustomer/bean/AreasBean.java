package com.pomelo.searchcustomer.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

public class AreasBean implements IPickerViewData {
    public int code;
    public String name;
    public List<ChildrenBeanX> sub;

    public static class ChildrenBeanX {
        public int code;
        public String name;
        public List<ChildrenBean> sub;

        public static class ChildrenBean {
            public int code;
            public String name;
        }
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

}
