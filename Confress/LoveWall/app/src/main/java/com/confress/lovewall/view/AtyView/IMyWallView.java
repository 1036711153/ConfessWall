package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/15.
 */
public interface IMyWallView {
    void Failure();
    User getCurrentUser();
    void UpdateAdapter(int size, List<MessageWall> messageWalls);
    void LoadOver();
}
