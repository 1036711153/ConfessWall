package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.confress.lovewall.biz.CommentBiz;
import com.confress.lovewall.biz.ICommentBiz;
import com.confress.lovewall.biz.IListener.OnCommentListener;
import com.confress.lovewall.biz.IListener.OnCommentQueryListener;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.Comment;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.view.AtyView.ICommentView;
import com.confress.lovewall.view.AtyView.IMyCollectionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/3/15.
 */
public class CommentPresenter {
    private ICommentBiz commentBiz;
    private ICommentView commentView;
    private Context context;

    public CommentPresenter(ICommentView commentView, Context context) {
        this.commentBiz = new CommentBiz();
        this.commentView = commentView;
        this.context = context;
    }

    //第一次加载数据OR刷新加载数据
    public void FirstLoadingData(final Handler mhandler, Context context, MessageWall messageWall) {
        final List<Comment> comments = new ArrayList<>();
        commentBiz.QueryCommentData(0, commentView.getCurrentUser(), messageWall, new OnCommentQueryListener() {
            @Override
            public void Success(List<Comment> list) {
                if (list.size() > 0) {
                    comments.clear();
                    for (Comment comment : list) {
                        comments.add(comment);
                    }
                    Message message = new Message();
                    message.what = 1;//0代表加载失败 1代表加载成功
                    message.obj = comments;
                    mhandler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = 1;//0代表加载失败 1代表加载成功
                    message.obj = comments;
                    mhandler.sendMessage(message);
                }
            }

            @Override
            public void Failure() {
                Message message = new Message();
                message.what = 0;//0代表加载失败 1代表加载成功
                mhandler.sendMessage(message);
            }
        }, context);
    }


    //上拉刷新加载
    public void PullDownRefreshqueryData(final Handler mhandler, int page, final Context context, MessageWall messageWall) {
        final List<Comment> comments = new ArrayList<>();
        commentBiz.QueryCommentData(page, commentView.getCurrentUser(), messageWall, new OnCommentQueryListener() {
            @Override
            public void Success(List<Comment> list) {
                if (list.size() > 0) {
                    comments.clear();
                    for (Comment comment : list) {
                        comments.add(comment);
                    }
                    Message message = new Message();
                    message.what = 2;//0代表加载失败 1代表加载成功
                    message.obj = comments;
                    mhandler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = 3;//0代表加载失败 1代表加载成功
                    mhandler.sendMessage(message);
                }
            }

            @Override
            public void Failure() {
                Message message = new Message();
                message.what = 0;//0代表加载失败 1代表加载成功
                mhandler.sendMessage(message);
            }
        }, context);
    }


    public void UpComment(MessageWall messageWall){
        if (TextUtils.isEmpty(commentView.getCommentMsg())){
            commentView.EmptyMsg();
            return;
        }
        commentBiz.PostComment(commentView.getCurrentUser(),commentView.getCommentMsg(),messageWall, new OnCommentListener() {
            @Override
            public void OnSuccess() {
              commentView.PostSuccess();
            }

            @Override
            public void OnFailed() {
              commentView.PostFailure();
            }
        },context);
    }


}
