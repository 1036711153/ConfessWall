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
import com.confress.lovewall.biz.IListener.OnUpdateCommentCountListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.Comment;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
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
    private IMessageWallBiz messageWallBiz;
    private IUserBiz userBiz;

    public CommentPresenter(ICommentView commentView, Context context) {
        this.userBiz=new UserBiz();
        this.messageWallBiz=new MessageWallBiz();
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


    public void UpComment(final MessageWall messageWall){
        if (TextUtils.isEmpty(commentView.getCommentMsg())){
            commentView.EmptyMsg();
            return;
        }
        if (commentView.getCurrentUser()==null){
            commentView.NeedLogin();
            return;
        }
        commentBiz.PostComment(commentView.getCurrentUser(), commentView.getCommentMsg(), messageWall, new OnCommentListener() {
            @Override
            public void OnSuccess() {
                commentView.PostSuccess();
                messageWallBiz.AddCommentCount(messageWall, new OnUpdateCommentCountListener() {

                    @Override
                    public void OnSuccess(int count) {
                        commentView.UpdateCommentCount(count);
                    }

                    @Override
                    public void OnFailed() {

                    }
                }, context);
            }

            @Override
            public void OnFailed() {
                commentView.PostFailure();
            }
        }, context);
    }
    public  void  AddSupport(final MessageWall messageWall){
        messageWallBiz.AddSupport(messageWall, new OnUpdateCommentCountListener() {
            @Override
            public void OnSuccess(int count) {
                commentView.UpdateSupportCount(count);
            }

            @Override
            public void OnFailed() {
            }
        }, context);
    }
    public  void  DelSupport(final MessageWall messageWall){
        messageWallBiz.DelSupport(messageWall, new OnUpdateCommentCountListener() {
            @Override
            public void OnSuccess(int count) {
                commentView.UpdateSupportCount(count);
            }

            @Override
            public void OnFailed() {
            }
        }, context);
    }

    //添加收藏
    public void CollectionOp(final Context context,final MessageWall messageWall){
        userBiz.CollectionOp(commentView.getCurrentUser(), messageWall, new OnUpdateListener() {
            @Override
            public void OnSuccess(User user) {
                commentView.CollectionSuccess();
                messageWallBiz.AddCollection(messageWall, new OnUpdateCommentCountListener() {
                    @Override
                    public void OnSuccess(int count) {
                        commentView.UpdateCollectionCount(count);
                    }

                    @Override
                    public void OnFailed() {
                    }
                }, context);
            }
            @Override
            public void OnFailed() {
                commentView.CollectionFailure();
            }
        }, context);
    }

    //取消收藏
    public void DelCollection(final Context context,final MessageWall messageWall){
        userBiz.DelCollection(commentView.getCurrentUser(), messageWall, new OnUpdateListener() {
            @Override
            public void OnSuccess(User user) {
                commentView.DelCollectionSuccess();
                messageWallBiz.DelCollection(messageWall, new OnUpdateCommentCountListener() {
                    @Override
                    public void OnSuccess(int count) {
                        commentView.UpdateCollectionCount(count);
                    }

                    @Override
                    public void OnFailed() {

                    }
                }, context);
            }

            @Override
            public void OnFailed() {
                commentView.DelCollectionFailure();
            }
        }, context);
    }

}
