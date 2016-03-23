package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.biz.IListener.OnUploadPictureListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.AtyView.IUserRegisterView;
import com.confress.lovewall.view.AtyView.IUserSettingView;

import java.nio.channels.Pipe;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2016/3/14.
 */
public class UserSettingActivityPresenter {
    private IUserBiz userBiz;
    private IUserSettingView userSettingView;
    private IMessageWallBiz messageWallBiz;
    private Context context;

    public UserSettingActivityPresenter(IUserSettingView userSettingView, Context context) {
        this.userBiz = new UserBiz();
        this.messageWallBiz = new MessageWallBiz();
        this.userSettingView = userSettingView;
        this.context = context;
    }

    //更新头像
    public void updateIcon(String picturepath) {
        messageWallBiz.uploadPicture(picturepath, new OnUploadPictureListener() {
            @Override
            public void OnSusscess(BmobFile file) {
                userSettingView.successTvprogress();
                UpdatePictureData(file.getUrl());
            }

            @Override
            public void OnProgress(int progress) {
                userSettingView.showtvProgress(progress);
            }

            @Override
            public void OnFailed() {
                userSettingView.successTvprogress();
            }
        }, context);
    }

    public void UpdatePictureData(String urlpath) {
        userBiz.UpdateUserIcon(userSettingView.getCurrentUser(), urlpath, new OnUpdateListener() {
            @Override
            public void OnSuccess(User user) {
                userSettingView.UploadIconSuccess();
            }

            @Override
            public void OnFailed() {
                userSettingView.UploadIconFailure();
            }
        }, context);
    }

    public void ShowUserData(){
        User user = userSettingView.getCurrentUser();
        userSettingView.setAge(user);
        userSettingView.setNickName(user);
        userSettingView.setTel(user);
        userSettingView.setSex(user);
        userSettingView.setUserIcon(user);
    }

    //更新个人数据
    public void UpdataUserData() {
        userBiz.UpdateUserData(userSettingView.getCurrentUser(), userSettingView.getNickName(),
                userSettingView.getSex(), userSettingView.getAge(), userSettingView.getTel(), new OnUpdateListener() {
            @Override
            public void OnSuccess(User user) {
                userSettingView.Success();
            }

            @Override
            public void OnFailed() {
               userSettingView.Failure();
            }
        }, context);
    }




}
