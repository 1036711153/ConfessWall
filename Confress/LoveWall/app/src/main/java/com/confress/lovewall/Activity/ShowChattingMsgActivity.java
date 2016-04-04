package com.confress.lovewall.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.confress.lovewall.Adapter.ChattingAdapter;
import com.confress.lovewall.Adapter.NewsAndHotAdapter;
import com.confress.lovewall.BroadcastReceiver.MyPushMessageReceiver;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.ChattingMsg;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.ShowChattingPresenter;
import com.confress.lovewall.view.AtyView.IShowChattingMsgView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/18.
 */
public class ShowChattingMsgActivity extends Activity implements View.OnClickListener, IShowChattingMsgView{
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.mRefresh)
    MaterialRefreshLayout mRefresh;
    @Bind(R.id.comment_msg)
    EditText commentMsg;
    @Bind(R.id.submit)
    FloatingActionButton submit;
    private ShowChattingPresenter  presenter=new ShowChattingPresenter(ShowChattingMsgActivity.this,this);
    private static ChattingAdapter adapter;
    public static List<ChattingMsg> chattingMsgs;
    private String friend_user_icon;
    private String friend_user_id;
    private int currentpage = 1;
    private static  String uid;
    private static  ListView mListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.looktrick_main);
        ButterKnife.bind(this);
        mListView= (ListView) findViewById(R.id.mListView);
        friend_user_icon = (String) getIntent().getSerializableExtra("friend_user_icon");
        friend_user_id = (String) getIntent().getSerializableExtra("friend_user_id");
        uid=getCurrentUser().getObjectId();
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        chattingMsgs=new ArrayList<>();
        adapter=new ChattingAdapter(chattingMsgs,this,getCurrentUser().getIcon(),friend_user_icon,getCurrentUser().getObjectId());
        mListView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                presenter.FirstLoadingData(ShowChattingMsgActivity.this,getCurrentUser().getObjectId(),friend_user_id);
                currentpage = 1;
            }
        }).start();
        //刷新加载数据
        mRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...与第一次加载数据一样
                presenter.PullDownRefreshqueryData(currentpage, getCurrentUser().getObjectId(), friend_user_id, ShowChattingMsgActivity.this);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...

                // 结束上拉刷新...
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                presenter.replyMsg(getCurrentUser().getObjectId(),friend_user_id);
                break;
            case R.id.back:
                ShowChattingMsgActivity.this.finish();
                break;
        }
    }

    @Override
    public String getSendMsg() {
        return commentMsg.getText().toString();
    }

    @Override
    public void SetRecieveMsg() {

    }

    @Override
    public void EmptyOfReplyMsg() {
        T.showShort(this, "回复内容不能为空");
    }

    @Override
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(this, User.class);
    }

    @Override
    public void ReplySuccess(String msg) {
        ChattingMsg chattingMsg=new ChattingMsg();
        chattingMsg.setUid1(uid);
        chattingMsg.setUid2(friend_user_id);
        chattingMsg.setContent(msg);
        chattingMsgs.add(chattingMsg);
        adapter.notifyDataSetChanged();
        mListView.setSelection(adapter.getCount());
    }

    @Override
    public void SetEmptyOfMsg() {
        commentMsg.setText("");
    }

    //初始化信息
    @Override
    public void InitFriendsData() {

    }

    @Override
    public void NeedLogin() {
        T.showShort(this, "请先登录呦！");
    }

    @Override
    public void UpdateAdapter(int size, List<ChattingMsg> IchattingMsgs) {
        if (size==0){
        }else if (size==1){
            chattingMsgs.addAll(IchattingMsgs);
            if (chattingMsgs.size()>0){
                adapter.notifyDataSetChanged();
                mListView.setSelection(adapter.getCount());
            }else {
//                T.showShort(getApplicationContext(), "没有数据！");
            }
        }else  if (size==2){
            if (chattingMsgs.size() > 0) {
                chattingMsgs.addAll(0, IchattingMsgs);
                adapter.notifyDataSetChanged();
                currentpage++;
            }
        }else  if (size==3){
            T.showShort(getApplicationContext(), "没有更多聊天内容了！");
        }
        LoadOver();
    }

    @Override
    public void LoadOver() {
        if (mRefresh!=null) {
            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
        chattingMsgs=null;
    }

    //收到的消息
    public static void getRequest(MsgGosn msgGosn) {
        ChattingMsg chattingMsg=new ChattingMsg();
        chattingMsg.setUid1(msgGosn.getId());
        chattingMsg.setUid2(uid);
        chattingMsg.setContent(msgGosn.getMsg());
        chattingMsgs.add(chattingMsg);
        adapter.notifyDataSetChanged();
        mListView.setSelection(adapter.getCount());
    }
}
