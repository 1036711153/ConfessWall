package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IListener.OnUpdateListener;
import com.confress.lovewall.biz.IListener.OnUploadDataListener;
import com.confress.lovewall.biz.IListener.OnUploadPictureListener;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;

import java.util.List;

/**
 * Created by admin on 2016/3/11.
 */
public interface IMessageWallBiz {
    void uploadMessage(User user,String message,OnUploadDataListener uploadDataListener,Context context);
    void uploadAnnomousMessage(User user,String message,OnUploadDataListener uploadDataListener,Context context);
    void uploadPictureAndMessage(User user,String message,String picture_path,OnUploadDataListener uploadDataListener,Context context);
    void uploadPicture(String picturePath,OnUploadPictureListener onUploadPictureListener,Context context);
    //加载NEWS信息
    void NewRefreshQueryData(int page,OnQueryListener queryListener,Context context);
    void HotRefreshQueryData(int page,OnQueryListener queryListener,Context context);
    void RandomRefreshQueryData(int page,OnQueryListener queryListener,Context context);
    void AnonmousRefreshQueryData(int page,OnQueryListener queryListener,Context context);

    void QueryMyWallData(int page,User user,OnQueryListener queryListener,Context context);


//    void CollectionOp(User user,MessageWall messageWall,OnUpdateListener updateListener,Context context);
//    void DelCollection(User user,MessageWall messageWall,OnUpdateListener updateListener,Context context);

    void updateMessageCount(MessageWall messageWall,int colletioncount,int commentcount,int supportcount,OnUploadDataListener uploadDataListener,Context context);

}
