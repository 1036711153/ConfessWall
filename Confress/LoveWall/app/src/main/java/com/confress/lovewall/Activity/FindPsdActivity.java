package com.confress.lovewall.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.confress.lovewall.R;
import com.confress.lovewall.presenter.AtyPresenter.UserForgetPsdPresenter;
import com.confress.lovewall.view.AtyView.IUserFindPsdView;

/**
 * Created by admin on 2016/3/7.
 */
public class FindPsdActivity extends Activity implements IUserFindPsdView ,View.OnClickListener{
    private UserForgetPsdPresenter userForgetPsdPresenter=new UserForgetPsdPresenter(this,FindPsdActivity.this);
    private EditText email;
    private Button  submit;
    private ProgressBar id_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psd);
        initId();
    }

    private void initId() {
        email= (EditText) findViewById(R.id.email);
        submit= (Button) findViewById(R.id.submit);
        id_progress= (ProgressBar) findViewById(R.id.id_progress);
        submit.setOnClickListener(this);
    }

    @Override
    public void showLoading() {
        id_progress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        id_progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public void Sussccess() {
        Toast.makeText(getApplicationContext(), "找回成功!请去邮箱重置密码!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Failed() {
        Toast.makeText(getApplicationContext(), "找回密码失败!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EmptyOfEmail() {
        Toast.makeText(getApplicationContext(), "请输入密码!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
        case   R.id.submit:
          userForgetPsdPresenter.RememberPsd();
          break;
      }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userForgetPsdPresenter=null;
    }
}
