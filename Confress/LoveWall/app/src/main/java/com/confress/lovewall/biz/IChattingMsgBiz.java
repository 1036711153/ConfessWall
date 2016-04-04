package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnQueryChattingMsgListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnSaveMsgsListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/27.
 */
public interface IChattingMsgBiz {
    void QueryMsgs(int page,String uid1,String uid2,OnQueryChattingMsgListener queryListener,Context context);
    void SaveMsgs(String uid1,String uid2,String content,OnSaveMsgsListener saveMsgsListener,Context context);


}
