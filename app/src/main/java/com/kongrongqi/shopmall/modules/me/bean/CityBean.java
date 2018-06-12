package com.kongrongqi.shopmall.modules.me.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.kongrongqi.shopmall.base.BaseBean;

import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 16:03
 * 作者:penny
 */
public class CityBean extends BaseBean implements IPickerViewData{
    /**
     * name : 城市
     * area : ["东城区","西城区","崇文区","昌平区"]
     */

    private String name;
    private List<String> area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
