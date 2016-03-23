package com.confress.lovewall.biz;

import android.content.Context;
import android.util.Log;

import com.confress.lovewall.biz.IListener.OnBindListener;
import com.confress.lovewall.model.MyBmobInstallation;
import com.confress.lovewall.model.User;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by admin on 2016/3/18.
 */
public class MyBmobInstallationBiz implements  IMyBmobInstallationBiz {
    //绑定设备与用户UID
    @Override
    public void UpdateMyBmobInstallationUser(final String uid, final OnBindListener bindListener, final Context context) {
        BmobQuery<MyBmobInstallation> query = new BmobQuery<MyBmobInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(context));
        query.findObjects(context, new FindListener<MyBmobInstallation>() {
            @Override
            public void onSuccess(List<MyBmobInstallation> object) {
                Log.e("MyBmobInstallation",""+object.size());
                // TODO Auto-generated method stub
                if(object.size() > 0){
                    MyBmobInstallation mbi = object.get(0);
                    mbi.setUid(uid);
                    mbi.update(context,new UpdateListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub

                            bindListener.OnSuccess();
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub

                            bindListener.OnFailed();
                        }
                    });
                }else{
                    //说明没有需要创建
                    Log.e("MyBmobInstallation","new MyBmobInstallation");

                }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                bindListener.OnFailed();
            }
        });
    }

}
