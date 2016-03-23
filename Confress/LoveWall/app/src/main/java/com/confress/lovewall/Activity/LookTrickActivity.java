package com.confress.lovewall.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.BroadcastReceiver.MyPushMessageReceiver;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.LookTrickPresenter;
import com.confress.lovewall.view.AtyView.IlookTrickView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/18.
 */
public class LookTrickActivity extends Activity implements View.OnClickListener, IlookTrickView {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.friend_name)
    TextView friendName;
    @Bind(R.id.comment_msg)
    EditText commentMsg;
    @Bind(R.id.submit)
    FloatingActionButton submit;
    @Bind(R.id.tricks)
    TextView tricks;
    @Bind(R.id.friend_icon)
    CircleImageView friendIcon;
    private LookTrickPresenter presenter = new LookTrickPresenter(LookTrickActivity.this, this);
    private MsgGosn msgGosn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.looktrick_main);
        ButterKnife.bind(this);
//        msgGosn = (MsgGosn) getIntent().getSerializableExtra("msgGosn");
        msgGosn= MyPushMessageReceiver.msgGosn;
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        if (!TextUtils.isEmpty(msgGosn.getMsg())) {
            tricks.setText(msgGosn.getMsg());
        }
        if (!TextUtils.isEmpty(msgGosn.getNick())) {
            friendName.setText(msgGosn.getNick());
        }
        if (!TextUtils.isEmpty(msgGosn.getIcon())){
            Glide.with(LookTrickActivity.this).load(msgGosn.getIcon()).into(friendIcon);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                presenter.replyMsg(msgGosn.getId());
                break;
            case R.id.back:
                LookTrickActivity.this.finish();
                break;
        }
    }

    @Override
    public String getSendMsg() {
        return commentMsg.getText().toString();
    }

    @Override
    public void SetRecieveMsg() {
        tricks.setText("");
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
    public void ReplySuccess() {
        T.showShort(this, "回复成功！");
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
    protected void onDestroy() {
        super.onDestroy();
        presenter=null;
    }
}
