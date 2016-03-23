package com.confress.lovewall.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.confress.lovewall.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/16.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout main_content;

    public LinearLayout content;

    public CircleImageView user_icon;

    public TextView user_name;
    public TextView update_time;
    public TextView confess_content;
    public TextView collection_count;
    public TextView message_count;
    public TextView support_count;

    public ImageView confess_image;

    public ImageView collection_image;
    public ImageView message_iamge;
    public ImageView support_image;

    public CardView collection;
    public CardView message;
    public CardView support;

    public MyViewHolder(View itemView) {
        super(itemView);
        main_content= (RelativeLayout) itemView.findViewById(R.id.main_content);
        content = (LinearLayout) itemView.findViewById(R.id.id_content);
        user_icon = (CircleImageView) itemView.findViewById(R.id.user_icon);
        user_name = (TextView) itemView.findViewById(R.id.user_name);
        update_time = (TextView) itemView.findViewById(R.id.update_time);
        confess_content = (TextView) itemView.findViewById(R.id.confess_content);
        collection_count = (TextView) itemView.findViewById(R.id.collection_count);
        message_count = (TextView) itemView.findViewById(R.id.message_count);
        support_count = (TextView) itemView.findViewById(R.id.support_count);

        confess_image = (ImageView) itemView.findViewById(R.id.confess_image);
        collection_image = (ImageView) itemView.findViewById(R.id.collection_image);
        message_iamge = (ImageView) itemView.findViewById(R.id.message_iamge);
        support_image = (ImageView) itemView.findViewById(R.id.support_image);


        collection = (CardView) itemView.findViewById(R.id.collection);
        message = (CardView) itemView.findViewById(R.id.message);
        support = (CardView) itemView.findViewById(R.id.support);
    }
}
