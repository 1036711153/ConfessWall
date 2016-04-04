package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.ChattingMsg;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/18.
 */
public interface IShowChattingMsgView {
    String  getSendMsg();
    void  SetRecieveMsg();
    void EmptyOfReplyMsg();
    User getCurrentUser();
    void ReplySuccess(String msg);
    void SetEmptyOfMsg();

    void InitFriendsData();
    void NeedLogin();

    void UpdateAdapter(int size, List<ChattingMsg> IchattingMsgs);
    void LoadOver();
}
