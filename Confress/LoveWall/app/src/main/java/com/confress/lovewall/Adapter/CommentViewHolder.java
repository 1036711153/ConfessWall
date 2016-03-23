package com.confress.lovewall.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.confress.lovewall.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/16.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView user_icon;
    public TextView user_name;
    public TextView update_time;
    public TextView confess_content;


    public CommentViewHolder(View itemView) {
        super(itemView);
        user_icon = (CircleImageView) itemView.findViewById(R.id.user_icon);
        user_name = (TextView) itemView.findViewById(R.id.user_name);
        update_time = (TextView) itemView.findViewById(R.id.update_time);
        confess_content = (TextView) itemView.findViewById(R.id.confess_content);
    }
}
