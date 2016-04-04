package com.confress.lovewall.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.AnnomousPresenter;
import com.confress.lovewall.view.AtyView.IAnnomousView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/8.
 */
public class AnnomousActivity extends Activity implements IAnnomousView, View.OnClickListener {
    @Bind(R.id.back)
    Button back;
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.message_content)
    EditText messageContent;
    @Bind(R.id.id_progress)
    ProgressBar idProgress;
    private AnnomousPresenter annomousPresenter = new AnnomousPresenter(this, AnnomousActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annomous_message_wall);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void success() {
        T.showShort(AnnomousActivity.this, "匿名表白成功！");
    }

    @Override
    public void failure() {
        T.showShort(AnnomousActivity.this, "匿名表白失败！");
    }

    @Override
    public String getWallMessage() {
        return messageContent.getText().toString();
    }

    @Override
    public Context getContext() {
        return AnnomousActivity.this;
    }

    @Override
    public void EmptyOfMessage() {
        T.showShort(AnnomousActivity.this, "表白内容不能为空！");
    }

    @Override
    public User getUser() {
        return BmobUser.getCurrentUser(this, User.class);
    }

    @Override
    public void showLoading() {
        idProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        idProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void back() {
        AnnomousActivity.this.finish();
    }

    @Override
    public void NeedLogin() {
        T.showShort(this,"请先登录呦！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                back();
                break;
            case R.id.send:
                annomousPresenter.uploadData();
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        annomousPresenter=null;
    }
}
