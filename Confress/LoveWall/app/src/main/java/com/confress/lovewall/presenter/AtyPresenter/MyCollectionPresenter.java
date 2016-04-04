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
    public  void FirstLoadingData(Context context){


        final   List<MessageWall>messageWalls=new ArrayList<>();
        userBiz.QueryMyCollectionData(0, myCollectionView.getCurrentUser(), new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    messageWalls.clear();
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    myCollectionView.UpdateAdapter(1,messageWalls);
                } else {
                    myCollectionView.UpdateAdapter(1,messageWalls);
                }
            }

            @Override
            public void Failure() {
                myCollectionView.UpdateAdapter(0,messageWalls);
            }
        }, context);
    }


    //上拉刷新加载
    public void PullDownRefreshqueryData(int page,final Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        userBiz.QueryMyCollectionData(page, myCollectionView.getCurrentUser(), new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    myCollectionView.UpdateAdapter(2,messageWalls);
                } else {
                    myCollectionView.UpdateAdapter(3,messageWalls);
                }
            }

            @Override
            public void Failure() {
                myCollectionView.UpdateAdapter(0,messageWalls);
            }
        }, context);
    }


}
