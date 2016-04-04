package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/15.
 */
public interface ICommentView {
    void showLoading();
    void hideLoading();
    void Failure();
    User getCurrentUser();
    String getCommentMsg();
    void PostSuccess();
    void PostFailure();
    void EmptyMsg();
    void NeedLogin();
    void CollectionSuccess();
    void CollectionFailure();
    void DelCollectionSuccess();
    void DelCollectionFailure();

    void UpdateCommentCount(int count);
    void UpdateSupportCount(int count);
    void UpdateCollectionCount(int count);
}
