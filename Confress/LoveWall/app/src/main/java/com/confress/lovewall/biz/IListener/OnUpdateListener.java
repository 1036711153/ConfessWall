package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/14.
 */
public interface OnUpdateListener {
    void OnSuccess(User user);

    void OnFailed();
}
