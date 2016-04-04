package com.confress.lovewall.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.confress.lovewall.Activity.CommentActivity;
import com.confress.lovewall.Activity.NearByFriendActivity;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.FragmentPresenter.HomeFragment3Presenter;
import com.confress.lovewall.view.CustomView.ViewPagerIndicator;
import com.confress.lovewall.view.FragmentView.IHomeFragment3View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/7.
 */
public class HomeFragment3 extends Fragment implements IHomeFragment3View, View.OnClickListener {
    View root;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.indicator)
    ViewPagerIndicator indicator;
    @Bind(R.id.messge1)
    TextView messge1;
    @Bind(R.id.messge2)
    TextView messge2;
    @Bind(R.id.messge3)
    TextView messge3;
    @Bind(R.id.messge4)
    TextView messge4;
    @Bind(R.id.new_topic)
    RelativeLayout newTopic;
    @Bind(R.id.hot_topic)
    RelativeLayout hotTopic;
    @Bind(R.id.wall_firends)
    RelativeLayout wallFirends;
    private List<String> mImagePath = new ArrayList<>();
    private List<MessageWall> messageWalls = new ArrayList<>();
    private HomeFragment3Presenter presenter = new HomeFragment3Presenter(this, getActivity());
    private List<VpSimpleFragment> mContents = new ArrayList<>();
    private FragmentStatePagerAdapter mAdpter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                presenter.FirstLoadingData( getActivity());
            }
        }).start();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_fragment3, container, false);
        ButterKnife.bind(this, root);
        messge1.setOnClickListener(this);
        messge2.setOnClickListener(this);
        messge3.setOnClickListener(this);
        messge4.setOnClickListener(this);
        hotTopic.setOnClickListener(this);
        newTopic.setOnClickListener(this);
        wallFirends.setOnClickListener(this);
        return root;
    }

    private void initDatas() {
        //防止页面切换过快导致空指针异常
        if (viewpager==null){
            return;
        }
        for (String imagepath : mImagePath) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(imagepath);
            mContents.add(fragment);
        }
        mAdpter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
        indicator.setTabItemImagecator(mImagePath);
        viewpager.setAdapter(mAdpter);
        indicator.setViewPager(viewpager, 0);
        messge1.setText("#" + messageWalls.get(0).getConfess_content());
        messge2.setText("#" + messageWalls.get(1).getConfess_content());
        messge3.setText("#" + messageWalls.get(2).getConfess_content());
        messge4.setText("#" + messageWalls.get(3).getConfess_content());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        presenter=null;
    }

    public void NeedLogin() {
        T.showShort(getActivity(), "请先登录呦！");
    }

    @Override
    public void Failure() {
        T.showShort(getActivity(), "获取信息失败，请检查网络状况！");
    }

    @Override
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(getActivity(), User.class);
    }


    @Override
    public void UpdateAdapter(int size, List<MessageWall> ImessageWalls) {
        if (size==0){
        }else if (size==1){
            messageWalls.addAll(ImessageWalls);
            if (messageWalls.size()>0){
                mImagePath.clear();
                for (MessageWall messageWall : messageWalls) {
                    mImagePath.add(messageWall.getConfess_image());
                    Log.e("messageWallsImage", "" + messageWall.getConfess_image());
                }
                initDatas();
            }else {
                T.showShort(getActivity(), "没有数据！");
            }
        }else  if (size==2){
            if (messageWalls.size() > 0) {
                messageWalls.addAll(ImessageWalls);
            }
        }else  if (size==3){
            T.showShort(getActivity(), "没有更多数据了！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messge1:
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("messageWall", messageWalls.get(0));
                startActivity(intent);
                break;
            case R.id.messge2:
                Intent intent2 = new Intent(getActivity(), CommentActivity.class);
                intent2.putExtra("messageWall", messageWalls.get(1));
                startActivity(intent2);

                break;
            case R.id.messge3:
                Intent intent3 = new Intent(getActivity(), CommentActivity.class);
                intent3.putExtra("messageWall", messageWalls.get(2));
                startActivity(intent3);
                break;
            case R.id.messge4:
                Intent intent4 = new Intent(getActivity(), CommentActivity.class);
                intent4.putExtra("messageWall", messageWalls.get(3));
                startActivity(intent4);
                break;
            case R.id.hot_topic:
                T.showShort(getActivity(), "Sorry,该功能还未开放！！");
                break;
            case R.id.new_topic:
                T.showShort(getActivity(), "Sorry,该功能还未开放！！");
                break;
            case R.id.wall_firends:
                Intent intent5 = new Intent(getActivity(), NearByFriendActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
