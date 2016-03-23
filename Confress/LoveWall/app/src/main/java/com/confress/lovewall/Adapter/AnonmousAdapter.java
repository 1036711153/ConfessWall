package com.confress.lovewall.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.view.CustomView.RoundImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/13.
 */
public class AnonmousAdapter extends BaseAdapter {
    private List<MessageWall> messageWalls;
    private Context context;

    public AnonmousAdapter(List<MessageWall> messageWalls, Context context) {
        this.messageWalls = messageWalls;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messageWalls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MessageWall messageWallitem = messageWalls.get(position);
        ViewHoder hoder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.anonmousfragmebt_item, null);
            hoder = new ViewHoder();
            hoder.content= (LinearLayout) convertView.findViewById(R.id.id_content);
            hoder.user_icon = (CircleImageView) convertView.findViewById(R.id.user_icon);
            hoder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            hoder.update_time = (TextView) convertView.findViewById(R.id.update_time);
            hoder.confess_content = (TextView) convertView.findViewById(R.id.confess_content);
            hoder.confess_image = (ImageView) convertView.findViewById(R.id.confess_image);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(messageWallitem.getConfess_image())) {
            hoder.confess_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(messageWallitem.getConfess_image()).into(hoder.confess_image);
        } else {
            hoder.confess_image.setVisibility(View.GONE);
        }
        hoder.user_name.setText("匿名人");
        hoder.update_time.setText(messageWallitem.getUpdatedAt());
        hoder.confess_content.setText(messageWallitem.getConfess_content());
        return convertView;
    }
    class ViewHoder {
        private LinearLayout content;
        private CircleImageView user_icon;

        private TextView user_name;
        private TextView update_time;
        private TextView confess_content;

        private ImageView confess_image;



    }
}
