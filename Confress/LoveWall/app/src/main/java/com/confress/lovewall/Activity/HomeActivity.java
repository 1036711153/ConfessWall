package com.confress.lovewall.Activity;

import android.Manifest;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.confress.lovewall.Fragment.HomeFragment1;
import com.confress.lovewall.Fragment.HomeFragment2;
import com.confress.lovewall.Fragment.HomeFragment3;
import com.confress.lovewall.Fragment.HomeFragment4;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.Utils.Utils;
import com.confress.lovewall.model.LocationInfo;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.HomePresenter;
import com.confress.lovewall.view.AtyView.IHomeView;
import com.confress.lovewall.view.CustomView.ChangeColorIconWithText;
import com.confress.lovewall.view.CustomView.SmallBang;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by admin on 2016/3/5.
 */
public class HomeActivity extends FragmentActivity implements IHomeView, View.OnClickListener,AMapLocationListener {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;


    private HomePresenter homePresenter;
    private SmallBang mSmallbang;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
    private ChangeColorIconWithText one, two, three, four;
    private Button center_message;
    private PopupWindow popupWindow;
    private View conterview;
    private View parent;
    private static LinearLayout main_bottom;
    public static final String TAG = "HomeActivity";
    private HomeFragment3 homeFragment3 = new HomeFragment3();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initId();
        //缓存用户不为空时候开始定位
        if (getCurrentUser()!=null) {
            //申请权限。。对于6.0以上机器。。。
            PermissionGen.with(HomeActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ).request();
        }
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                     int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public  void doLocation(){
        InitLocation();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        Toast.makeText(this, "获取定位权限失败！", Toast.LENGTH_SHORT).show();
    }

    private void InitLocation() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         *  设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
         *  只有持续定位设置定位间隔才有效，单次定位无效
         */
        locationOption.setInterval(Long.valueOf(1000));
        //单次定位
        locationOption.setOnceLocation(true);
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        mHandler.sendEmptyMessage(Utils.MSG_LOCATION_START);
    }



    private void initId() {
        // 使用推送服务时的初始化操作
        Bmob.initialize(this, "766e2d78b852727f6a4be394c0af7237");
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this, "");
        main_bottom = (LinearLayout) findViewById(R.id.main_bottom);
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



        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 设置定位监听
        locationClient.setLocationListener(this);
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
                if (getCurrentUser() == null) {
                    NeedLogin();
                    return;
                }
                homePresenter.toCharactersFragement(1);
                break;

            case R.id.image_characters:
                if (getCurrentUser() == null) {
                    NeedLogin();
                    return;
                }
                homePresenter.toCharactersFragement(2);
                break;

            case R.id.Note:
                if (getCurrentUser() == null) {
                    NeedLogin();
                    return;
                }
                homePresenter.toCharactersFragement(3);
                break;

            case R.id.more:
                T.showShort(getApplicationContext(), "Sorry，没有更多了！");
                break;

            case R.id.back:
                homePresenter.IshidePopWindows(true);
                break;
        }
    }

    @Override
    public void click_indicator_one() {
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
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new HomeFragment4()).commit();

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
        return BmobUser.getCurrentUser(this, User.class);
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

    public static void hideMainBottom() {
        main_bottom.setVisibility(View.GONE);
    }

    public static void showMainBottom() {
        main_bottom.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter = null;
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
    @Override
    public void NeedLogin() {
        T.showShort(this, "请先登录呦！");
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    Handler mHandler = new Handler(){
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case Utils.MSG_LOCATION_START:
                    break;
                //定位完成
                case Utils.MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation)msg.obj;
                    LocationInfo locationInfo = Utils.getLocationInfo(loc);
                    if (TextUtils.isEmpty(locationInfo.getAddress())&&locationInfo.getLatitude()==0&&locationInfo.getLongtitude()==0){
                        T.showShort(getApplicationContext(),"定位失败！");
                    }else {
                        //定位成功
                        homePresenter.UpdateGspLocation(locationInfo);
                    }
                    break;
                case Utils.MSG_LOCATION_STOP:
                    break;
                default:
                    break;
            }
        };
    };


}
