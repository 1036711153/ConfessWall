package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.text.TextUtils;

import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.biz.IListener.OnUploadPictureListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.view.AtyView.ICharactersView;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2016/3/11.
 */
public class CharactersPresenter {
    private IMessageWallBiz messageWallBiz;
    private ICharactersView charactersView;
    private Context context;

    public CharactersPresenter(ICharactersView charactersView, Context context) {
        this.messageWallBiz = new MessageWallBiz();
        this.charactersView = charactersView;
        this.context = context;
    }

    public void UploadMessage() {
        if (TextUtils.isEmpty(charactersView.getWallMessage())){
            charactersView.EmptyOfMessage();
            return;
        }
        charactersView.showLoading();
        messageWallBiz.uploadMessage(charactersView.getUser(), charactersView.getWallMessage(), new OnUploadDataListener() {
            @Override
            public void OnSusscess() {
                charactersView.hideLoading();
                charactersView.success();
                charactersView.back();
            }

            @Override
            public void OnFailed() {
                charactersView.hideLoading();
                charactersView.failure();
            }
        }, context);
    }

    //上传照片和文字
    public void UploadPictureAndMessage() {
        if (TextUtils.isEmpty(charactersView.getWallMessage())){
            charactersView.EmptyOfMessage();
            return;
        }
        charactersView.showLoading();
        messageWallBiz.uploadPictureAndMessage(charactersView.getUser(), charactersView.getWallMessage(), charactersView.getUploadPicturepath(), new OnUploadDataListener() {
            @Override
            public void OnSusscess() {
                charactersView.hideLoading();
                charactersView.success();
                charactersView.back();
            }

            @Override
            public void OnFailed() {
                charactersView.hideLoading();
                charactersView.failure();
            }
        }, context);
    }

    //上传照片
    public void uploadPicture(String picturepath) {
        messageWallBiz.uploadPicture(picturepath, new OnUploadPictureListener() {
            @Override
            public void OnSusscess( BmobFile file) {
                charactersView.hidetvProgress();
                charactersView.setUploadPicturepath(file.getUrl());
                charactersView.uploadPicSuccess();
            }

            @Override
            public void OnProgress(int progress) {
                charactersView.showtvProgress(progress);
            }

            @Override
            public void OnFailed() {
                charactersView.hidetvProgress();
                charactersView.uploadPicfailured();
            }
        }, context);
    }


}
