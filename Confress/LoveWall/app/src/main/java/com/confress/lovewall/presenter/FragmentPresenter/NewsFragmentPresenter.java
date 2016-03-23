package com.confress.lovewall.presenter.FragmentPresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.confress.lovewall.Utils.T;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.FragmentView.INewsFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public class NewsFragmentPresenter {
    private IUserBiz userBiz;
    private IMessageWallBiz messageWallBiz;
    private INewsFragmentView newsFragmentView;
    private Context context;

    public NewsFragmentPresenter(INewsFragmentView newsFragmentView, Context context) {
        this.userBiz = new UserBiz();
        this.messageWallBiz = new MessageWallBiz();
        this.newsFragmentView = newsFragmentView;
        this.context = context;
    }

    //上拉刷新加载
    public List<MessageWall> PullDownRefreshqueryData(final Handler mhandler,int page,final Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        messageWallBiz.NewRefreshQueryData(page, new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    Message message = new Message();
                    message.what = 2;//0代表加载失败 1代表加载成功
                    message.obj = messageWalls;
                    mhandler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = 3;//0代表加载失败 1代表加载成功
                    mhandler.sendMessage(message);
                }
            }

            @Override
            public void Failure() {
                Message message = new Message();
                message.what = 0;//0代表加载失败 1代表加载成功
                mhandler.sendMessage(message);
            }
        }, context);
        return messageWalls;
    }


    public List<MessageWall> FirstLoadingData(final Handler mhandler,Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        messageWallBiz.NewRefreshQueryData(0, new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    messageWalls.clear();
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    Message message=new Message();
                    message.what=1;//0代表加载失败 1代表加载成功
                    message.obj=messageWalls;
                    mhandler.sendMessage(message);
                } else {
                    Message message=new Message();
                    message.what=1;//0代表加载失败 1代表加载成功
                    message.obj=messageWalls;
                    mhandler.sendMessage(message);
                }
            }

            @Override
            public void Failure() {
                Message message=new Message();
                message.what=0;//0代表加载失败 1代表加载成功
                mhandler.sendMessage(message);
            }
        }, context);
        return messageWalls;
    }

    //添加收藏
    public void CollectionOp(Context context,MessageWall messageWall){
        userBiz.CollectionOp(newsFragmentView.getCurrentUser(), messageWall, new OnUpdateListener() {
               @Override
               public void OnSuccess(User user) {
                   newsFragmentView.CollectionSuccess();
               }

               @Override
               public void OnFailed() {
                   newsFragmentView.CollectionFailure();
               }
           }, context);

    }

    //取消收藏
    public void DelCollection(Context context,MessageWall messageWall){
        userBiz.DelCollection(newsFragmentView.getCurrentUser(), messageWall, new OnUpdateListener() {
            @Override
            public void OnSuccess(User user) {
                newsFragmentView.DelCollectionSuccess();
            }

            @Override
            public void OnFailed() {
                newsFragmentView.DelCollectionFailure();
            }
        }, context);

    }


    public void ChangeCount(MessageWall messageWall,Context context,int collectioncount,int messagecount,int supportcount){
        messageWallBiz.updateMessageCount(messageWall,collectioncount,messagecount,supportcount, new OnUploadDataListener() {
            @Override
            public void OnSusscess() {

            }

            @Override
            public void OnFailed() {

            }
        },context);
    }

}
