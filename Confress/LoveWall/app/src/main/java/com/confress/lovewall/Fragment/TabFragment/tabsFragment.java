package com.confress.lovewall.Fragment.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.confress.lovewall.R;

/**
 * Created by admin on 2016/3/13.
 */
public class tabsFragment extends Fragment {
    View root;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_tab4, container, false);
        return root;
    }
}
