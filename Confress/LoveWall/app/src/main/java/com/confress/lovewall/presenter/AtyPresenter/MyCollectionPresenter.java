package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.view.AtyView.IMyCollectionView;
import com.confress.lovewall.view.AtyView.IMyWallView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/3/15.
 */
public class MyCollectionPresenter {
    private IUserBiz userBiz;
    private IMessageWallBiz messageWallBiz;
    private IMyCollectionView myCollectionView;
    private Context context;

    public MyCollectionPresenter(IMyCollectionView myCollectionView, Context context) {
        this.userBiz=new UserBiz();
        this.messageWallBiz = new MessageWallBiz();
        this.myCollectionView = myCollectionView;
        this.context = context;
    }

    //第一次加载数据OR刷新加载数据
    public  void FirstLoadingData(final Handler mhandler,Context context){
        final   List<MessageWall>messageWalls=new ArrayList<>();
        userBiz.QueryMyCollectionData(0, myCollectionView.getCurrentUser(), new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    messageWalls.clear();
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    Message message = new Message();
                    message.what = 1;//0代表加载失败 1代表加载成功
                    message.obj = messageWalls;
                    mhandler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = 1;//0代表加载失败 1代表加载成功
                    message.obj = messageWalls;
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
    }


    //上拉刷新加载
    public void PullDownRefreshqueryData(final Handler mhandler,int page,final Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        userBiz.QueryMyCollectionData(page, myCollectionView.getCurrentUser(), new OnQueryListener() {
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
    }


}
