package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/18.
 */
public interface IChattingView {
    void back();

    String getMsg();

    void SendError();

    void EmptyOfMsg();

    User getCurrentUser();

    User getFriendUser();

    void SetFriendUser(User friend_user);

    void SetEmptyMsg();
    void SendSuccess();
}
