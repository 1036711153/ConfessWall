package com.confress.lovewall.biz.IListener;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/24.
 */
public interface OnUpdateCommentCountListener {
    void OnSuccess(int count);

    void OnFailed();

}
