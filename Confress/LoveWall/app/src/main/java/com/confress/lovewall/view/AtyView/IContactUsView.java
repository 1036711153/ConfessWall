package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/15.
 */
public interface IContactUsView {
    User getUser();
    String getContent();
    void Success();
    void Failure();
}
