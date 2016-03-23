package com.confress.lovewall.view.FragmentView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/17.
 */
public interface IHomeFragment2View {
    void Loading();
    void HideLoading();
    User getUser();
    void Failure();

}
