package com.confress.lovewall.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;

/**
 * Created by admin on 2016/3/22.
 */
public class VpSimpleFragment extends Fragment {
    private String mImagePath;
    public  static final  String BUNDLE_IMAGEPATH="image_path";
    private  ImageView image;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       Bundle bundle=getArguments();
        if (bundle!=null){
            mImagePath=bundle.getString(BUNDLE_IMAGEPATH);
        }
        View root=inflater.inflate(R.layout.fragment_image,container,false);
        ImageView image= (ImageView) root.findViewById(R.id.image);
        Glide.with(getActivity()).load(mImagePath).into(image);
        return root;
    }

    public  static  VpSimpleFragment newInstance(String path){
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLE_IMAGEPATH, path);
        VpSimpleFragment fragment=new VpSimpleFragment();
        fragment.setArguments(bundle);
        return  fragment;
    }
}
