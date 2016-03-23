package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/7.
 */
public interface OnLoginListener {
    void OnSuccess(User user);

    void OnFailed();
}
