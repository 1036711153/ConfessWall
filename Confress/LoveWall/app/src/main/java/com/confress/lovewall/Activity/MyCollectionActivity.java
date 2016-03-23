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
import com.confress.lovewall.presenter.AtyPresenter.MyCollectionPresenter;
import com.confress.lovewall.view.AtyView.IMyCollectionView;
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
public class MyCollectionActivity extends AppCompatActivity implements IMyCollectionView {

    @Bind(R.id.id_toolbar)
    Toolbar idToolbar;

    @Bind(R.id.myRecycler)
    RecyclerView myRecycler;
    @Bind(R.id.mRefresh)
    MaterialRefreshLayout mRefresh;
    private int currentpage = 1;

    private MyCollectionPresenter myCollectionPresenter = new MyCollectionPresenter(this, MyCollectionActivity.this);
    private List<MessageWall> messageWalls;
    private MyParallaxRecyclerAdapter adapter;


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideLoading();
            if (msg.what == 0) {
                Failure();
            } else if (msg.what == 1) {
                messageWalls.addAll((List<MessageWall>) msg.obj);
                if (messageWalls.size() > 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    T.showShort(getApplicationContext(), "没有数据！");
                }
            } else if (msg.what == 2) {
                if (messageWalls.size() > 0) {
                    messageWalls.addAll((List<MessageWall>) msg.obj);
                    adapter.notifyDataSetChanged();
                    currentpage++;
                }
            } else if (msg.what == 3) {
                T.showShort(getApplicationContext(), "没有更多数据了！");
            }

            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
            Log.e("messageWalls", "" + messageWalls.size());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollection_main);
        ButterKnife.bind(this);
        InitToolbar();
        InitAdpter();
        InitLoadData();
    }

    private void InitLoadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                myCollectionPresenter.FirstLoadingData(mhandler, MyCollectionActivity.this);
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
                myCollectionPresenter.FirstLoadingData(mhandler, MyCollectionActivity.this);

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                myCollectionPresenter.PullDownRefreshqueryData(mhandler, currentpage, MyCollectionActivity.this);
                // 结束上拉刷新...
            }
        });
    }

    private void InitAdpter() {
        messageWalls = new ArrayList<MessageWall>();
        adapter = new MyParallaxRecyclerAdapter(null, messageWalls, MyCollectionActivity.this);

        View hearview = LayoutInflater.from(this).inflate(R.layout.header, myRecycler, false);

        CircleImageView user_icon = (CircleImageView) hearview.findViewById(R.id.user_icon);
        TextView user_name = (TextView) hearview.findViewById(R.id.user_name);
        User currentUser = getCurrentUser();
        if (TextUtils.isEmpty(currentUser.getIcon())){
            user_icon.setImageResource(R.drawable.wall);
        }else {
            Glide.with(this).load(currentUser.getIcon()).into(user_icon);
        }
        user_name.setText(currentUser.getNick());

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
                Intent intent=new Intent(MyCollectionActivity.this, CommentActivity.class);
                intent.putExtra("messageWall", messageWall);
                startActivity(intent);
            }
        });
    }

    private void InitToolbar() {
        idToolbar.setTitleTextColor(this.getResources().getColor(R.color.white));
        setSupportActionBar(idToolbar);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(manager);
        myRecycler.setHasFixedSize(true);


        idToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollectionActivity.this.finish();

            }
        });
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void Failure() {
        T.showShort(this, "加载数据失败！！");
    }

    @Override
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(this, User.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCollectionPresenter=null;
    }
}
