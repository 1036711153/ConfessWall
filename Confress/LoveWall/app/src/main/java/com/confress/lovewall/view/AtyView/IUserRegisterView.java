package com.confress.lovewall.view.AtyView;

import android.content.Context;

/**
 * Created by admin on 2016/3/7.
 */
public interface IUserRegisterView {
    //注册成功时候提示
    void Success();
    //注册失败
    void Failed();
    void showLoading();
    void hideLoading();
    //注册成功back这个Aty
    void FinishAty();
    String getUsername();
    String getPsd();
    String getConPsd();
    String getEmail();
    Context getContext();
    void ErrorOfUsnorPsdorEmail();
    void ErrorOfConfingerPsd();

}
