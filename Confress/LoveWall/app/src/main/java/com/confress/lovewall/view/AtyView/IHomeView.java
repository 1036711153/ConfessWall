package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/7.
 */
public interface IHomeView {
//    //显示progress
//    void showLoading();
//
//    //隐藏progress
//    void hideLoading();
//
//    //加载数据成功
//    void Sussccess();
//
//    //加载数据失败
//    void Failed();

    void click_indicator_one();

    void click_indicator_two();

    void click_indicator_three();

    void click_indicator_four();

    void click_indicator_center();

    void click_pop(int i);

    void showPop();
    void hidePop();
    User getCurrentUser();
    void NeedLogin();

}
