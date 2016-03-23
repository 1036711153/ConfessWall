package com.confress.lovewall.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.confress.lovewall.R;
import com.confress.lovewall.presenter.AtyPresenter.UserRegisterPresenter;
import com.confress.lovewall.view.AtyView.IUserRegisterView;



/**
 * Created by admin on 2016/3/7.
 */
public class RegisterActivity extends Activity implements IUserRegisterView,View.OnClickListener {
    private UserRegisterPresenter userRegisterPresenter=new UserRegisterPresenter(this,RegisterActivity.this);
    EditText username;
    EditText psd;
    EditText configPsd;
    EditText email;
    Button submit;
    ProgressBar registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initId();
        submit.setOnClickListener(this);
    }

    private void initId() {
        username= (EditText) findViewById(R.id.username);
        psd= (EditText) findViewById(R.id.psd);
        configPsd= (EditText) findViewById(R.id.config_psd);
        email= (EditText) findViewById(R.id.email);
        submit= (Button) findViewById(R.id.submit);
        registerProgress= (ProgressBar) findViewById(R.id.register_progress);
    }


    @Override
    public void Success() {
        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Failed() {
        Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        registerProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        registerProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void FinishAty() {
        RegisterActivity.this
                .finish();
    }

    @Override
    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public String getPsd() {
        return psd.getText().toString();
    }

    @Override
    public String getConPsd() {
        return configPsd.getText().toString();
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public Context getContext() {
        return RegisterActivity.this;
    }

    @Override
    public void ErrorOfUsnorPsdorEmail() {
        Toast.makeText(getApplicationContext(),"用户名,密码或者邮箱错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ErrorOfConfingerPsd() {
        Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
         case  R.id.submit:
           userRegisterPresenter.register();
          break;
      }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userRegisterPresenter=null;
    }
}
