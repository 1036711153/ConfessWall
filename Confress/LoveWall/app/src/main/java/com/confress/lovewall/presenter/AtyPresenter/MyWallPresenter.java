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
import com.confress.lovewall.view.AtyView.IMyWallView;
import com.confress.lovewall.view.AtyView.IUserFindPsdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/3/15.
 */
public class MyWallPresenter {
    private IMessageWallBiz messageWallBiz;
    private IMyWallView myWallView;
    private Context context;

    public MyWallPresenter(IMyWallView myWallView, Context context) {
        this.messageWallBiz = new MessageWallBiz();
        this.myWallView = myWallView;
        this.context = context;
    }

    //第一次加载数据OR刷新加载数据
    public  void FirstLoadingData(Context context){
        final   List<MessageWall>messageWalls=new ArrayList<>();
        messageWallBiz.QueryMyWallData(0,myWallView.getCurrentUser(), new OnQueryListener() {
             @Override
             public void Success(List<MessageWall> list) {
                 if (list.size() > 0) {
                     messageWalls.clear();
                     for (MessageWall messageWall : list) {
                         messageWalls.add(messageWall);
                     }
                     myWallView.UpdateAdapter(1,messageWalls);
                 } else {
                     myWallView.UpdateAdapter(1,messageWalls);
                 }
             }

             @Override
             public void Failure() {
                 myWallView.UpdateAdapter(0,messageWalls);
             }
         },context);
    }


    //上拉刷新加载
    public void PullDownRefreshqueryData(int page,final Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        messageWallBiz.QueryMyWallData(page, myWallView.getCurrentUser(), new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    myWallView.UpdateAdapter(2,messageWalls);
                } else {
                    myWallView.UpdateAdapter(3,messageWalls);
                }
            }

            @Override
            public void Failure() {
                myWallView.UpdateAdapter(0,messageWalls);
            }
        }, context);
    }


}
