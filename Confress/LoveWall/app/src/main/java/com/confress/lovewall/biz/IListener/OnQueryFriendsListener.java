package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public interface OnQueryFriendsListener {
    void Success(List<User> list);

    void Failure();
}
