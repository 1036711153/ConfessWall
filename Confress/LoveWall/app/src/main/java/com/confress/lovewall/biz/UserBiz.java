package com.confress.lovewall.biz;

import android.content.Context;
import android.util.Log;

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

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by admin on 2016/3/7.
 */
public class UserBiz implements IUserBiz {
    //登录
    @Override
    public void login(String username, String password, final OnLoginListener loginListener, Context context) {
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                loginListener.OnSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                loginListener.OnFailed();
            }
        });

    }

    @Override
    public void third_login(String uid, String nickname, String icon, final OnLoginListener loginListener, Context context) {
        User user=new User();
        user.setUsername(uid);
        user.setPassword(uid);
        user.setEmail(uid + "@163.com");
        user.setNick(nickname);
        user.setIcon(icon);
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                loginListener.OnSuccess(null);
            }

            @Override
            public void onFailure(int i, String s) {
                //第三方注册失败说明，这个账号已经注册过了直接可以登录成功即可。。。
                loginListener.OnFailed();
            }
        });
    }

    //注册
    @Override
    public void register(String username, String password, String email, final OnRegisterListener registerListener, Context context) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                registerListener.OnSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                registerListener.OnError();
            }
        });
    }

    //找回密码
    @Override
    public void rememberpds(String email, final OnRememberPsdListener onRememberPsdListener, Context context) {
        User.resetPasswordByEmail(context, email, new ResetPasswordByEmailListener() {
            @Override
            public void onSuccess() {
                onRememberPsdListener.OnSusscess();
            }

            @Override
            public void onFailure(int i, String s) {
                onRememberPsdListener.OnFailed();
            }
        });
    }

    //更新个人信息
    @Override
    public void UpdateUserData(final User user, String nick, String sex, int age, String tel, final OnUpdateListener updateListener, Context context) {
        user.setAge(age);
        user.setSex(sex);
        user.setNick(nick);
        user.setTel(tel);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                updateListener.OnSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                updateListener.OnFailed();
            }
        });
    }

    //更新头像
    @Override
    public void UpdateUserIcon(final User user, String iconpath, final OnUpdateListener updateListener, Context context) {
        user.setIcon(iconpath);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                updateListener.OnSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                updateListener.OnFailed();
            }
        });
    }

    //个人收藏操作
    @Override
    public void CollectionOp(final User user, final MessageWall messageWall, final OnUpdateListener updateListener, Context context) {
        User myUser = new User();
        myUser.setObjectId(user.getObjectId());
        BmobRelation relation = new BmobRelation();
        relation.add(messageWall);
        myUser.setLikes(relation);
        myUser.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                updateListener.OnSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                updateListener.OnFailed();
            }
        });

    }


    //个人取消收藏操作
    @Override
    public void DelCollection(final User user, MessageWall messageWall, final OnUpdateListener updateListener, Context context) {
        User myUser = new User();
        myUser.setObjectId(user.getObjectId());
        BmobRelation relation = new BmobRelation();
        relation.remove(messageWall);
        myUser.setLikes(relation);
        myUser.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                updateListener.OnSuccess(user);
            }

            @Override
            public void onFailure(int i, String s) {
                updateListener.OnFailed();
            }
        });
    }


    //查询我的收藏
    @Override
    public void QueryMyCollectionData(int page, User user, final OnQueryListener queryListener, Context context) {
        final BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        User muser = new User();
        muser.setObjectId(user.getObjectId());
        query.addWhereRelatedTo("likes", new BmobPointer(muser));
        query.include("user");
        query.order("-createdAt");
        query.setLimit(20);
        query.setSkip(page * 20);
        query.findObjects(context, new FindListener<MessageWall>() {
            @Override
            public void onSuccess(List<MessageWall> list) {
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryListener.Failure();
            }
        });


    }

    //创建FriendObject对象
    @Override
    public void CreateFriendObject(String friendId, final OnCreateFriendListener createFriendListener, Context context) {
        final FriendObject friendObject=new FriendObject();
        User friend=new User();
        friend.setObjectId(friendId);
        friendObject.setUser(friend);
        friendObject.setTricks(null);
        friendObject.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                createFriendListener.OnSuccess(friendObject);
            }

            @Override
            public void onFailure(int i, String s) {
                createFriendListener.OnFailed();
            }
        });

    }

    //添加好友
    @Override
    public void AddFriends(final User user,String friendId, final OnAddFriendsListener addFriendsListener,final Context context) {
        User myUser = new User();
        myUser.setObjectId(user.getObjectId());

        BmobRelation relation = new BmobRelation();

        User friendObject=new User();
        friendObject.setObjectId(friendId);

        relation.add(friendObject);
        myUser.setFriends(relation);
        myUser.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                addFriendsListener.OnSuccess();
            }
            @Override
            public void onFailure(int i, String s) {
                addFriendsListener.OnFailed();
            }
        });
    }

    @Override
    public void AddFriends2(User user, User friend,final OnAddFriendsListener addFriendsListener, Context context) {
        Log.e("AddFriends2","AddFriends2");
        User myUser2 = new User();
        myUser2.setObjectId(friend.getObjectId());
        BmobRelation relation2 = new BmobRelation();
        relation2.add(user);
        myUser2.setFriends(relation2);
        myUser2.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.e("AddFriends2","AddFriends2Success");
                addFriendsListener.OnSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("AddFriends2","AddFriends2Failure"+s);
                addFriendsListener.OnFailed();
            }
        });
    }

    //取关
    @Override
    public void DelFriends(User user, User friend, final OnAddFriendsListener addFriendsListener, Context context) {
        User myUser = new User();
        myUser.setObjectId(user.getObjectId());
        BmobRelation relation = new BmobRelation();
        relation.remove(friend);
        myUser.setFriends(relation);
        myUser.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                addFriendsListener.OnSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                addFriendsListener.OnFailed();
            }
        });
    }

    @Override
    public void QueryFriends(int page, User user, final OnQueryFriendsListener queryFriendsListener, Context context) {
        final BmobQuery<User> query = new BmobQuery<User>();
        User muser = new User();
        muser.setObjectId(user.getObjectId());
        query.addWhereRelatedTo("friends", new BmobPointer(muser));
        query.order("-createdAt");
        query.setLimit(20);
        query.setSkip(page * 20);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                queryFriendsListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryFriendsListener.Failure();
            }
        });

    }
    //通过经纬度查找附近墙友
    @Override
    public void QueryNearFriends(int page, BmobGeoPoint gpsadd,final OnQueryFriendsListener queryFriendsListener, Context context) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereNear("gpsadd", new BmobGeoPoint(gpsadd.getLongitude(),gpsadd.getLatitude()));
        query.setLimit(20);
        query.setSkip(page * 20);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                queryFriendsListener.Success(list);
                Log.e("USERBIZ",""+list.size());
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
               queryFriendsListener.Failure();
                Log.e("USERBIZ", "" + msg);
            }
        });


    }

    //根据ID查找User
    @Override
    public void QueryUserById(String userId, final OnQueryUserByIdListener queryUserByIdListener, Context context) {
        final BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", userId);
        query.order("-createdAt");
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list.size()>0){
                    queryUserByIdListener.OnSuccess(list.get(0));
                }else {
                    queryUserByIdListener.OnFailed();
                }
            }
            @Override
            public void onError(int i, String s) {
                queryUserByIdListener.OnFailed();
            }
        });
    }

    @Override
    public void UpdateLocation(User user,LocationInfo locationInfo, final OnUpdateGspListener onUpdateGspListener, Context context) {
        user.setAddress(locationInfo.getAddress());
        user.setGpsadd(new BmobGeoPoint(locationInfo.getLongtitude(), locationInfo.getLatitude()));
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                onUpdateGspListener.OnSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                onUpdateGspListener.OnFailed();
            }
        });
    }
}
