package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.text.TextUtils;

import com.confress.lovewall.biz.IListener.OnRememberPsdListener;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.view.AtyView.IUserFindPsdView;

/**
 * Created by admin on 2016/3/7.
 */
public class UserForgetPsdPresenter {
    private IUserBiz userBiz;
    private IUserFindPsdView userFindPsdView;
    private Context context;

    public UserForgetPsdPresenter(IUserFindPsdView userFindPsdView, Context context) {
        this.userBiz = new UserBiz();
        this.userFindPsdView = userFindPsdView;
        this.context = context;
    }

    public void RememberPsd() {
        if (TextUtils.isEmpty(userFindPsdView.getEmail())){
            userFindPsdView.EmptyOfEmail();
            return;
        }
        userFindPsdView.showLoading();
        userBiz.rememberpds(userFindPsdView.getEmail(), new OnRememberPsdListener() {
            @Override
            public void OnSusscess() {
                userFindPsdView.hideLoading();
                userFindPsdView.Sussccess();
            }

            @Override
            public void OnFailed() {
                userFindPsdView.hideLoading();
                userFindPsdView.Failed();
            }
        }, context);

    }

}
