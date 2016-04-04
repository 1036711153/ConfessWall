package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.FriendObject;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/16.
 */
public interface OnCreateFriendListener {
    void OnSuccess(FriendObject friendObject);

    void OnFailed();
}
