package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnCommentListener;
import com.confress.lovewall.biz.IListener.OnCommentQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/16.
 */
public interface ICommentBiz {
    void PostComment(User user,String comment,MessageWall iMessageWall,OnCommentListener commentListener,Context context);
    void QueryCommentData(int page,User user,MessageWall messageWall,OnCommentQueryListener queryListener,Context context);


}
