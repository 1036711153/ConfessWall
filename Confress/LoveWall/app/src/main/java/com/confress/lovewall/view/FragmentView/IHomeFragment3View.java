package com.confress.lovewall.view.FragmentView;

import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public interface IHomeFragment3View {
    void Failure();
    User getCurrentUser();
    void UpdateAdapter(int size, List<MessageWall> ImessageWall);
}
