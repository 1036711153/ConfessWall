package com.confress.lovewall.view.FragmentView;

import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/17.
 */
public interface IHomeFragment2View {
    void LoadOver();
    User getUser();
    void Failure();
    void NeedLogin();
    void UpdateAdapter(int size, List<User> Iusers);

}
