package com.confress.lovewall.view.FragmentView;

import android.content.Context;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/14.
 */
public interface IHomeFragment4View  {
    void setUserData();
    User getCurrentUser();
    Context getContext();
    void InitUserData(String path,String usernam);
    void NeedLogin();
}
