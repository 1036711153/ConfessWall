package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnQueryChattingMsgListener;
import com.confress.lovewall.biz.IListener.OnSaveMsgsListener;
import com.confress.lovewall.model.ChattingMsg;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 2016/3/27.
 */
public class ChattingMsgBiz implements IChattingMsgBiz {
    @Override
    public void QueryMsgs(int page,String uid1, String uid2, final OnQueryChattingMsgListener queryListener, Context context) {
        //条件一情况And
        BmobQuery<ChattingMsg> eq1 = new BmobQuery<ChattingMsg>();
        eq1.addWhereEqualTo("uid1", uid1);
        eq1.addWhereEqualTo("uid2", uid2);

        //条件2情况And
        BmobQuery<ChattingMsg> eq2 = new BmobQuery<ChattingMsg>();
        eq2.addWhereEqualTo("uid2", uid1);
        eq2.addWhereEqualTo("uid1", uid2);


        //条件一或者条件二合并情况OR
        List<BmobQuery<ChattingMsg>> queries = new ArrayList<>();
        queries.add(eq1);
        queries.add(eq2);

        BmobQuery<ChattingMsg> mainQuery = new BmobQuery<ChattingMsg>();
        mainQuery.or(queries);
        mainQuery.order("-createdAt");
        mainQuery.setLimit(10);
        mainQuery.setSkip(page * 10);
        mainQuery.findObjects(context, new FindListener<ChattingMsg>() {
            @Override
            public void onSuccess(List<ChattingMsg> list) {
                queryListener.Success(list);
            }

            @Override
            public void onError(int i, String s) {
                queryListener.Failure();
            }
        });
    }

    @Override
    public void SaveMsgs(String uid1, String uid2, String content, final OnSaveMsgsListener saveMsgsListener, Context context) {
        ChattingMsg msg = new ChattingMsg();
        msg.setUid1(uid1);
        msg.setUid2(uid2);
        msg.setContent(content);
        msg.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                saveMsgsListener.OnSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                saveMsgsListener.OnFailed();
            }
        });

    }
}
