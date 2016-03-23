package com.confress.lovewall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;
import com.confress.lovewall.model.Comment;
import com.confress.lovewall.model.MessageWall;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.List;

/**
 * Created by admin on 2016/3/16.
 */
public class CommentParallaxRecyclerAdapter extends ParallaxRecyclerAdapter {
    private List<Comment> comments;
    private Context context;

    public CommentParallaxRecyclerAdapter(List data, List<Comment> comments, Context context) {
        super(data);
        this.comments = comments;
        this.context = context;
    }

    @Override
    public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter parallaxRecyclerAdapter, int i) {
        Comment comment = comments.get(i);

        if (TextUtils.isEmpty(comment.getUser().getIcon())) {
            ((CommentViewHolder) viewHolder).user_icon.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Glide.with(context).load(comment.getUser().getIcon()).into(((CommentViewHolder) viewHolder).user_icon);
        }

        if (TextUtils.isEmpty(comment.getUser().getNick())) {
            ((CommentViewHolder) viewHolder).user_name.setText("无名人士");
        } else {
            ((CommentViewHolder) viewHolder).user_name.setText(comment.getUser().getNick());
        }

        ((CommentViewHolder) viewHolder).update_time.setText(comment.getUpdatedAt());
        ((CommentViewHolder) viewHolder).confess_content.setText(comment.getContent());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter parallaxRecyclerAdapter, int i) {
        return new CommentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.commentrecycle_item, viewGroup, false));
    }

    @Override
    public int getItemCountImpl(ParallaxRecyclerAdapter parallaxRecyclerAdapter) {
        return comments.size();
    }
}
