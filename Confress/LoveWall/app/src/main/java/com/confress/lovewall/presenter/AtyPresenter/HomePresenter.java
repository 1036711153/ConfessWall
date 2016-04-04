package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.confress.lovewall.Activity.AnnomousActivity;
import com.confress.lovewall.Activity.CharactersActivity;
import com.confress.lovewall.Activity.HomeActivity;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.biz.IListener.OnBindListener;
import com.confress.lovewall.biz.IListener.OnLoginListener;
import com.confress.lovewall.biz.IListener.OnUpdateGspListener;
import com.confress.lovewall.biz.IMyBmobInstallationBiz;
import com.confress.lovewall.biz.MyBmobInstallationBiz;
import com.confress.lovewall.model.LocationInfo;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.CustomView.ChangeColorIconWithText;
import com.confress.lovewall.view.CustomView.SmallBang;
import com.confress.lovewall.view.CustomView.SmallBangListener;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.view.AtyView.IHomeView;

import java.util.List;

/**
 * Created by admin on 2016/3/7.
 */
public class HomePresenter {
    private IUserBiz userBiz;
    private IMyBmobInstallationBiz myBmobInstallationBiz;
    private IHomeView homeView;
    private Context context;
    private static List<ChangeColorIconWithText> mTabIndicators;
    private SmallBang mSmallbang;


    public HomePresenter(IHomeView homeView, Context context, List<ChangeColorIconWithText> mTabIndicators, SmallBang mSmallbang) {
        this.myBmobInstallationBiz = new MyBmobInstallationBiz();
        this.userBiz = new UserBiz();
        this.homeView = homeView;
        this.context = context;
        this.mTabIndicators = mTabIndicators;
        this.mSmallbang = mSmallbang;
    }


    public void BindUserInstallation() {
        if (homeView.getCurrentUser() == null) {
            homeView.NeedLogin();
            return;
        }
        //绑定User与设备
        myBmobInstallationBiz.UpdateMyBmobInstallationUser(homeView.getCurrentUser().getObjectId(), new OnBindListener() {
            @Override
            public void OnSuccess() {
                Log.i("bmob", "设备信息更新成功");
            }

            @Override
            public void OnFailed() {
                Log.i("bmob", "设备信息更新失败:");
            }
        }, context);
    }

    public void Write_Message(View view) {
        mSmallbang.bang(view, 80, new SmallBangListener() {
            @Override
            public void onAnimationStart() {
                homeView.showPop();
            }

            @Override
            public void onAnimationEnd() {

            }
        });
    }

    public void ReplaceFragement1(ChangeColorIconWithText view) {
        resetOtherTabs();
        setmSmallbang(view);
        view.setIconAlpha(1.0f);
        homeView.click_indicator_one();
    }

    public void ReplaceFragement2(ChangeColorIconWithText view) {
        resetOtherTabs();
        setmSmallbang(view);
        view.setIconAlpha(1.0f);
        homeView.click_indicator_two();
    }

    public void ReplaceFragement3(ChangeColorIconWithText view) {
        resetOtherTabs();
        setmSmallbang(view);
        view.setIconAlpha(1.0f);
        homeView.click_indicator_three();
    }

    public void ReplaceFragement4(ChangeColorIconWithText view) {
        resetOtherTabs();
        setmSmallbang(view);
        view.setIconAlpha(1.0f);
        homeView.click_indicator_four();
    }

    //重置颜色
    private static void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }


    private void setmSmallbang(View view) {
        mSmallbang.bang(view, 80, new SmallBangListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }
        });
    }


    public void toCharactersFragement(int i) {
        IshidePopWindows(true);
        if (i == 1) {
            Intent intent = new Intent(context, CharactersActivity.class);
            intent.putExtra(HomeActivity.TAG, i);
            context.startActivity(intent);
        } else if (i == 2) {
            Intent intent = new Intent(context, CharactersActivity.class);
            intent.putExtra(HomeActivity.TAG, i);
            context.startActivity(intent);
        } else if (i == 3) {
            Intent intent = new Intent(context, AnnomousActivity.class);
            intent.putExtra(HomeActivity.TAG, i);
            context.startActivity(intent);
        } else if (i == 4) {
//            context.startActivity(new Intent(
//                    context, CharactersActivity.class
//            ));
        }
    }

    //是否隐藏popview
    public void IshidePopWindows(boolean ishide) {
        if (ishide) {
            homeView.hidePop();

        } else {
            homeView.showPop();
        }
    }


    public void UpdateGspLocation(LocationInfo locationInfo) {
        userBiz.UpdateLocation(homeView.getCurrentUser(), locationInfo, new OnUpdateGspListener() {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnFailed() {

            }
        },context);


    }

    ;
}
