package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnAddFriendsListener;
import com.confress.lovewall.biz.IListener.OnCreateFriendListener;
import com.confress.lovewall.biz.IListener.OnLoginListener;
import com.confress.lovewall.biz.IListener.OnQueryFriendsListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryUserByIdListener;
import com.confress.lovewall.biz.IListener.OnRegisterListener;
import com.confress.lovewall.biz.IListener.OnRememberPsdListener;
import com.confress.lovewall.biz.IListener.OnUpdateGspListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.model.FriendObject;
import com.confress.lovewall.model.LocationInfo;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by admin on 2016/3/7.
 */
public interface IUserBiz {
    //登录
    void login(String username, String password, OnLoginListener loginListener, Context context);
    //第三方登录
    void  third_login(String uid,String nickname,String icon,OnLoginListener loginListener,Context context);
    //注册
    void register(String username, String password, String email, OnRegisterListener registerListener, Context context);
   //忘记密码
    void rememberpds(String email, OnRememberPsdListener onRememberPsdListener, Context context);
    //更新User基本信息
    void UpdateUserData(User user, String nick, String sex, int age, String tel, OnUpdateListener updateListener, Context context);
   //上传User头像
    void UpdateUserIcon(User user, String iconpath, OnUpdateListener updateListener, Context context);
    //添加收藏
    void CollectionOp(User user, MessageWall messageWall, OnUpdateListener updateListener, Context context);
    //取消收藏
    void DelCollection(User user, MessageWall messageWall, OnUpdateListener updateListener, Context context);
    //查找我的收藏
    void QueryMyCollectionData(int page,User user,OnQueryListener queryListener,Context context);


    void CreateFriendObject(String friendId,OnCreateFriendListener createFriendListener,Context context);
    //添加好友
    void AddFriends(User user,String friendId,OnAddFriendsListener addFriendsListener,Context context);
    void AddFriends2(User user,User friend,OnAddFriendsListener addFriendsListener,Context context);
    void DelFriends(User user,User friend,OnAddFriendsListener addFriendsListener,Context context);

    //查找我的好友
    void QueryFriends(int page,User user,OnQueryFriendsListener queryFriendsListener,Context context);
   //查找附近好友
    void QueryNearFriends(int page,BmobGeoPoint gpsadd,OnQueryFriendsListener queryFriendsListener,Context context);


    void QueryUserById(String userId,OnQueryUserByIdListener queryUserByIdListener,Context context);

   //更新地理位置
    void UpdateLocation(User user,LocationInfo locationInfo,OnUpdateGspListener onUpdateGspListener,Context context);

}
