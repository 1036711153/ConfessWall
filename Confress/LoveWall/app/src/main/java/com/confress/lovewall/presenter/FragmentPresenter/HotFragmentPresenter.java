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
import com.confress.lovewall.view.FragmentView.IHotFragmentView;
import com.confress.lovewall.view.FragmentView.INewsFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public class HotFragmentPresenter {
    private IUserBiz userBiz;
    private IMessageWallBiz messageWallBiz;
    private IHotFragmentView hotFragmentView;
    private Context context;

    public HotFragmentPresenter(IHotFragmentView hotFragmentView, Context context) {
        this.userBiz = new UserBiz();
        this.messageWallBiz = new MessageWallBiz();
        this.hotFragmentView = hotFragmentView;
        this.context = context;
    }

    //一页查询10个数据
    private int limit = 10;
    //当前页
    private int curPage = 0;

    //上拉刷新加载
    public List<MessageWall> PullDownRefreshqueryData(final Handler mhandler,int page,final Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        messageWallBiz.HotRefreshQueryData(page, new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    Message message=new Message();
                    message.what=2;//0代表加载失败 1代表加载成功
                    message.obj=messageWalls;
                    mhandler.sendMessage(message);
                } else {
                    Message message=new Message();
                    message.what=3;//0代表加载失败 1代表加载成功
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


    //下拉刷新和第一次加载页面方法
    public List<MessageWall> FirstLoadingData(final Handler mhandler,Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        messageWallBiz.HotRefreshQueryData(0, new OnQueryListener() {
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
                    Log.e("newsFragmentView", "" + messageWalls.size());
                } else {
                    Message message=new Message();
                    message.what=1;//0代表加载失败 1代表加载成功
                    message.obj=messageWalls;
                    mhandler.sendMessage(message);
                    Log.e("newsFragmentView", "" + messageWalls.size());
                }
            }

            @Override
            public void Failure() {
                Log.e("FirstLoadingData", "Failure");
                Message message=new Message();
                message.what=0;//0代表加载失败 1代表加载成功
                mhandler.sendMessage(message);
            }
        }, context);
        return messageWalls;
    }

    public void ChangeCount(MessageWall messageWall,Context context,int collectioncount,int messagecount,int supportcount){
        messageWallBiz.updateMessageCount(messageWall, collectioncount, messagecount, supportcount, new OnUploadDataListener() {
            @Override
            public void OnSusscess() {

            }

            @Override
            public void OnFailed() {

            }
        }, context);
    }
    //添加收藏
    public void CollectionOp(Context context,MessageWall messageWall){
        userBiz.CollectionOp(hotFragmentView.getCurrentUser(), messageWall, new OnUpdateListener() {
            @Override
            public void OnSuccess(User user) {
                hotFragmentView.CollectionSuccess();
            }

            @Override
            public void OnFailed() {
                hotFragmentView.CollectionFailure();
            }
        }, context);

    }

    //取消收藏
    public void DelCollection(Context context,MessageWall messageWall){
        userBiz.DelCollection(hotFragmentView.getCurrentUser(), messageWall, new OnUpdateListener() {
            @Override
            public void OnSuccess(User user) {
                hotFragmentView.DelCollectionSuccess();
            }

            @Override
            public void OnFailed() {
                hotFragmentView.DelCollectionFailure();
            }
        }, context);

    }


}
