package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/15.
 */
public interface IUserWallView {
    void Failure();
    User getWallUser();
    User getCurrentUser();
    void AttentionSuccess();
    void AttentionFailure();
    void DisAttentionSuccess();
    void DisAttentionFailure();
    void ErrorOfAttention();
    void UpdateAdapter(int size, List<MessageWall> messageWalls);
    void LoadOver();
}
