package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnBindListener;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/18.
 */
public interface IMyBmobInstallationBiz {
    void UpdateMyBmobInstallationUser(String uid,OnBindListener bindListener,Context context);
}
