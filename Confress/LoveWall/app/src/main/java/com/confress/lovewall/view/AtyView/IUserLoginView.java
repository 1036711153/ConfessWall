package com.confress.lovewall.view.AtyView;

import android.content.Context;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/7.
 */
public interface IUserLoginView {
    String getUserName();
    String getPassword();
    void showLoading();
    void hideLoading();
    void toHomeActivity(User user);
    void showFailedError();
    Context getContext();
    void ErrorOfUsnAndPsd();
    void toSettingAty(String nickname,String icon);

}
