package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/18.
 */
public interface IlookTrickView {
    String  getSendMsg();
    void  SetRecieveMsg();
    void EmptyOfReplyMsg();
    User getCurrentUser();
    void ReplySuccess();
    void SetEmptyOfMsg();

    void InitFriendsData();
    
}
