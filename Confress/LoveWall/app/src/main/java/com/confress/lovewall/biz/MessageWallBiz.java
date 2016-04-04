package com.confress.lovewall.biz;

import android.content.Context;
import android.util.Log;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnUpdateCommentCountListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.biz.IListener.OnUploadPictureListener;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by admin on 2016/3/11.
 */
public class MessageWallBiz implements IMessageWallBiz {

    //上传文字
    @Override
    public void uploadMessage(User user, String message, final OnUploadDataListener uploadDataListener, Context context) {
        CreateMessageWall(user, message, false, uploadDataListener, context, null);

    }

    @Override
    public void uploadAnnomousMessage(User user, String message, OnUploadDataListener uploadDataListener, Context context) {
        CreateMessageWall(user, message, true, uploadDataListener, context, null);
    }

    //上传文字和图片
    @Override
    public void uploadPictureAndMessage(User user, String message, String uploadPicturePath, final OnUploadDataListener picuploadDataListener, Context context) {
        CreateMessageWall(user, message, false, picuploadDataListener, context, uploadPicturePath);
    }


    private void CreateMessageWall(User user, String message, boolean isannomous, final OnUploadDataListener picuploadDataListener, Context context, String uploadPicturePath) {
        MessageWall messageWall = new MessageWall();
        messageWall.setCollection_count(0);
        messageWall.setComment_count(0);
        messageWall.setSupport_count(0);
        messageWall.setConfess_image(uploadPicturePath);
        messageWall.setIs_Anonymous(isannomous);
        messageWall.setUser(user);
//        messageWall.setUserIcon(user.getIcon());
//        messageWall.setUserName(user.getNick());
        messageWall.setConfess_content(message);
        //上传数据
        messageWall.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                picuploadDataListener.OnSusscess();
            }

            @Override
            public void onFailure(int i, String s) {
                picuploadDataListener.OnFailed();
            }
        });
    }

    //上传照片
    @Override
    public void uploadPicture(final String picturePath, final OnUploadPictureListener uploadPictureListener, final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BTPFileResponse response = BmobProFile.getInstance(
                        context).upload(picturePath,
                        new UploadListener() {
                            @Override
                            public void onSuccess(String fileName,
                                                  String url, BmobFile file) {
                                uploadPictureListener.OnSusscess(file);
                            }

                            @Override
                            public void onProgress(int progress) {
                                uploadPictureListener.OnProgress(progress);
                            }

                            @Override
                            public void onError(int statuscode,
                                                String errormsg) {
                                uploadPictureListener.OnFailed();
                            }
                        });
            }
        }).start();
    }

    //下拉刷新
    @Override
    public void NewRefreshQueryData(int page, final OnQueryListener queryListener, final Context context) {
        final BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.addWhereNotEqualTo("Confess_content", "");
        query.addWhereEqualTo("Is_Anonymous", false);
        query.include("user,likes");
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(page * 10);
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);    // 先从缓存获取数据，如果没有，再从网络获取。
        query.findObjects(context, new FindListener<MessageWall>() {
            @Override
            public void onSuccess(List<MessageWall> list) {
                Log.e("RefreshQueryData", "onSuccess");
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("RefreshQueryData", s);
                queryListener.Failure();
            }
        });


    }

    //上拉加载数据
    @Override
    public void HotRefreshQueryData(int page, final OnQueryListener queryListener, Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.addWhereNotEqualTo("Confess_content", "");
        query.addWhereEqualTo("Is_Anonymous", false);
        query.include("user,likes");
        query.order("-createdAt");
        query.order("-Collection_count");
        query.order("-Comment_count");
        query.order("-Support_count");
        query.setLimit(20);
        query.setSkip(page * 20);
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);    // 先从缓存获取数据，如果没有，再从网络获取。
        query.findObjects(context, new FindListener<MessageWall>() {
            @Override
            public void onSuccess(List<MessageWall> list) {
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryListener.Failure();
            }
        });
    }

    @Override
    public void RandomRefreshQueryData(int page, final OnQueryListener queryListener, Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.addWhereContains("Confess_image", "http");
        query.include("user,likes");
        query.order("-createdAt");
        query.setLimit(4);
        query.findObjects(context, new FindListener<MessageWall>() {
            @Override
            public void onSuccess(List<MessageWall> list) {
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryListener.Failure();
            }
        });
    }

    @Override
    public void AnonmousRefreshQueryData(int page, final OnQueryListener queryListener, Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.addWhereNotEqualTo("Confess_content", "");
        query.addWhereEqualTo("Is_Anonymous", true);
        query.order("-Collection_count");
        query.order("-Comment_count");
        query.order("-Support_count");
        query.order("-createdAt");
        query.setLimit(20);
        query.setSkip(page * 20);
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);    // 先从缓存获取数据，如果没有，再从网络获取。
        query.findObjects(context, new FindListener<MessageWall>() {
            @Override
            public void onSuccess(List<MessageWall> list) {
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryListener.Failure();
            }
        });
    }

    @Override
    public void QueryMyWallData(int page,User user, final OnQueryListener queryListener, Context context) {
        final BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        User muser=new User();
        muser.setObjectId(user.getObjectId());
        query.addWhereEqualTo("user", new BmobPointer(muser));
        query.include("user,likes");
        query.order("-createdAt");
        query.setLimit(20);
        query.setSkip(page * 20);
        query.findObjects(context, new FindListener<MessageWall>() {
            @Override
            public void onSuccess(List<MessageWall> list) {
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryListener.Failure();
            }
        });

    }



//    //收藏操作
//    @Override
//    public void CollectionOp(final User user, MessageWall ImessageWall, final OnUpdateListener updateListener, Context context) {
//        MessageWall messageWall=new MessageWall();
//        messageWall.setObjectId(ImessageWall.getObjectId());
//        BmobRelation relation=new BmobRelation();
//        relation.add(user);
//        messageWall.setLikes(relation);
//        messageWall.update(context, new UpdateListener() {
//            @Override
//            public void onSuccess() {
//                updateListener.OnSuccess(user);
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                updateListener.OnFailed();
//            }
//        });
//
//    }


//    //Del收藏操作
//    @Override
//    public void DelCollection(final User user, MessageWall ImessageWall, final OnUpdateListener updateListener, Context context) {
//        MessageWall messageWall=new MessageWall();
//        messageWall.setObjectId(ImessageWall.getObjectId());
//        BmobRelation relation=new BmobRelation();
//        relation.remove(user);
//        messageWall.setLikes(relation);
//        messageWall.update(context, new UpdateListener() {
//            @Override
//            public void onSuccess() {
//                updateListener.OnSuccess(user);
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                updateListener.OnFailed();
//            }
//        });
//
//    }

    @Override
    public void updateMessageCount(MessageWall messageWall,int colletioncount, int commentcount, int supportcount, final OnUploadDataListener uploadDataListener, Context context) {
        messageWall.setCollection_count(colletioncount);
//        List<BmobPointer> objects = messageWall.getLikes().getObjects();
        messageWall.setComment_count(commentcount);
        messageWall.setSupport_count(supportcount);
        messageWall.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                uploadDataListener.OnSusscess();
            }

            @Override
            public void onFailure(int i, String s) {
               uploadDataListener.OnFailed();
            }
        });
    }

    @Override
    public void AddCommentCount(final MessageWall ImessageWall,final OnUpdateCommentCountListener uploadDataListener,final Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.getObject(context, ImessageWall.getObjectId(), new GetListener<MessageWall>() {
            @Override
            public void onSuccess(final MessageWall messageWall) {
                ImessageWall.setComment_count((messageWall.getComment_count() + 1));
                ImessageWall.update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        uploadDataListener.OnSuccess((messageWall.getComment_count() + 1));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        uploadDataListener.OnFailed();
                    }
                });
            }
            @Override
            public void onFailure(int i, String s) {
                uploadDataListener.OnFailed();
            }
        });
    }


    @Override
    public void DelCommentCount(final MessageWall ImessageWall,final OnUpdateCommentCountListener uploadDataListener,final Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.getObject(context, ImessageWall.getObjectId(), new GetListener<MessageWall>() {
            @Override
            public void onSuccess(final MessageWall messageWall) {
                ImessageWall.setComment_count((messageWall.getComment_count() - 1));
                ImessageWall.update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        uploadDataListener.OnSuccess((messageWall.getComment_count() - 1));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        uploadDataListener.OnFailed();
                    }
                });
            }
            @Override
            public void onFailure(int i, String s) {
                uploadDataListener.OnFailed();
            }
        });
    }

    @Override
    public void AddSupport(final  MessageWall ImessageWall,final OnUpdateCommentCountListener uploadDataListener,final Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.getObject(context, ImessageWall.getObjectId(), new GetListener<MessageWall>() {
            @Override
            public void onSuccess(final MessageWall messageWall) {
                ImessageWall.setSupport_count((messageWall.getSupport_count()+1));
                ImessageWall.update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        uploadDataListener.OnSuccess((messageWall.getSupport_count() + 1));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        uploadDataListener.OnFailed();
                    }
                });
            }
            @Override
            public void onFailure(int i, String s) {
                uploadDataListener.OnFailed();
            }
        });
    }
    @Override
    public void DelSupport(final  MessageWall ImessageWall,final OnUpdateCommentCountListener uploadDataListener,final Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.getObject(context, ImessageWall.getObjectId(), new GetListener<MessageWall>() {
            @Override
            public void onSuccess(final MessageWall messageWall) {
                ImessageWall.setSupport_count((messageWall.getSupport_count() - 1));
                ImessageWall.update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        uploadDataListener.OnSuccess((messageWall.getSupport_count() -1));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        uploadDataListener.OnFailed();
                    }
                });
            }
            @Override
            public void onFailure(int i, String s) {
                uploadDataListener.OnFailed();
            }
        });
    }

    @Override
    public void AddCollection(final  MessageWall ImessageWall,final OnUpdateCommentCountListener uploadDataListener,final Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.getObject(context, ImessageWall.getObjectId(), new GetListener<MessageWall>() {
            @Override
            public void onSuccess(final MessageWall messageWall) {
                ImessageWall.setCollection_count((messageWall.getCollection_count() + 1));
                ImessageWall.update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        uploadDataListener.OnSuccess((messageWall.getCollection_count() + 1));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        uploadDataListener.OnFailed();
                    }
                });
            }
            @Override
            public void onFailure(int i, String s) {
                uploadDataListener.OnFailed();
            }
        });
    }


    @Override
    public void DelCollection(final  MessageWall ImessageWall,final OnUpdateCommentCountListener uploadDataListener,final Context context) {
        BmobQuery<MessageWall> query = new BmobQuery<MessageWall>();
        query.getObject(context, ImessageWall.getObjectId(), new GetListener<MessageWall>() {
            @Override
            public void onSuccess(final MessageWall messageWall) {
                ImessageWall.setCollection_count((messageWall.getCollection_count() -1));
                ImessageWall.update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        uploadDataListener.OnSuccess((messageWall.getCollection_count() - 1));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        uploadDataListener.OnFailed();
                    }
                });
            }
            @Override
            public void onFailure(int i, String s) {
                uploadDataListener.OnFailed();
            }
        });
    }


}
