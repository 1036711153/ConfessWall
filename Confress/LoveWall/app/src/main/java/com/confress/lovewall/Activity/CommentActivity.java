package com.confress.lovewall.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.confress.lovewall.Adapter.CommentParallaxRecyclerAdapter;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.Comment;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.CommentPresenter;
import com.confress.lovewall.view.AtyView.ICommentView;
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
public class CommentActivity extends AppCompatActivity implements ICommentView,View.OnClickListener {

    @Bind(R.id.id_toolbar)
    Toolbar idToolbar;
    @Bind(R.id.myRecycler)
    RecyclerView myRecycler;
    @Bind(R.id.mRefresh)
    MaterialRefreshLayout mRefresh;
    @Bind(R.id.comment_msg)
    EditText commentMsg;
    @Bind(R.id.submit)
    FloatingActionButton submit;
    private int currentpage = 1;
    private MessageWall messageWall;
    private CommentPresenter commentPresenter = new CommentPresenter(this, CommentActivity.this);
    private List<Comment> mComments;
    private CommentParallaxRecyclerAdapter adapter;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideLoading();
            if (msg.what == 0) {
                Failure();
            } else if (msg.what == 1) {
                mComments.addAll((List<Comment>) msg.obj);
                if (mComments.size() > 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    T.showShort(getApplicationContext(), "没有评论！");
                }
            } else if (msg.what == 2) {
                if (mComments.size() > 0) {
                    mComments.addAll((List<Comment>) msg.obj);
                    adapter.notifyDataSetChanged();
                    currentpage++;
                }
            } else if (msg.what == 3) {
                T.showShort(getApplicationContext(), "没有更多评论了！");
            }
            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
            Log.e("messageWalls", "" + mComments.size());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycomment_main);
        ButterKnife.bind(this);
        messageWall = (MessageWall) getIntent().getSerializableExtra("messageWall");
        InitToolBar();
        InitAdpter();
        InitData();
        submit.setOnClickListener(this);
    }

    private void InitData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                commentPresenter.FirstLoadingData(mhandler, CommentActivity.this,messageWall);
                currentpage = 1;
            }
        }).start();


        //刷新加载数据
        mRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...与第一次加载数据一样
                currentpage = 1;
                mComments.clear();
                commentPresenter.FirstLoadingData(mhandler, CommentActivity.this,messageWall);

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                commentPresenter.PullDownRefreshqueryData(mhandler, currentpage, CommentActivity.this,messageWall);
                // 结束上拉刷新...
            }
        });
    }

    private void InitAdpter() {
        mComments = new ArrayList<Comment>();
        adapter = new CommentParallaxRecyclerAdapter(null, mComments, CommentActivity.this);

        View hearview = LayoutInflater.from(this).inflate(R.layout.newhotfragmebt_item, myRecycler, false);
        CircleImageView user_icon = (CircleImageView) hearview.findViewById(R.id.user_icon);

        TextView user_name = (TextView) hearview.findViewById(R.id.user_name);
        TextView update_time = (TextView) hearview.findViewById(R.id.update_time);
        TextView confess_content = (TextView) hearview.findViewById(R.id.confess_content);
        TextView collection_count = (TextView) hearview.findViewById(R.id.collection_count);
        TextView message_count = (TextView) hearview.findViewById(R.id.message_count);
        TextView support_count = (TextView) hearview.findViewById(R.id.support_count);
        ImageView confess_image = (ImageView) hearview.findViewById(R.id.confess_image);
        ImageView collection_image = (ImageView) hearview.findViewById(R.id.collection_image);
        ImageView message_iamge = (ImageView) hearview.findViewById(R.id.message_iamge);
        ImageView support_image = (ImageView) hearview.findViewById(R.id.support_image);

        user_name.setText(messageWall.getUser().getNick());
        update_time.setText(messageWall.getUpdatedAt());
        confess_content.setText(messageWall.getConfess_content());
        collection_count.setText(messageWall.getCollection_count() + "");
        message_count.setText(messageWall.getComment_count() + "");
        support_count.setText(messageWall.getSupport_count() + "");
        if (!TextUtils.isEmpty(messageWall.getUser().getIcon())){
            Glide.with(CommentActivity.this).load(messageWall.getUser().getIcon()).into(user_icon);
        }

        if (TextUtils.isEmpty(messageWall.getConfess_image())) {
            confess_image.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(messageWall.getConfess_image()).into(confess_image);
        }

        collection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        support_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        adapter.setParallaxHeader(hearview, myRecycler);
        adapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {

            }
        });
        myRecycler.setAdapter(adapter);
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
                CommentActivity.this.finish();

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
    public String getCommentMsg() {
        return commentMsg.getText().toString();
    }

    @Override
    public void PostSuccess() {
        T.showShort(CommentActivity.this, "评论成功！");
        commentMsg.setText("");
        currentpage = 1;
        mComments.clear();
        commentPresenter.FirstLoadingData(mhandler, CommentActivity.this, messageWall);
    }

    @Override
    public void PostFailure() {
        T.showShort(CommentActivity.this, "评论失败！");
    }

    @Override
    public void EmptyMsg() {
        T.showShort(CommentActivity.this, "评论内容不能为空！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                commentPresenter.UpComment(messageWall);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentPresenter=null;
    }
}
