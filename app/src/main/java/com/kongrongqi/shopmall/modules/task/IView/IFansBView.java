package com.kongrongqi.shopmall.modules.task.IView;

import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.model.FansBListModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;

/**
 * 创建日期：2017/5/26 0026 on 17:16
 * 作者:penny
 */
public interface IFansBView extends IUI{
    void gotoCallTxt(UserServiceParam param);
    void gotoUseService(UserServiceParam param);
}
