package com.confress.lovewall.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.Activity.ContactUsActivity;
import com.confress.lovewall.Activity.MyCollectionActivity;
import com.confress.lovewall.Activity.MyTrickAcivity;
import com.confress.lovewall.Activity.MyWallActivity;
import com.confress.lovewall.Activity.UserSettingActivity;
import com.confress.lovewall.R;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.FragmentPresenter.HomeFragment4Presenter;
import com.confress.lovewall.view.FragmentView.IHomeFragment4View;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/7.
 */
public class HomeFragment4 extends Fragment implements View.OnClickListener, IHomeFragment4View {
    View root;
    @Bind(R.id.user_icon)
    CircleImageView userIcon;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.login)
    LinearLayout login;
    @Bind(R.id.my_wall)
    CardView myWall;
    @Bind(R.id.center_user_nick)
    TextView centerUserNick;

    @Bind(R.id.collection)
    CardView collection;
    @Bind(R.id.contact_us)
    CardView contactUs;
    @Bind(R.id.user_setting)
    CardView userSetting;
    @Bind(R.id.save_exit)
    CardView saveExit;


    private HomeFragment4Presenter homeFragment4Presenter = new HomeFragment4Presenter(this, getActivity());
    private static final int REQUEST_CODE = 1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_fragment4, container, false);
        ButterKnife.bind(this, root);
        login.setOnClickListener(this);
        userSetting.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        collection.setOnClickListener(this);
        saveExit.setOnClickListener(this);
        myWall.setOnClickListener(this);
        homeFragment4Presenter.InitUserData();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        homeFragment4Presenter=null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivityForResult(new Intent(getActivity(), UserSettingActivity.class), REQUEST_CODE);
                break;
            case R.id.user_setting:
                startActivityForResult(new Intent(getActivity(), UserSettingActivity.class), REQUEST_CODE);
                break;
            case R.id.contact_us:
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
                break;
            case R.id.collection:
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                break;
            case R.id.save_exit:
                User.logOut(getActivity());
                getActivity().finish();
                break;
            case R.id.my_wall:
                startActivity(new Intent(getActivity(), MyWallActivity.class));
                break;
        }
    }

    @Override
    public void setUserData() {

    }

    @Override
    public User getCurrentUser() {
        return User.getCurrentUser(getActivity(), User.class);
    }

    @Override
    public void InitUserData(String path, String nickname) {
        if (TextUtils.isEmpty(path)) {
            userIcon.setImageResource(R.drawable.wall);
        } else {
            Glide.with(getActivity()).load(path).into(userIcon);
        }
        if (TextUtils.isEmpty(nickname)) {
            userName.setText("请在设置中填写个人资料");
        } else {
            userName.setText(nickname);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == 1) {
            if (data == null) {
                return;
            }
            String nickname = data.getStringExtra("nickname");
            String icon = data.getStringExtra("icon");
            userName.setText(nickname);
            Glide.with(getActivity()).load(icon).into(userIcon);
        }
    }


    //    //更新之后刷新显示
//    public  void UpdateUser() {
//        homeFragment4Presenter.InitUserData();
//    }
}
