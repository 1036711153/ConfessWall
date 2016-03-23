package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/16.
 */
public interface OnQueryUserByIdListener {
    void OnSuccess(User user);

    void OnFailed();
}
