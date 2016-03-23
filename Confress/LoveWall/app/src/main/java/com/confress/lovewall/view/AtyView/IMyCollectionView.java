package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/15.
 */
public interface IMyCollectionView {
    void showLoading();
    void hideLoading();
    void Failure();
    User getCurrentUser();
}
