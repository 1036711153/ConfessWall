package com.confress.lovewall.Activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.ContactUsPresenter;
import com.confress.lovewall.view.AtyView.IContactUsView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/14.
 */
public class ContactUsActivity extends AppCompatActivity implements IContactUsView, View.OnClickListener {
    @Bind(R.id.backdrop)
    ImageView backdrop;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.feedback)
    EditText feedback;
    @Bind(R.id.submit)
    FloatingActionButton submit;
    private ContactUsPresenter contactUsPresenter=new ContactUsPresenter(this,ContactUsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus_main);
        ButterKnife.bind(this);
        initUserData();
        submit.setOnClickListener(this);
    }

    private void initUserData() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUsActivity.this.finish();

            }
        });
        collapsingToolbar.setTitle("FOREVER LOVE");
    }


    @Override
    public User getUser() {
        return BmobUser.getCurrentUser(this, User.class);
    }

    @Override
    public String getContent() {
        return feedback.getText().toString();
    }

    @Override
    public void Success() {
        T.showShort(this, "谢谢您的意见！");
        this.finish();
    }

    @Override
    public void Failure() {
        T.showShort(this, "Sorry，意见没有提交成功！");
    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.submit:
                  contactUsPresenter.uploadSuggestion();
                  break;
          }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactUsPresenter=null;
    }
}
