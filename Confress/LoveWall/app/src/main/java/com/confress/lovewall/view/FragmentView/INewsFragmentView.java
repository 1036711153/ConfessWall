package com.confress.lovewall.view.FragmentView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/13.
 */
public interface INewsFragmentView {
    void showLoading();
    void hideLoading();
    void Failure();
    User getCurrentUser();
    void CollectionSuccess();
    void CollectionFailure();
    void DelCollectionSuccess();
    void DelCollectionFailure();
}
