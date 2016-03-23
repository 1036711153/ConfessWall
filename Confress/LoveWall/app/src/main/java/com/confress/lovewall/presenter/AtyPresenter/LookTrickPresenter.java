package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.text.TextUtils;

import com.confress.lovewall.biz.IListener.OnQueryUserByIdListener;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.model.MyBmobInstallation;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.AtyView.IlookTrickView;
import com.google.gson.Gson;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;

/**
 * Created by admin on 2016/3/18.
 */
public class LookTrickPresenter {
    private Context context;
    private IlookTrickView lookTrickView;
    private IUserBiz userBiz;

    public LookTrickPresenter(Context context, IlookTrickView lookTrickView) {
        this.userBiz=new UserBiz();
        this.context = context;
        this.lookTrickView = lookTrickView;
    }

    public void replyMsg(String uid){
        if (TextUtils.isEmpty(lookTrickView.getSendMsg())){
            lookTrickView.EmptyOfReplyMsg();
            return;
        }
        BmobPushManager bmobPush = new BmobPushManager(context);
        BmobQuery<MyBmobInstallation> query = MyBmobInstallation.getQuery();
        query.addWhereEqualTo("uid", uid);
        bmobPush.setQuery(query);
        MsgGosn msgGosn=new MsgGosn();
        msgGosn.setId(lookTrickView.getCurrentUser().getObjectId());
        msgGosn.setNick(lookTrickView.getCurrentUser().getNick());
        msgGosn.setMsg(lookTrickView.getSendMsg());
        msgGosn.setIcon(lookTrickView.getCurrentUser().getIcon());
        Gson gson=new Gson();
        bmobPush.pushMessage(gson.toJson(msgGosn));
        lookTrickView.SetEmptyOfMsg();
        lookTrickView.ReplySuccess();
    }

    public  void QueryUserById(String  uid){
        userBiz.QueryUserById(uid, new OnQueryUserByIdListener() {
            @Override
            public void OnSuccess(User user) {

            }

            @Override
            public void OnFailed() {

            }
        },context);
    }

}
