package com.confress.lovewall.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.UserLoginPresenter;
import com.confress.lovewall.view.AtyView.IUserLoginView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/7.
 */
public class LoginActivity extends Activity implements IUserLoginView, View.OnClickListener {

    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this,LoginActivity.this);

    private ProgressBar loginProgress;
    private EditText UserName;
    private EditText UserPsd;
    private Button Login;
    private TextView Btn_Register;
    private TextView forget_psd;

    private LinearLayout tvWeibo;
    private LinearLayout tvQq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initID();
        Bmob.initialize(this, "766e2d78b852727f6a4be394c0af7237");
    }

    private void initID() {
        tvWeibo= (LinearLayout) findViewById(R.id.tvWeibo);
        tvQq= (LinearLayout) findViewById(R.id.tvQq);
        loginProgress = (ProgressBar) findViewById(R.id.login_progress);
        UserName = (EditText) findViewById(R.id.User_Name);
        UserPsd = (EditText) findViewById(R.id.User_Psd);
        Login = (Button) findViewById(R.id.Login);
        Btn_Register= (TextView) findViewById(R.id.register);
        forget_psd= (TextView) findViewById(R.id.forget_psd);
        Login.setOnClickListener(this);
        Btn_Register.setOnClickListener(this);
        forget_psd.setOnClickListener(this);
        tvWeibo.setOnClickListener(this);
        tvQq.setOnClickListener(this);
        User bmobUser = BmobUser.getCurrentUser(this, User.class);
        if (bmobUser!=null) {
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
            return;
        }
    }

    @Override
    public String getUserName() {
        return UserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return UserPsd.getText().toString();
    }

    @Override
    public void showLoading() {
        loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loginProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void toHomeActivity(User user) {
        mUserLoginPresenter.toHomeActivty();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(LoginActivity.this
                , "用户名或者密码错误", Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void ErrorOfUsnAndPsd() {
        if (TextUtils.isEmpty(UserName.getText().toString()) || TextUtils.isEmpty(UserPsd.getText().toString())) {
            showFailedError();
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login:
                mUserLoginPresenter.login();
                break;
            case  R.id.register:
                mUserLoginPresenter.toRegisterActivity();
                break;
            case R.id.forget_psd:
                mUserLoginPresenter.toRememberPsdActivity();
                break;
            case R.id.tvQq:
                T.showShort(getApplicationContext(),"Sorry，改功能还未开放！");
                break;
            case R.id.tvWeibo:
                T.showShort(getApplicationContext(),"Sorry，改功能还未开放！");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserLoginPresenter=null;
    }
}
