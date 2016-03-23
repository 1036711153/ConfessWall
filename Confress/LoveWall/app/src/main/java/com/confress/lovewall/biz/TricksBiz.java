package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnQueryTricksListener;
import com.confress.lovewall.biz.IListener.OnTricksListener;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/18.
 */
public class TricksBiz implements  ITricksBiz {

    @Override
    public void QueryMsgs(int page, User user, OnQueryTricksListener queryTricksListener, Context context) {

    }

    @Override
    public void SendMsgs(User user, User friend, String msgs, int type, OnTricksListener tricksListener, Context context) {

    }

    @Override
    public void RecieveMsgs(User user, User friend, String msgs, int type, OnTricksListener tricksListener, Context context) {

    }
}
