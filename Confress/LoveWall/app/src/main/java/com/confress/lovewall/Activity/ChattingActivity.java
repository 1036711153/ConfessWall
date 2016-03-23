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
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.ChattingPresenter;
import com.confress.lovewall.view.AtyView.IChattingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/17.
 */
public class ChattingActivity extends Activity implements IChattingView, View.OnClickListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.friend_name)
    TextView friendName;
    @Bind(R.id.tricks)
    EditText tricks;
    @Bind(R.id.submit)
    FloatingActionButton submit;
    @Bind(R.id.friend_icon)
    CircleImageView friendIcon;
    private User friend_user;
    private ChattingPresenter presenter = new ChattingPresenter(this, ChattingActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_main);
        ButterKnife.bind(this);
        friend_user = (User) getIntent().getSerializableExtra("user");
        if (!TextUtils.isEmpty(friend_user.getNick())) {
            friendName.setText(friend_user.getNick());
        }
        if (!TextUtils.isEmpty(friend_user.getIcon())){
            Glide.with(ChattingActivity.this).load(friend_user.getIcon()).into(friendIcon);
        }

        submit.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    @Override
    public void back() {
        ChattingActivity.this.finish();
    }

    @Override
    public String getMsg() {
        return tricks.getText().toString();
    }

    @Override
    public void SendError() {
        T.showShort(this, "发送消息失败！！");
    }

    @Override
    public void EmptyOfMsg() {
        T.showShort(this, "消息内容不能为空！！");
    }

    @Override
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(ChattingActivity.this, User.class);
    }

    @Override
    public User getFriendUser() {
        return friend_user;
    }

    @Override
    public void SetFriendUser(User friend_user) {
        this.friend_user = friend_user;
    }

    @Override
    public void SetEmptyMsg() {
        tricks.setText("");
    }

    @Override
    public void SendSuccess() {
        T.showShort(this, "发送悄悄话成功！！");
        back();
    }

    public void Failure() {
        T.showShort(this, "加载数据失败！！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                presenter.sendMsg();
                break;
            case R.id.back:
                back();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter=null;
    }
}
