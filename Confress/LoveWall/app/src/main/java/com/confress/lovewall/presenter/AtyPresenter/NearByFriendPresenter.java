package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnQueryFriendsListener;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.AtyView.INearByFriendView;
import com.confress.lovewall.view.FragmentView.IHomeFragment2View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/4/2.
 */
public class NearByFriendPresenter {
    private IUserBiz userBiz;
    private Context context;
    private INearByFriendView nearByFriendView;

    public NearByFriendPresenter(INearByFriendView nearByFriendView, Context context) {
        this.userBiz = new UserBiz();
        this.nearByFriendView = nearByFriendView;
        this.context = context;
    }


    //第一次加载数据OR刷新加载数据
    public void FirstLoadingData( Context context) {
        if (nearByFriendView.getUser() == null) {
            return;
        }
        final List<User> users = new ArrayList<>();
        userBiz.QueryNearFriends(0, nearByFriendView.getUser().getGpsadd(), new OnQueryFriendsListener() {
            @Override
            public void Success(List<User> list) {
                if (list.size() > 0) {
                    users.clear();
                    for (User user : list) {
                        users.add(user);
                    }
                    nearByFriendView.UpdateAdapter(1, users);

                } else {
                    nearByFriendView.UpdateAdapter(1, users);
                }
            }
            @Override
            public void Failure() {
                nearByFriendView.UpdateAdapter(0, users);
            }
        }, context);
    }

    //上拉刷新加载
    public void PullDownRefreshqueryData( int page, final Context context) {
        if (nearByFriendView.getUser() == null) {
            return;
        }
        final List<User> users = new ArrayList<>();
        userBiz.QueryNearFriends(page, nearByFriendView.getUser().getGpsadd(), new OnQueryFriendsListener() {
            @Override
            public void Success(List<User> list) {
                if (list.size() > 0) {
                    for (User user : list) {
                        users.add(user);
                    }
                    nearByFriendView.UpdateAdapter(2, users);
                } else {
                    nearByFriendView.UpdateAdapter(3, users);
                }
            }
            @Override
            public void Failure() {
                nearByFriendView.UpdateAdapter(0, users);
            }
        }, context);
    }


}
