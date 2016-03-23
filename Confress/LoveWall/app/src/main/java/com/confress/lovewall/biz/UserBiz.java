package com.confress.lovewall.biz;

import android.content.Context;
import android.text.TextUtils;

import com.confress.lovewall.biz.IListener.OnAddFriendsListener;
import com.confress.lovewall.biz.IListener.OnLoginListener;
import com.confress.lovewall.biz.IListener.OnQueryFriendsListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryUserByIdListener;
import com.confress.lovewall.biz.IListener.OnRegisterListener;
import com.confress.lovewall.biz.IListener.OnRememberPsdListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
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
        query.order("-updatedAt");
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

    //关注
    @Override
    public void AddFriends(User user, User friend, final OnAddFriendsListener addFriendsListener, Context context) {
        User myUser = new User();
        myUser.setObjectId(user.getObjectId());
        BmobRelation relation = new BmobRelation();
        relation.add(friend);
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
        query.order("-updatedAt");
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

    //根据ID查找User
    @Override
    public void QueryUserById(String userId, final OnQueryUserByIdListener queryUserByIdListener, Context context) {
        final BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", userId);
        query.order("-updatedAt");
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


}
