package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.text.TextUtils;

import com.confress.lovewall.biz.ITricksBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.TricksBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.model.MyBmobInstallation;
import com.confress.lovewall.view.AtyView.IChattingView;
import com.google.gson.Gson;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;

/**
 * Created by admin on 2016/3/15.
 */
public class ChattingPresenter {
    private ITricksBiz tricksBiz;
    private IChattingView chattingView;
    private Context context;
    private IUserBiz userBiz;

    public ChattingPresenter(IChattingView chattingView, Context context) {
        this.userBiz=new UserBiz();
        this.tricksBiz = new TricksBiz();
        this.chattingView = chattingView;
        this.context = context;
    }

    public void sendMsg(){
        if (TextUtils.isEmpty(chattingView.getMsg())){
            chattingView.EmptyOfMsg();
            return;
        }
        BmobPushManager bmobPush = new BmobPushManager(context);
        BmobQuery<MyBmobInstallation> query = MyBmobInstallation.getQuery();
        query.addWhereEqualTo("uid", chattingView.getFriendUser().getObjectId());
        bmobPush.setQuery(query);
        MsgGosn msgGosn=new MsgGosn();
        msgGosn.setId(chattingView.getCurrentUser().getObjectId());
        msgGosn.setNick(chattingView.getCurrentUser().getNick());
        msgGosn.setMsg(chattingView.getMsg());
        msgGosn.setIcon(chattingView.getCurrentUser().getIcon());
        Gson gson=new Gson();
        bmobPush.pushMessage(gson.toJson(msgGosn));
        chattingView.SetEmptyMsg();
        chattingView.SendSuccess();
    }

}
