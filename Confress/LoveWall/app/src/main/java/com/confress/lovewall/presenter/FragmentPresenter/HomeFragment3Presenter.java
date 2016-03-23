package com.confress.lovewall.presenter.FragmentPresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.confress.lovewall.biz.IListener.OnQueryListener;
import com.confress.lovewall.biz.IMessageWallBiz;
import com.confress.lovewall.biz.IUserBiz;
import com.confress.lovewall.biz.MessageWallBiz;
import com.confress.lovewall.biz.UserBiz;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.view.FragmentView.IHomeFragment3View;
import com.confress.lovewall.view.FragmentView.IHotFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/3/13.
 */
public class HomeFragment3Presenter {
    private IUserBiz userBiz;
    private IMessageWallBiz messageWallBiz;
    private IHomeFragment3View homeFragment3View;
    private Context context;

    public HomeFragment3Presenter(IHomeFragment3View homeFragment3View, Context context) {
        this.userBiz = new UserBiz();
        this.messageWallBiz = new MessageWallBiz();
        this.homeFragment3View = homeFragment3View;
        this.context = context;
    }

    //下拉刷新和第一次加载页面方法
    public List<MessageWall> FirstLoadingData(final Handler mhandler,Context context) {
        final   List<MessageWall>messageWalls=new ArrayList<>();
        messageWallBiz.RandomRefreshQueryData(0, new OnQueryListener() {
            @Override
            public void Success(List<MessageWall> list) {
                if (list.size() > 0) {
                    messageWalls.clear();
                    for (MessageWall messageWall : list) {
                        messageWalls.add(messageWall);
                    }
                    Message message=new Message();
                    message.what=1;//0代表加载失败 1代表加载成功
                    message.obj=messageWalls;
                    mhandler.sendMessage(message);
                } else {
                    Message message=new Message();
                    message.what=1;//0代表加载失败 1代表加载成功
                    message.obj=messageWalls;
                    mhandler.sendMessage(message);
                }
            }
            @Override
            public void Failure() {
                Message message=new Message();
                message.what=0;//0代表加载失败 1代表加载成功
                mhandler.sendMessage(message);
            }
        }, context);
        return messageWalls;
    }
}
