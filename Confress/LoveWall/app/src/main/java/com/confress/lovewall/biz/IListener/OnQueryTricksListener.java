package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.Tricks;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public interface OnQueryTricksListener {
    void Success(List<Tricks> list);

    void Failure();
}
