package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnCommentListener;
import com.confress.lovewall.biz.IListener.OnCommentQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.model.Comment;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 2016/3/16.
 */
public class CommentBiz implements ICommentBiz {
    @Override
    public void PostComment(User user, String message, MessageWall iMessageWall, final OnCommentListener commentListener, Context context) {
        MessageWall messageWall = new MessageWall();
        messageWall.setObjectId(iMessageWall.getObjectId());
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent(message);
        comment.setMessageWall(messageWall);
        comment.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                commentListener.OnSuccess();

            }

            @Override
            public void onFailure(int i, String s) {
                commentListener.OnFailed();
            }
        });
    }


    //查询我的收藏
    @Override
    public void QueryCommentData(int page, User user, MessageWall ImessageWall,final OnCommentQueryListener queryListener, Context context) {
        final BmobQuery<Comment> query = new BmobQuery<Comment>();
        MessageWall  messageWall1=new MessageWall();
        messageWall1.setObjectId(ImessageWall.getObjectId());
        query.addWhereEqualTo("messageWall", new BmobPointer(messageWall1));
        query.include("user");
        query.order("-createdAt");
        query.setLimit(20);
        query.setSkip(page * 20);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryListener.Failure();
            }
        });


    }
}
