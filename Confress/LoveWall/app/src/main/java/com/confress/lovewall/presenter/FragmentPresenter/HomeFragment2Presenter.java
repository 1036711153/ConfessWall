package com.confress.lovewall.presenter.FragmentPresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.confress.lovewall.biz.IListener.OnCommentQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryFriendsListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.Comment;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.FragmentView.IHomeFragment2View;
import com.confress.lovewall.view.FragmentView.IHomeFragment4View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/3/14.
 */
public class HomeFragment2Presenter {
    private IUserBiz userBiz;
    private IHomeFragment2View homeFragment2View;
    private Context context;



    public HomeFragment2Presenter(IHomeFragment2View homeFragment2View, Context context) {
        this.userBiz=new UserBiz();
        this.homeFragment2View = homeFragment2View;
        this.context = context;
    }

    //第一次加载数据OR刷新加载数据
    public  void FirstLoadingData(final Handler mhandler,Context context){
        final   List<User> users=new ArrayList<>();
        userBiz.QueryFriends(0, homeFragment2View.getUser(), new OnQueryFriendsListener() {
            @Override
            public void Success(List<User> list) {
                if (list.size() > 0) {
                    users.clear();
                    for (User user : list) {
                        users.add(user);
                    }
                    Message message = new Message();
                    message.what = 1;//0代表加载失败 1代表加载成功
                    message.obj = users;
                    mhandler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = 1;//0代表加载失败 1代表加载成功
                    message.obj = users;
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
        final   List<User>users=new ArrayList<>();
        userBiz.QueryFriends(page, homeFragment2View.getUser(), new OnQueryFriendsListener() {
            @Override
            public void Success(List<User> list) {
                if (list.size() > 0) {
                    for (User user : list) {
                        users.add(user);
                    }
                    Message message = new Message();
                    message.what = 2;//0代表加载失败 1代表加载成功
                    message.obj = users;
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
