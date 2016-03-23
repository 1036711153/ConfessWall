package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnAddFriendsListener;
import com.confress.lovewall.biz.IListener.OnQueryFriendsListener;
import com.confress.lovewall.biz.IListener.OnQueryTricksListener;
import com.confress.lovewall.biz.IListener.OnTricksListener;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/18.
 */
public interface ITricksBiz {
    void QueryMsgs(int page,User user,OnQueryTricksListener queryTricksListener,Context context);
    void SendMsgs(User user,User friend,String msgs,int type,OnTricksListener tricksListener,Context context);
    void RecieveMsgs(User user,User friend,String msgs,int type,OnTricksListener tricksListener,Context context);

}
