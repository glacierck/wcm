package com.kongrongqi.shopmall.modules.model;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

//import com.kongrongqi.shopmall.BR;
//import com.kongrongqi.shopmall.BR;
import com.kongrongqi.shopmall.base.BaseBean;

/**
 * 创建日期：2017/5/19 0019 on 11:50
 * 作者:penny
 */
public class ToolBarModel extends BaseBean{

    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    private String title;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
//        notifyChange(BR.title);
    }

    private void notifyChange(int propertyId) {

        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(
                this,
                propertyId
        );
    }

    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {

        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);

    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {

        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }

    @Override
    public String toString() {
        return "ToolBarModel{" +
                "title='" + title + '\'' +
                '}';
    }
}
