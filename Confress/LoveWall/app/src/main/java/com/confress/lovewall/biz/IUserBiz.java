package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnAddFriendsListener;
import com.confress.lovewall.biz.IListener.OnLoginListener;
import com.confress.lovewall.biz.IListener.OnQueryFriendsListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryUserByIdListener;
import com.confress.lovewall.biz.IListener.OnRegisterListener;
import com.confress.lovewall.biz.IListener.OnRememberPsdListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/7.
 */
public interface IUserBiz {
    void login(String username, String password, OnLoginListener loginListener, Context context);

    void register(String username, String password, String email, OnRegisterListener registerListener, Context context);

    void rememberpds(String email, OnRememberPsdListener onRememberPsdListener, Context context);

    void UpdateUserData(User user, String nick, String sex, int age, String tel, OnUpdateListener updateListener, Context context);

    void UpdateUserIcon(User user, String iconpath, OnUpdateListener updateListener, Context context);

    void CollectionOp(User user, MessageWall messageWall, OnUpdateListener updateListener, Context context);

    void DelCollection(User user, MessageWall messageWall, OnUpdateListener updateListener, Context context);

    void QueryMyCollectionData(int page,User user,OnQueryListener queryListener,Context context);

    void AddFriends(User user,User friend,OnAddFriendsListener addFriendsListener,Context context);
    void DelFriends(User user,User friend,OnAddFriendsListener addFriendsListener,Context context);


    void QueryFriends(int page,User user,OnQueryFriendsListener queryFriendsListener,Context context);


    void QueryUserById(String userId,OnQueryUserByIdListener queryUserByIdListener,Context context);

}
