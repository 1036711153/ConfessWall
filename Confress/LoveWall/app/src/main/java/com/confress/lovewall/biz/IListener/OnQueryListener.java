package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.MessageWall;

import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public interface OnQueryListener {
    void Success(List<MessageWall> list);

    void Failure();
}
