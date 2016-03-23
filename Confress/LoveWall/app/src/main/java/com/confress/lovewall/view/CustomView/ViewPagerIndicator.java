package com.confress.lovewall.view.CustomView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.confress.lovewall.R;
import com.confress.lovewall.model.MessageWall;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2016/3/22.
 */
public class ViewPagerIndicator extends LinearLayout {
    /**
     * tab上的内容
     */
    private List<String> mTabsImagePath;
    /**
     * 与之绑定的ViewPager
     */
    public ViewPager mViewPager;

    private int mPosition=0;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        timer.schedule(task, 1000, 2000); // 1s后执行task,经过1s再次执行
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 设置点击事件
        setItemClickEvent();
    }

    public  void setTabItemImagecator(List<String> datas){
        if (datas!=null&&datas.size()>0){
            this.removeAllViews();
            this.mTabsImagePath=datas;
            for (String path:mTabsImagePath){
                addView(generateImageView());
            }
            // 设置item的click事件
            setItemClickEvent();
        }
    }

    private View generateImageView() {
        ImageView imageView=new ImageView(getContext());
        LayoutParams lp = new LayoutParams(
                25,25);
        lp.setMargins(5,5,5,5);
        imageView.setImageResource(R.drawable.ic_focus_select);
        imageView.setLayoutParams(lp);
        return imageView;
    }
    /**
     * 对外的ViewPager的回调接口
     *
     * @author zhy
     *
     */
    public interface PageChangeListener
    {
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    // 对外的ViewPager的回调接口
    private PageChangeListener onPageChangeListener;

    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener)
    {
        this.onPageChangeListener = pageChangeListener;
    }

    // 设置关联的ViewPager
    public void setViewPager(final ViewPager mViewPager, final int pos)
    {
        this.mViewPager = mViewPager;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 设置字体颜色高亮
                resetImageViewColor();
                highLightImageView(position);

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
//                if (position==mTabsImagePath.size()-1){
//                    SystemClock.sleep(1000);
//                    mViewPager.setCurrentItem(0);
//                }
                mPosition = position;
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }

            }
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightImageView(pos);
    }

    /**
     * 高亮文本
     *
     * @param position
     */
    protected void highLightImageView(int position)
    {
        View view = getChildAt(position);
        if (view instanceof ImageView)
        {
            ((ImageView) view).setImageResource(R.drawable.ic_focus);
        }

    }

    /**
     * 重置文本颜色
     */
    private void resetImageViewColor()
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            View view = getChildAt(i);
            if (view instanceof ImageView)
            {
                ((ImageView) view).setImageResource(R.drawable.ic_focus_select);
            }
        }
    }


    /**
     * 设置点击事件
     */
    public void setItemClickEvent()
    {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = mPosition+1;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTabsImagePath==null||mTabsImagePath.size()==0){
                return;
            }
            mViewPager.setCurrentItem(msg.what%mTabsImagePath.size());
            super.handleMessage(msg);
        };
    };
}
