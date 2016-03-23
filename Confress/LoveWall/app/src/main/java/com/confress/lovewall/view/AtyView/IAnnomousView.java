package com.confress.lovewall.view.AtyView;

import android.content.Context;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/12.
 */
public interface IAnnomousView {
    void success();
    void failure();
    String getWallMessage();
    Context getContext();
    void EmptyOfMessage();
    User getUser();
    void showLoading();
    void hideLoading();
    void back();
}
