package com.confress.lovewall.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.confress.lovewall.R;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.UserLoginPresenter;
import com.confress.lovewall.view.AtyView.IUserLoginView;

import java.util.HashMap;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;


/**
 * Created by admin on 2016/3/7.
 */
public class LoginActivity extends Activity implements IUserLoginView, View.OnClickListener,PlatformActionListener,Callback {
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    private static final String TAG = "LoginActivity";
    private Platform mPlatform;


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
        ShareSDK.initSDK(this);
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
        LoginActivity.this.finish();
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
    public void toSettingAty(String nickname,String icon) {
        Intent intent=new Intent(LoginActivity.this,FirstUserSettingActivity.class);
        startActivity(intent);
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
                authorize(new QZone(this));
                break;
            case R.id.tvWeibo:
                authorize(new SinaWeibo(this));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserLoginPresenter=null;
        ShareSDK.stopSDK(this);
        if (mPlatform!=null) {
            mPlatform.removeAccount();
            mPlatform = null;
        }
    }


    private void authorize(Platform plat) {
        Log.i(TAG, "authorize执行了");
        mPlatform=plat;
        if (plat == null) {
            popupOthers();
            return;
        }

        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                Log.i(TAG, "id:" + userId);
                Log.i(TAG, "getExpiresIn:" + plat.getDb().getExpiresIn());
                Log.i(TAG, "getExpiresTime:" + plat.getDb().getExpiresTime());
                Log.i(TAG, "getPlatformNname:"
                        + plat.getDb().getPlatformNname());
                Log.i(TAG, "getPlatformVersion:"
                        + plat.getDb().getPlatformVersion());
                Log.i(TAG, "getToken:" + plat.getDb().getToken());
                Log.i(TAG, "getTokenSecret:" + plat.getDb().getTokenSecret());
                Log.i(TAG, "getUserIcon:" + plat.getDb().getUserIcon());
                Log.i(TAG, "getUserId:" + plat.getDb().getUserId());
                Log.i(TAG, "getUserName:" + plat.getDb().getUserName());
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(true);
        plat.showUser(null);
    }

    private void popupOthers() {
        Log.i(TAG, "popupOthers执行了");
        Dialog dlg = new Dialog(this);
        View dlgView = View.inflate(this, R.layout.other_plat_dialog, null);
        View tvFacebook = dlgView.findViewById(R.id.tvFacebook);
        tvFacebook.setTag(dlg);
        tvFacebook.setOnClickListener(this);
        View tvTwitter = dlgView.findViewById(R.id.tvTwitter);
        tvTwitter.setTag(dlg);
        tvTwitter.setOnClickListener(this);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(dlgView);
        dlg.show();
    }

    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        Log.i(TAG, "onComplete执行了");
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
        }
        Log.e(TAG, res.toString());
    }

    public void onError(Platform platform, int action, Throwable t) {
        Log.i(TAG, "onError执行了");
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        Log.i(TAG, "onCancel执行了");
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    private void login(String plat, String userId,
                       HashMap<String, Object> userInfo) {
        Log.i(TAG, "login执行了");
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
            }
            break;
            case MSG_LOGIN: {
                //第三方登录成功。。。
                mUserLoginPresenter.Third_login(mPlatform.getDb().getUserId(),mPlatform.getDb().getUserName(),mPlatform.getDb().getUserIcon());
                mPlatform.removeAccount();
            }
            break;
            case MSG_AUTH_CANCEL: {
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT)
                        .show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT)
                        .show();
            }
            break;
        }
        return false;
    }
}
