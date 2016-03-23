package com.confress.lovewall.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.confress.lovewall.Fragment.TabFragment.AnonmousFragment;
import com.confress.lovewall.Fragment.TabFragment.HotFragment;
import com.confress.lovewall.Fragment.TabFragment.NewsFragment;
import com.confress.lovewall.Fragment.TabFragment.tabsFragment;
import com.confress.lovewall.R;
import com.confress.lovewall.view.FragmentView.IHomeFragment1View;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/3/7.
 */
public class HomeFragment1 extends Fragment implements IHomeFragment1View{
    View root;
    @Bind(R.id.id_tablayout)
    TabLayout idTablayout;
    @Bind(R.id.id_viewpager)
    ViewPager idViewpager;
    private String[]mTitles=new String[]{"最新","热门","匿名"};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_fragment1, container, false);
        ButterKnife.bind(this, root);
        idViewpager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return toNewsFragmnet();
                } else if (position == 1) {
                    return toHotFragmnet();
                } else if (position == 2) {
                    return toAnonmousFragmnet();
                }
                return new tabsFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        idTablayout.setupWithViewPager(idViewpager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public Fragment toNewsFragmnet() {
        return new NewsFragment();
    }

    @Override
    public Fragment toHotFragmnet() {
        return new HotFragment();
    }

    @Override
    public Fragment toAnonmousFragmnet() {
        return new AnonmousFragment();
    }
}
