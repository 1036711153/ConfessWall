package com.confress.lovewall.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.confress.lovewall.Adapter.MyParallaxRecyclerAdapter;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.MyWallPresenter;
import com.confress.lovewall.presenter.AtyPresenter.UserWallPresenter;
import com.confress.lovewall.view.AtyView.IMyWallView;
import com.confress.lovewall.view.AtyView.IUserWallView;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/14.
 */
public class UserWallActivity extends AppCompatActivity implements IUserWallView,View.OnClickListener{
    @Bind(R.id.id_toolbar)
    Toolbar idToolbar;
    @Bind(R.id.id_progress)
    ProgressBar idProgress;
    @Bind(R.id.myRecycler)
    RecyclerView myRecycler;
    @Bind(R.id.mRefresh)
    MaterialRefreshLayout mRefresh;
    private User messageWall_user;
    private int currentpage = 1;
    private UserWallPresenter userWallPresenter = new UserWallPresenter(this, UserWallActivity.this);
    private List<MessageWall> messageWalls;
    private MyParallaxRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userwall_main);
        ButterKnife.bind(this);
        messageWall_user= (User) getIntent().getSerializableExtra("user");
        InitToolBar();
        InitAdpter();
        DoRefresh();
    }

    private void DoRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userWallPresenter.FirstLoadingData( UserWallActivity.this);
                currentpage = 1;
            }
        }).start();


        //刷新加载数据
        mRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...与第一次加载数据一样
                currentpage = 1;
                messageWalls.clear();
                userWallPresenter.FirstLoadingData( UserWallActivity.this);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                userWallPresenter.PullDownRefreshqueryData(currentpage, UserWallActivity.this);
                // 结束上拉刷新...
            }
        });
    }

    private void InitAdpter() {
        messageWalls = new ArrayList<MessageWall>();
        adapter = new MyParallaxRecyclerAdapter(null, messageWalls, UserWallActivity.this);
        View hearview= LayoutInflater.from(this).inflate(R.layout.userwall_head,myRecycler,false);

        CircleImageView user_icon= (CircleImageView) hearview.findViewById(R.id.user_icon);
        TextView user_name= (TextView) hearview.findViewById(R.id.user_name);
        TextView addfriends= (TextView) hearview.findViewById(R.id.addfriends);
        addfriends.setOnClickListener(this);
        User currentUser = messageWall_user;

        if (!TextUtils.isEmpty(currentUser.getIcon())){
            Glide.with(this).load(currentUser.getIcon()).into(user_icon);
        }
        if (!TextUtils.isEmpty(currentUser.getNick())) {
            user_name.setText(currentUser.getNick());
        }
        adapter.setParallaxHeader(hearview, myRecycler);
        adapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {

            }
        });
        myRecycler.setAdapter(adapter);
        adapter.setMyOnItemClickListener(new MyParallaxRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position, MessageWall messageWall) {
                Intent intent = new Intent(UserWallActivity.this, CommentActivity.class);
                intent.putExtra("messageWall", messageWall);
                startActivity(intent);
            }
        });
    }

    private void InitToolBar() {
        idToolbar.setTitleTextColor(this.getResources().getColor(R.color.white));
        setSupportActionBar(idToolbar);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(manager);
        myRecycler.setHasFixedSize(true);
        idToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserWallActivity.this.finish();
            }
        });


    }

    @Override
    public void Failure() {
        T.showShort(this, "加载数据失败！！");
    }

    @Override
    public User getWallUser() {
        return messageWall_user;
    }

    @Override
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(this,User.class);
    }

    @Override
    public void AttentionSuccess() {
        T.showShort(this, "添加好友成功！！");
    }

    @Override
    public void AttentionFailure() {
        T.showShort(this, "添加好友失败！！");
    }

    @Override
    public void DisAttentionSuccess() {
        T.showShort(this, "取关成功！！");
    }

    @Override
    public void DisAttentionFailure() {
        T.showShort(this, "取关失败！！");
    }

    @Override
    public void ErrorOfAttention() {
        T.showShort(this, "不能添加自己为好友！！");
    }

    @Override
    public void UpdateAdapter(int size, List<MessageWall> ImessageWalls) {
        if (size==0){
            Failure();
        }else if (size==1){
            messageWalls.addAll(ImessageWalls);
            if (messageWalls.size()>0){
                adapter.notifyDataSetChanged();
            }else {
                T.showShort(getApplicationContext(), "没有数据！");
            }
        }else  if (size==2){
            if (messageWalls.size() > 0) {
                messageWalls.addAll(ImessageWalls);
                adapter.notifyDataSetChanged();
                currentpage++;
            }
        }else  if (size==3){
            T.showShort(getApplicationContext(), "没有更多数据了！");
        }
        LoadOver();
    }

    @Override
    public void LoadOver() {
        if (mRefresh!=null) {
            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addfriends:
                userWallPresenter.AddRequest();
//                userWallPresenter.ADDFriends2();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userWallPresenter=null;
    }
}
