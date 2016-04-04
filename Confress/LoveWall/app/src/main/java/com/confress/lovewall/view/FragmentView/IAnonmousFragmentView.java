package com.confress.lovewall.view.FragmentView;

import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public interface IAnonmousFragmentView {
    void LoadOver();
    User getCurrentUser();
    void CollectionSuccess();
    void CollectionFailure();
    void DelCollectionSuccess();
    void DelCollectionFailure();
    void UpdateAdapter(int size, List<MessageWall> messageWalls);
}
