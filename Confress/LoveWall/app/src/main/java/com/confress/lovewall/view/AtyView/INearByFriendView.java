package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/4/2.
 */
public interface INearByFriendView {
    void LoadOver();
    User getUser();
    void Failure();
    void NeedLogin();
    void UpdateAdapter(int size, List<User> Iusers);
}
