package com.confress.lovewall.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.confress.lovewall.Activity.ChattingActivity;
import com.confress.lovewall.Activity.ShowChattingMsgActivity;
import com.confress.lovewall.Adapter.TricksAdpter;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.FragmentPresenter.HomeFragment2Presenter;
import com.confress.lovewall.view.FragmentView.IHomeFragment2View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/7.
 */
public class HomeFragment2 extends Fragment implements IHomeFragment2View {
    @Bind(R.id.mListView)
    ListView mListView;
    @Bind(R.id.mRefresh)
    MaterialRefreshLayout mRefresh;
    private TricksAdpter adapter;
    private List<User> friends;
    private int currentpage = 1;
    private HomeFragment2Presenter homeFragment2Presenter=new HomeFragment2Presenter(this,getActivity());

    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tab2, container, false);
        ButterKnife.bind(this, root);
        friends = new ArrayList<User>();
        adapter = new TricksAdpter(getActivity(),friends);
        mListView.setAdapter(adapter);
        InitData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = friends.get(position);
                Intent intent = new Intent(getActivity(), ShowChattingMsgActivity.class);
                intent.putExtra("friend_user_icon", user.getIcon());
                intent.putExtra("friend_user_id", user.getObjectId());
                startActivity(intent);
            }
        });
        return root;
    }

    private void InitData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                homeFragment2Presenter.FirstLoadingData(getActivity());
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
                homeFragment2Presenter.FirstLoadingData(getActivity());
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                homeFragment2Presenter.PullDownRefreshqueryData(currentpage, getActivity());
                // 结束上拉刷新...
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        homeFragment2Presenter=null;
    }


    @Override
    public void Failure() {
        T.showShort(getActivity(), "加载数据失败！！");
    }

    @Override
    public void LoadOver() {
        if (mRefresh!=null) {
            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
        }
    }

    @Override
    public User getUser() {
        return BmobUser.getCurrentUser(getActivity(), User.class);
    }
    @Override
    public void NeedLogin() {
        T.showShort(getActivity(), "请先登录呦！");
    }

    @Override
    public void UpdateAdapter(int size, List<User> Iusers) {
        if (size==0){
        }else if (size==1){
            friends.addAll(Iusers);
            if (friends.size()>0){
                adapter.notifyDataSetChanged();
            }else {
                T.showShort(getActivity(), "您还没有关注别人哦，关注之后就可以聊天啦！");
            }
        }else  if (size==2){
            if (friends.size() > 0) {
                friends.addAll(Iusers);
                adapter.notifyDataSetChanged();
                currentpage++;
            }
        }else  if (size==3){
            T.showShort(getActivity(), "没有更多好友了！");
        }
        LoadOver();
    }
}
