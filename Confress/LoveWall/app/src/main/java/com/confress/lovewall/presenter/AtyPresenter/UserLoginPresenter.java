package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.content.Intent;

import com.confress.lovewall.Activity.FindPsdActivity;
import com.confress.lovewall.Activity.HomeActivity;
import com.confress.lovewall.Activity.RegisterActivity;
import com.confress.lovewall.biz.IMyBmobInstallationBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.IListener.OnLoginListener;
import com.confress.lovewall.biz.MyBmobInstallationBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.AtyView.IUserLoginView;

/**
 * Created by admin on 2016/3/7.
 */
public class UserLoginPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Context context;

    public UserLoginPresenter(IUserLoginView userLoginView,Context context) {
        this.userBiz = new UserBiz();
        this.userLoginView = userLoginView;
        this.context= context;
    }

    public void login() {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void OnSuccess(User user) {
                userLoginView.hideLoading();

                userLoginView.toHomeActivity(user);
            }

            @Override
            public void OnFailed() {
                userLoginView.hideLoading();
                userLoginView.showFailedError();

            }
        },userLoginView.getContext());
    }

    public  void Third_login(final String uid, final String nickname, final String icon){
        userBiz.third_login(uid, nickname, icon, new OnLoginListener() {
            @Override
            public void OnSuccess(User user) {
               //跳转页面去设置页面去。。。说明是第一次登录
                userBiz.login(uid, uid, new OnLoginListener() {
                    @Override
                    public void OnSuccess(User user) {
                       userLoginView.toSettingAty(nickname,icon);
                    }

                    @Override
                    public void OnFailed() {
                        userLoginView.showFailedError();
                    }
                }, userLoginView.getContext());
            }

            @Override
            public void OnFailed() {
                //跳转页面去设置页面去。。。说明不是第一次登录直接登录跳转
                userBiz.login(uid, uid, new OnLoginListener() {
                    @Override
                    public void OnSuccess(User user) {
                        userLoginView.toHomeActivity(user);
                    }

                    @Override
                    public void OnFailed() {
                        userLoginView.showFailedError();
                    }
                }, userLoginView.getContext());
            }
        },context);
    }

    //跳转到主的页面
    public  void  toHomeActivty(){
        Intent intent = new Intent(context
                , HomeActivity.class);
        context.startActivity(intent);
    }

    //跳转到注册的页面
    public  void  toRegisterActivity(){
        Intent intent = new Intent(context
                , RegisterActivity.class);
        context.startActivity(intent);
    }

    public void  toRememberPsdActivity(){
        Intent intent = new Intent(context
                , FindPsdActivity.class);
        context.startActivity(intent);
    }

}
