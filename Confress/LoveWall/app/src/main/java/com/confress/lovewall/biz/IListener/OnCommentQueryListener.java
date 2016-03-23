package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.Comment;
import com.confress.lovewall.model.MessageWall;

import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public interface OnCommentQueryListener {
    void Success(List<Comment> list);

    void Failure();
}
