package com.confress.lovewall.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.PhotoViewUtils;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.First_UserSettingActivityPresenter;
import com.confress.lovewall.view.AtyView.IFirstUserSettingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/14.
 */
public class FirstUserSettingActivity extends Activity implements View.OnClickListener, IFirstUserSettingView {
    @Bind(R.id.user_icon)
    CircleImageView userIcon;
    @Bind(R.id.tv_progress)
    TextView tvprogress;
    @Bind(R.id.change_image)
    LinearLayout changeImage;
    @Bind(R.id.nickname)
    EditText nickname;
    @Bind(R.id.age)
    EditText age;
    @Bind(R.id.tel)
    EditText tel;
    @Bind(R.id.submit)
    Button submit;
    //相册寻找照片
    private static final int IMAGE_REQUEST_CODE1 = 0;
    @Bind(R.id.radio_man)
    RadioButton radioMan;
    @Bind(R.id.radio_woman)
    RadioButton radioWoman;
    private First_UserSettingActivityPresenter userSettingActivityPresenter = new First_UserSettingActivityPresenter(this, FirstUserSettingActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_usersetting_main);
        ButterKnife.bind(this);
        changeImage.setOnClickListener(this);
        submit.setOnClickListener(this);
        userSettingActivityPresenter.ShowUserData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                userSettingActivityPresenter.UpdataUserData();
                Intent intent2 = new Intent(FirstUserSettingActivity.this, HomeActivity.class);
                startActivity(intent2);
                break;
            case R.id.change_image:
                //更换照片
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE1);
                break;
        }
    }

    @Override
    public String getNickName() {
        return nickname.getText().toString();
    }

    @Override
    public void setNickName(User user) {
        nickname.setText(user.getNick());
    }

    @Override
    public String getSex() {
        if (radioMan.isChecked()){
            return "男";
        }else {
            return "女";
        }
    }

    @Override
    public void setSex(User user) {
        if (user.getSex().equals("女")){
            radioWoman.setChecked(true);
        }else {
            radioMan.setChecked(true);
        }
    }

    @Override
    public int getAge() {
        return Integer.parseInt(age.getText().toString());
    }

    @Override
    public void setAge(User user) {
        if (user.getAge() == null) {
            age.setText(18 + "");
        } else {
            age.setText(user.getAge() + "");
        }
    }

    @Override
    public String getTel() {
        return tel.getText().toString();
    }

    @Override
    public void setTel(User user) {
        tel.setText(user.getTel());
    }

    @Override
    public String getUserIcon(User user) {
        return user.getIcon();
    }

    @Override
    public void setUserIcon(User user) {
        if (TextUtils.isEmpty(user.getIcon())) {
            userIcon.setImageResource(R.drawable.wall);
        } else {
            Glide.with(FirstUserSettingActivity.this).load(user.getIcon()).into(userIcon);
        }
    }

    @Override
    public User getCurrentUser() {
        return User.getCurrentUser(FirstUserSettingActivity.this, User.class);
    }

    @Override
    public void Success() {
        T.showShort(FirstUserSettingActivity.this, "更新个人信息成功！");
    }

    @Override
    public void Failure() {
        T.showShort(FirstUserSettingActivity.this, "更新个人信息失败！");
    }

    @Override
    public void UploadIconSuccess() {
        T.showShort(FirstUserSettingActivity.this, "更新头像成功！");
    }

    @Override
    public void UploadIconFailure() {
        T.showShort(FirstUserSettingActivity.this, "更新头像失败！");
    }

    @Override
    public void showtvProgress(int progress) {
        tvprogress.setText(progress + "%");
    }

    @Override
    public void successTvprogress() {
        tvprogress.setText("更新头像");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE1) {
            if (data == null) {
                return;
            } else {
                Uri uri = data.getData();
                Uri imageUri = PhotoViewUtils.convertUri(uri, "confress", FirstUserSettingActivity.this);
                userIcon.setImageURI(imageUri);
                userSettingActivityPresenter.updateIcon(PhotoViewUtils.getImagePath());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userSettingActivityPresenter = null;
    }
}
