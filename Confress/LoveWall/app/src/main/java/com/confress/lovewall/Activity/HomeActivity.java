package com.confress.lovewall.Activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.confress.lovewall.Utils.ScreenUtil;
import com.confress.lovewall.model.MyBmobInstallation;
import com.confress.lovewall.model.User;
import com.confress.lovewall.view.CustomView.ChangeColorIconWithText;
import com.confress.lovewall.view.CustomView.SmallBang;
import com.confress.lovewall.Fragment.HomeFragment1;
import com.confress.lovewall.Fragment.HomeFragment2;
import com.confress.lovewall.Fragment.HomeFragment3;
import com.confress.lovewall.Fragment.HomeFragment4;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.presenter.AtyPresenter.HomePresenter;
import com.confress.lovewall.view.AtyView.IHomeView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/5.
 */
public class HomeActivity extends FragmentActivity implements IHomeView, View.OnClickListener {
    private HomePresenter homePresenter;

    private SmallBang mSmallbang;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
    private ChangeColorIconWithText one, two, three, four;
    private Button center_message;
    private PopupWindow popupWindow;
    private View conterview;
    private View parent;
    private static LinearLayout main_bottom;
    public static final String TAG="HomeActivity";
    private HomeFragment3 homeFragment3=new HomeFragment3();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initId();
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        center_message.setOnClickListener(this);
        conterview.findViewById(R.id.characters).setOnClickListener(this);
        conterview.findViewById(R.id.image_characters).setOnClickListener(this);
        conterview.findViewById(R.id.Note).setOnClickListener(this);
        conterview.findViewById(R.id.more).setOnClickListener(this);
        conterview.findViewById(R.id.back).setOnClickListener(this);

    }

    private void initId() {
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this,"");
        main_bottom= (LinearLayout) findViewById(R.id.main_bottom);
        center_message = (Button) findViewById(R.id.center_message);
        one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
        mTabIndicators.add(one);
        two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
        mTabIndicators.add(two);
        three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
        mTabIndicators.add(three);
        four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
        mTabIndicators.add(four);

        mSmallbang = SmallBang.attach2Window(this);
        homePresenter = new HomePresenter(this, HomeActivity.this, mTabIndicators, mSmallbang);
        //默认情况下将one设置为点击绿色
        one.setIconAlpha(1.0f);
        //初始化数据第一页
        click_indicator_one();

        conterview = getLayoutInflater().inflate(R.layout.add_pop_main, null);
        popupWindow = new PopupWindow(conterview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.setAnimationStyle(R.style.animation);
        parent = findViewById(R.id.linearlayout1);
        //绑定用户
        homePresenter.BindUserInstallation();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_indicator_one:
                homePresenter.ReplaceFragement1(one);
                break;
            case R.id.id_indicator_two:
                homePresenter.ReplaceFragement2(two);
                break;
            case R.id.id_indicator_three:
                homePresenter.ReplaceFragement3(three);
                break;
            case R.id.id_indicator_four:
                homePresenter.ReplaceFragement4(four);
                break;
            case R.id.center_message:
                homePresenter.Write_Message(v);
                break;
            case R.id.characters:
//                T.showShort(getApplicationContext(), "characters");
                homePresenter.toCharactersFragement(1);
                break;

            case R.id.image_characters:
//                T.showShort(getApplicationContext(), "image_characters");
                homePresenter.toCharactersFragement(2);
                break;

            case R.id.Note:
//                T.showShort(getApplicationContext(), "Note");
                homePresenter.toCharactersFragement(3);
                break;

            case R.id.more:
                T.showShort(getApplicationContext(), "Sorry，没有更多了！");
//                homePresenter.toCharactersFragement(4);
                break;

            case R.id.back:
                homePresenter.IshidePopWindows(true);
                break;
        }
    }

    @Override
    public  void click_indicator_one() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment1()).commit();
    }

    @Override
    public void click_indicator_two() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment2()).commit();

    }

    @Override
    public void click_indicator_three() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment3()).commit();

    }

    @Override
    public void click_indicator_four() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment4()).commit();

    }

    @Override
    public void click_indicator_center() {
        showPop();
     }

    @Override
    public void click_pop(int i) {
    }

    @Override
    public void showPop() {
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void hidePop() {
        popupWindow.dismiss();
    }

    @Override
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(this,User.class);
    }


    //设置背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }


    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    public static  void  hideMainBottom(){
        main_bottom.setVisibility(View.GONE);
    }
    public static  void showMainBottom(){
        main_bottom.setVisibility(View.VISIBLE);
    }
    public static  void  ToFirstFragment(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter=null;
    }
}
