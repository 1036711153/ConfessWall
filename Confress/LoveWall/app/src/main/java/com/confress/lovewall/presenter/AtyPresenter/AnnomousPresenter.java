package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.text.TextUtils;

import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.view.AtyView.IAnnomousView;

/**
 * Created by admin on 2016/3/11.
 */
public class AnnomousPresenter {
    private IMessageWallBiz messageWallBiz;
    private IAnnomousView annomousView;
    private Context context;

    public AnnomousPresenter(IAnnomousView annomousView, Context context) {
        this.messageWallBiz = new MessageWallBiz();
        this.annomousView = annomousView;
        this.context = context;
    }

    public void uploadData(){
        annomousView.showLoading();
        if (TextUtils.isEmpty(annomousView.getWallMessage())){
            annomousView.hideLoading();
            annomousView.EmptyOfMessage();
            return;
        }
        messageWallBiz.uploadAnnomousMessage(annomousView.getUser(), annomousView.getWallMessage(),new OnUploadDataListener() {
            @Override
            public void OnSusscess() {
                annomousView.hideLoading();
                annomousView.success();
                annomousView.back();

            }

            @Override
            public void OnFailed() {
                annomousView.hideLoading();
                annomousView.failure();
            }
        },context);
    }



}
