package com.confress.lovewall.BroadcastReceiver;

import android.content.Context;

import com.confress.lovewall.Utils.T;
import com.confress.lovewall.biz.IListener.OnAddFriendsListener;
import com.confress.lovewall.biz.IListener.OnCreateFriendListener;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.FriendObject;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.model.MyBmobInstallation;
import com.confress.lovewall.model.User;
import com.google.gson.Gson;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/27.
 */
public class PushMessageReceiverPresenter {
    private IUserBiz userBiz;
    private Context context;
    private MsgGosn msgGosn;

    public PushMessageReceiverPresenter(Context context, MsgGosn msgGosn) {
        this.userBiz = new UserBiz();
        this.context = context;
        this.msgGosn = msgGosn;
    }

    //接受添加邀请
    public void ReceiveRequest() {
        userBiz.AddFriends(BmobUser.getCurrentUser(context, User.class), msgGosn.getId(), new OnAddFriendsListener() {
            @Override
            public void OnSuccess() {
                T.showShort(context, "添加好友成功!");
                //回复信息
                AddRequest();
            }

            @Override
            public void OnFailed() {
                T.showShort(context, "添加好友失败!");
            }
        }, context);
    }
    //发送回复好友添加成功！
    public void AddRequest(){
        BmobPushManager bmobPush = new BmobPushManager(context);
        BmobQuery<MyBmobInstallation> query = MyBmobInstallation.getQuery();
        query.addWhereEqualTo("uid", msgGosn.getId());
        bmobPush.setQuery(query);
        MsgGosn msgGosn=new MsgGosn();
        msgGosn.setId(BmobUser.getCurrentUser(context, User.class).getObjectId());
        msgGosn.setNick(BmobUser.getCurrentUser(context, User.class).getNick());
        msgGosn.setIcon(BmobUser.getCurrentUser(context, User.class).getIcon());
        msgGosn.setIsaddfriend(false);
        msgGosn.setRecieveaddfriend(true);
        Gson gson=new Gson();
        bmobPush.pushMessage(gson.toJson(msgGosn));
    }

    //接受添加邀请成功返回之后自己添加好友
    public void RequestSuccess() {
        userBiz.AddFriends(BmobUser.getCurrentUser(context, User.class), msgGosn.getId(), new OnAddFriendsListener() {
            @Override
            public void OnSuccess() {
                T.showShort(context, "添加好友" + msgGosn.getNick()+"成功!");
            }

            @Override
            public void OnFailed() {
                T.showShort(context, "添加好友失败!");
            }
        }, context);
    }


    public void SaveChattingMsg(){

    }





}
