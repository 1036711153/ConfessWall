package com.confress.lovewall.Fragment.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.confress.lovewall.Activity.CommentActivity;
import com.confress.lovewall.Activity.HomeActivity;
import com.confress.lovewall.Activity.UserWallActivity;
import com.confress.lovewall.Adapter.AnonmousAdapter;
import com.confress.lovewall.Adapter.NewsAndHotAdapter;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.T;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.FragmentPresenter.NewsFragmentPresenter;
import com.confress.lovewall.view.FragmentView.INewsFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/13.
 */
public class NewsFragment extends Fragment implements INewsFragmentView {
    View root;
    @Bind(R.id.mListView)
    ListView mListView;
    @Bind(R.id.mRefresh)
    MaterialRefreshLayout mRefresh;
    @Bind(R.id.tabs_progress)
    ProgressBar tabsProgress;
    private NewsFragmentPresenter newsFragmentPresenter = new NewsFragmentPresenter(this, getActivity());
    private NewsAndHotAdapter adapter;
    private List<MessageWall> messageWalls;
    private int currentpage = 1;
    private int mFirstY;
    private int mCurrentY;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            hideLoading();
            if (msg.what == 0) {
                Failure();
            } else if (msg.what == 1) {
                messageWalls.addAll((List<MessageWall>) msg.obj);
                if (messageWalls.size() > 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    T.showShort(getActivity(), "没有数据！");
                }
            } else if (msg.what == 2) {
                if (messageWalls.size() > 0) {
                    messageWalls.addAll((List<MessageWall>) msg.obj);
                    adapter.notifyDataSetChanged();
                    currentpage++;
                }
            } else if (msg.what == 3) {
                T.showShort(getActivity(), "没有更多数据了！");
            }
            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
        }
    };


    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, root);
        messageWalls = new ArrayList<MessageWall>();
        adapter = new NewsAndHotAdapter(messageWalls, getActivity());
        mListView.setAdapter(adapter);
        //初始化加载一次数据并且显示
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsFragmentPresenter.FirstLoadingData(mhandler, getActivity());
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
                newsFragmentPresenter.FirstLoadingData(mhandler, getActivity());

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                newsFragmentPresenter.PullDownRefreshqueryData(mhandler, currentpage, getActivity());
                // 结束上拉刷新...

            }
        });
        adapter.setMyOnItemClickListener(new NewsAndHotAdapter.OnMyItemClickListener() {
            @Override
            public void onSupportClick(View view, int position, MessageWall messageWall, boolean isclick, ImageView imageView, TextView textView) {
                if (isclick) {
                    imageView.setImageResource(R.drawable.statusdetail_comment_icon_like_highlighted);
                    int count = Integer.parseInt(textView.getText().toString());
                    count++;
                    textView.setText("" + count);
                    newsFragmentPresenter.ChangeCount(messageWall, getActivity(), messageWall.getCollection_count(), messageWall.getComment_count(), count);
                } else {
                    imageView.setImageResource(R.drawable.radar_card_people_good_highlighted);
                    int count = Integer.parseInt(textView.getText().toString());
                    count--;
                    textView.setText("" + count);
                    newsFragmentPresenter.ChangeCount(messageWall, getActivity(), messageWall.getCollection_count(), messageWall.getComment_count(), count);
                }
            }

            @Override
            public void onCollectionClick(View view, int position, MessageWall messageWall, boolean isclick, ImageView imageView, TextView textView) {
                if (isclick) {
                    newsFragmentPresenter.CollectionOp(getActivity(), messageWall);
                    imageView.setImageResource(R.drawable.btn_star_on_pressed_holo_dark);
                    int count = Integer.parseInt(textView.getText().toString());
                    count++;
                    textView.setText("" + count);
                    newsFragmentPresenter.ChangeCount(messageWall, getActivity(), count, messageWall.getComment_count(), messageWall.getSupport_count());
                } else {
                    newsFragmentPresenter.DelCollection(getActivity(), messageWall);
                    imageView.setImageResource(R.drawable.ic_menu_star);
                    int count = Integer.parseInt(textView.getText().toString());
                    count--;
                    textView.setText("" + count);
                    newsFragmentPresenter.ChangeCount(messageWall, getActivity(), count, messageWall.getComment_count(), messageWall.getSupport_count());
                }
            }

            @Override
            public void onConmmentClick(View view, int position, MessageWall messageWall) {
                Intent intent=new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("messageWall", messageWall);
                startActivity(intent);
            }

            @Override
            public void onContentClick(View view, int position, MessageWall messageWall) {
                Intent intent=new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("messageWall", messageWall);
                startActivity(intent);

            }

            @Override
            public void onUserIconClick(View view, int position,MessageWall messageWall) {
                Intent intent=new Intent(getActivity(), UserWallActivity.class);
                intent.putExtra("messageWall",messageWall);
                startActivity(intent);
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        mCurrentY = (int) event.getY();
                        if (mCurrentY - mFirstY > 50) {
                            Log.e("Show", "Show");
                            HomeActivity.showMainBottom();

                        } else if (mFirstY - mCurrentY > 20) {
                            Log.e("Hide", "Hide");
                            HomeActivity.hideMainBottom();
                        }
                        break;
                }
                return false;
            }
        });
        return root;
    }

    @Override
    public void showLoading() {
//        tabsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
//        tabsProgress.setVisibility(View.INVISIBLE);
    }


    @Override
    public void Failure() {
//        T.showShort(getActivity(), "加载数据失败，请刷新试试！");
        mRefresh.finishRefresh();
        mRefresh.finishRefreshLoadMore();
    }

    @Override
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(getActivity(), User.class);
    }

    @Override
    public void CollectionSuccess() {
        T.showShort(getActivity(), "收藏成功！");
    }

    @Override
    public void CollectionFailure() {
        T.showShort(getActivity(), "收藏失败！");
    }

    @Override
    public void DelCollectionSuccess() {
        T.showShort(getActivity(), "取消收藏成功！");
    }

    @Override
    public void DelCollectionFailure() {
        T.showShort(getActivity(), "取消收藏失败！");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        newsFragmentPresenter=null;
    }
}
