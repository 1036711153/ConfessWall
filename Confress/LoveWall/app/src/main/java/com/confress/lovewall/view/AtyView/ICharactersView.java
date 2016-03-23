package com.confress.lovewall.view.AtyView;

import android.content.Context;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/11.
 */
public interface ICharactersView {
    void back();
    void showLoading();
    void hideLoading();
    String getPicturepath();
    void setUploadPicturepath(String fileurl);
    String getUploadPicturepath();
    String getWallMessage();
    Context getContext();
    void EmptyOfMessage();
    User getUser();
    void uploadPicfailured();
    void uploadPicSuccess();
    void showtvProgress(int progress);
    void hidetvProgress();
    void success();
    void failure();
}
