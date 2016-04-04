package com.confress.lovewall.presenter.FragmentPresenter;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.confress.lovewall.Activity.ContactUsActivity;
import com.confress.lovewall.Activity.MyTrickAcivity;
import com.confress.lovewall.Activity.MyWallActivity;
import com.confress.lovewall.Activity.UserSettingActivity;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.FragmentView.IHomeFragment4View;
import com.confress.lovewall.view.FragmentView.IHotFragmentView;

/**
 * Created by admin on 2016/3/14.
 */
public class HomeFragment4Presenter {
    private IUserBiz userBiz;
    private IHomeFragment4View homeFragment4View;
    private Context context;

    public HomeFragment4Presenter(IHomeFragment4View homeFragment4View, Context context) {
        this.userBiz=new UserBiz();
        this.homeFragment4View = homeFragment4View;
        this.context = context;
    }

    public  void InitUserData(){
        User currentUser = homeFragment4View.getCurrentUser();
        if (currentUser==null){
            homeFragment4View.NeedLogin();
            return;
        }
        homeFragment4View.InitUserData(currentUser.getIcon(),currentUser.getNick());
    }
}
