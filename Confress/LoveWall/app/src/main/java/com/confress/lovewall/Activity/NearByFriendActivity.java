package com.confress.lovewall.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.confress.lovewall.Adapter.NearFriendsAdpter;
import com.confress.lovewall.Adapter.TricksAdpter;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.NearByFriendPresenter;
import com.confress.lovewall.view.AtyView.INearByFriendView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/4/2.
 */
public class NearByFriendActivity extends Activity implements INearByFriendView{
    @Bind(R.id.addfriendrequest_lv)
    ListView addfriendrequestLv;
    @Bind(R.id.mListView)
    ListView mListView;
    @Bind(R.id.mRefresh)
    MaterialRefreshLayout mRefresh;

    private int currentpage = 1;
    private NearByFriendPresenter presenter=new NearByFriendPresenter(this,NearByFriendActivity.this);
    private List<User> friends;
    private NearFriendsAdpter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearbyfriend_main);
        ButterKnife.bind(this);
        friends = new ArrayList<User>();
        adapter = new NearFriendsAdpter(NearByFriendActivity.this,friends);
        mListView.setAdapter(adapter);
        InitData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = friends.get(position);
                Intent intent = new Intent(NearByFriendActivity.this, UserWallActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
//        T.showShort(getApplicationContext(),getUser().getGpsadd().getLatitude()+"");
    }

    private void InitData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                presenter.FirstLoadingData(NearByFriendActivity.this);
                currentpage = 1;
            }
        }).start();

        //刷新加载数据
        mRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...与第一次加载数据一样
                mRefresh.finishRefresh();
                currentpage = 1;
                friends.clear();
                presenter.FirstLoadingData(NearByFriendActivity.this);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                presenter.PullDownRefreshqueryData(currentpage, NearByFriendActivity.this);
                // 结束上拉刷新...
            }
        });
    }

    //加载完毕。。。
    @Override
    public void LoadOver() {
        if (mRefresh!=null) {
            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
        }
    }

    @Override
    public User getUser() {
        return BmobUser.getCurrentUser(this,User.class);
    }

    @Override
    public void Failure() {
        T.showShort(getApplicationContext(), "加载数据失败！！");
    }

    @Override
    public void NeedLogin() {
        T.showShort(getApplicationContext(), "请先登录呦！");
    }

    @Override
    public void UpdateAdapter(int size, List<User> Iusers) {
        if (size==0){
        }else if (size==1){
            friends.addAll(Iusers);
            if (friends.size()>0){
                adapter.notifyDataSetChanged();
            }else {
                //没有数据
            }
        }else  if (size==2){
            if (friends.size() > 0) {
                friends.addAll(Iusers);
                adapter.notifyDataSetChanged();
                currentpage++;
            }
        }else  if (size==3){
            T.showShort(getApplicationContext(), "附近没有更多墙友了！");
        }
        LoadOver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter=null;
    }
}
