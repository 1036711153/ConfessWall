package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.text.TextUtils;

import com.confress.lovewall.Utils.T;
import com.confress.lovewall.biz.ChattingMsgBiz;
import com.confress.lovewall.biz.IChattingMsgBiz;
import com.confress.lovewall.biz.IListener.OnQueryChattingMsgListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryUserByIdListener;
import com.confress.lovewall.biz.IListener.OnSaveMsgsListener;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.ChattingMsg;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.model.MyBmobInstallation;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.AtyView.IShowChattingMsgView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;

/**
 * Created by admin on 2016/3/18.
 */
public class ShowChattingPresenter {
    private Context context;
    private IShowChattingMsgView lookTrickView;
    private IUserBiz userBiz;
    private IChattingMsgBiz msgBiz;

    public ShowChattingPresenter(Context context, IShowChattingMsgView lookTrickView) {
        msgBiz=new ChattingMsgBiz();
        this.userBiz = new UserBiz();
        this.context = context;
        this.lookTrickView = lookTrickView;
    }

    public void replyMsg(String uid1, final String uid2) {
        if (lookTrickView.getCurrentUser() == null) {
            lookTrickView.NeedLogin();
            return;
        }
        if (TextUtils.isEmpty(lookTrickView.getSendMsg())) {
            lookTrickView.EmptyOfReplyMsg();
            return;
        }
       final   String msg=lookTrickView.getSendMsg();
        msgBiz.SaveMsgs(uid1, uid2, msg, new OnSaveMsgsListener() {
            @Override
            public void OnSuccess() {
                lookTrickView.SetEmptyOfMsg();
                PushMessage(uid2,msg);
                lookTrickView.ReplySuccess(msg);
            }
            @Override
            public void OnFailed() {
                T.showShort(context,"发送消息失败！");
            }
        },context);
    }


    public void PushMessage(String uid2,String msg){
        BmobPushManager bmobPush = new BmobPushManager(context);
        BmobQuery<MyBmobInstallation> query = MyBmobInstallation.getQuery();
        query.addWhereEqualTo("uid", uid2);
        bmobPush.setQuery(query);
        MsgGosn msgGosn = new MsgGosn();
        msgGosn.setId(lookTrickView.getCurrentUser().getObjectId());
        msgGosn.setNick(lookTrickView.getCurrentUser().getNick());
        msgGosn.setMsg(msg);
        msgGosn.setIcon(lookTrickView.getCurrentUser().getIcon());
        Gson gson = new Gson();
        bmobPush.pushMessage(gson.toJson(msgGosn));
    }

    public void QueryMsgById(String uid1, String uid2) {
//        msgBiz.QueryMsgs(uid1, uid2, new OnQueryChattingMsgListener() {
//            @Override
//            public void Success(List<ChattingMsg> list) {
//
//            }
//
//            @Override
//            public void Failure() {
//
//            }
//        }, context);

    }


    //上拉刷新加载
    public List<ChattingMsg> PullDownRefreshqueryData(int page,String uid1,String uid2,final Context context) {
        final   List<ChattingMsg>messageWalls=new ArrayList<>();
        msgBiz.QueryMsgs(page, uid1,uid2,new OnQueryChattingMsgListener() {
            @Override
            public void Success(List<ChattingMsg> list) {
                if (list.size() > 0) {
                    //添加时候倒叙插入
                    for (int i=list.size()-1;i>=0;i--) {
                        messageWalls.add(list.get(i));
                    }
                    lookTrickView.UpdateAdapter(2, messageWalls);
                } else {
                    lookTrickView.UpdateAdapter(3, messageWalls);
                }
            }

            @Override
            public void Failure() {
                lookTrickView.UpdateAdapter(0, messageWalls);
            }
        }, context);
        return messageWalls;
    }


    public List<ChattingMsg> FirstLoadingData(Context context,String uid1,String uid2) {
        final   List<ChattingMsg>messageWalls=new ArrayList<>();
        msgBiz.QueryMsgs(0, uid1,uid2,new OnQueryChattingMsgListener() {
            @Override
            public void Success(List<ChattingMsg> list) {
                if (list.size() > 0) {
                    messageWalls.clear();
                    for (int i=list.size()-1;i>=0;i--) {
                        messageWalls.add(list.get(i));
                    }
                    lookTrickView.UpdateAdapter(1, messageWalls);
                } else {
                    lookTrickView.UpdateAdapter(1, messageWalls);
                }
            }

            @Override
            public void Failure() {
                lookTrickView.UpdateAdapter(0, messageWalls);
            }
        }, context);
        return messageWalls;
    }

}
