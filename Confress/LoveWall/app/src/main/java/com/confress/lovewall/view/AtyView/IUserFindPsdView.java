package com.confress.lovewall.view.AtyView;

/**
 * Created by admin on 2016/3/7.
 */
public interface IUserFindPsdView {
    void showLoading();

    void hideLoading();

    String getEmail();

    void Sussccess();

    void Failed();
    void EmptyOfEmail();
}
