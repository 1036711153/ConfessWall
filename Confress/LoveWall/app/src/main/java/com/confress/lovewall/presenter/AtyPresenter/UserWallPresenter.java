package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.confress.lovewall.biz.IListener.OnAddFriendsListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.model.MyBmobInstallation;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.AtyView.IMyWallView;
import com.confress.lovewall.view.AtyView.IUserWallView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/15.
 */
public class UserWallPresenter {
    private IUserBiz userBiz;
    private IMessageWallBiz messageWallBiz;
    private IUserWallView userWallView;
    private Context context;

    public UserWallPresenter(IUserWallView userWallView, Context context) {
        this.userBiz = new UserBiz();
        this.messageWallBiz = new MessageWallBiz();
        this.userWallView = userWallView;
        this.context = context;
    }

    //第一次加载数据OR刷新加载数据
    public void FirstLoadingData(Context context) {
        final List<MessageWall> messageWalls = new ArrayList<>();
        messageWallBiz.QueryMyWallData(0, userWallView.getWallUser(), new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    messageWalls.clear();
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    userWallView.UpdateAdapter(1,messageWalls);
                } else {
                    userWallView.UpdateAdapter(1,messageWalls);
                }
            }

            @Override
            public void Failure() {
                userWallView.UpdateAdapter(0,messageWalls);
            }
        }, context);
    }


    //上拉刷新加载
    public void PullDownRefreshqueryData(int page, final Context context) {
        final List<MessageWall> messageWalls = new ArrayList<>();
        messageWallBiz.QueryMyWallData(page, userWallView.getWallUser(), new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    userWallView.UpdateAdapter(2,messageWalls);
                } else {
                    userWallView.UpdateAdapter(3,messageWalls);
                }
            }

            @Override
            public void Failure() {
                userWallView.UpdateAdapter(0,messageWalls);
            }
        }, context);
    }

    //添加好友
    public void ADDFriends() {

        final User user = BmobUser.getCurrentUser(context, User.class);
        if (user.getObjectId().endsWith(userWallView.getWallUser().getObjectId())){
            userWallView.ErrorOfAttention();
            return;
        }
//        userBiz.AddFriends(user, userWallView.getWallUser(), new OnAddFriendsListener() {
//            @Override
//            public void OnSuccess() {
//                userWallView.AttentionSuccess();
//            }
//
//            @Override
//            public void OnFailed() {
//                userWallView.AttentionFailure();
//            }
//        }, context);

    }

    public  void ADDFriendSuccess(){
        userBiz.AddFriends2( BmobUser.getCurrentUser(context, User.class), userWallView.getWallUser(), new OnAddFriendsListener() {
            @Override
            public void OnSuccess() {
//                userWallView.AttentionSuccess();
            }

            @Override
            public void OnFailed() {
//                userWallView.AttentionFailure();
            }
        }, context);
    }

    //发送添加好友邀请
    public void AddRequest(){
        BmobPushManager bmobPush = new BmobPushManager(context);
        BmobQuery<MyBmobInstallation> query = MyBmobInstallation.getQuery();
        query.addWhereEqualTo("uid", userWallView.getWallUser().getObjectId());
        bmobPush.setQuery(query);
        MsgGosn msgGosn=new MsgGosn();
        msgGosn.setId(userWallView.getCurrentUser().getObjectId());
        msgGosn.setNick(userWallView.getCurrentUser().getNick());
        msgGosn.setIcon(userWallView.getCurrentUser().getIcon());
        msgGosn.setIsaddfriend(true);
        msgGosn.setRecieveaddfriend(false);
        Gson gson=new Gson();
        bmobPush.pushMessage(gson.toJson(msgGosn));
    }
}
