package com.confress.lovewall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;
import com.confress.lovewall.model.MessageWall;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.List;

/**
 * Created by admin on 2016/3/16.
 */
public class MyParallaxRecyclerAdapter extends ParallaxRecyclerAdapter {
    private List<MessageWall> messageWalls;
    private Context context;

    public OnMyItemClickListener mOnItemClickListener;

    public void setMyOnItemClickListener(OnMyItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public interface OnMyItemClickListener {
        void onItemClick(View view, int position,MessageWall messageWall);
    }


    public MyParallaxRecyclerAdapter(List data, List<MessageWall> messageWalls, Context context) {
        super(data);
        this.messageWalls = messageWalls;
        this.context = context;
    }

    @Override
    public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter parallaxRecyclerAdapter,final int i) {
        final  MessageWall messageWallitem = messageWalls.get(i);

        if (TextUtils.isEmpty(messageWallitem.getUser().getIcon())) {
            ((MyViewHolder) viewHolder).user_icon.setImageResource(R.drawable.wall);
        } else {
            Glide.with(context).load(messageWallitem.getUser().getIcon()).into(((MyViewHolder) viewHolder).user_icon);
        }
        if (!TextUtils.isEmpty(messageWallitem.getConfess_image())) {
            ((MyViewHolder) viewHolder).confess_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(messageWallitem.getConfess_image()).into(((MyViewHolder) viewHolder).confess_image);
        } else {
            ((MyViewHolder) viewHolder).confess_image.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(messageWallitem.getUser().getNick())) {
            ((MyViewHolder) viewHolder).user_name.setText("匿名人");
        } else {
            ((MyViewHolder) viewHolder).user_name.setText(messageWallitem.getUser().getNick());
        }

        ((MyViewHolder) viewHolder).user_name.setText(messageWallitem.getUser().getNick());


        ((MyViewHolder) viewHolder).update_time.setText(messageWallitem.getUpdatedAt());
        ((MyViewHolder) viewHolder).confess_content.setText(messageWallitem.getConfess_content());
        ((MyViewHolder) viewHolder).collection_count.setText(messageWallitem.getCollection_count() + "");
        ((MyViewHolder) viewHolder).message_count.setText(messageWallitem.getComment_count() + "");
        ((MyViewHolder) viewHolder).support_count.setText(messageWallitem.getSupport_count() + "");


        if (mOnItemClickListener!=null) {
            ((MyViewHolder) viewHolder).main_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, i, messageWallitem);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter parallaxRecyclerAdapter, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newhotfragmebt_item, viewGroup, false));
    }

    @Override
    public int getItemCountImpl(ParallaxRecyclerAdapter parallaxRecyclerAdapter) {
        return messageWalls.size();
    }
}
