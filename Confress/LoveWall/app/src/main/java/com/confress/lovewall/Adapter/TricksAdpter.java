package com.confress.lovewall.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;
import com.confress.lovewall.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/17.
 */
public class TricksAdpter extends BaseAdapter {
    private Context context;
    private List<User> friends;

    public TricksAdpter(Context context, List<User> friends) {
        this.context = context;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = friends.get(position);
//        View rootview = LayoutInflater.from(context).inflate(R.layout.fragment_tab2_item, null);
//        CircleImageView friend_icon = (CircleImageView) rootview.findViewById(R.id.friend_icon);
//        TextView friend_name = (TextView) rootview.findViewById(R.id.friend_name);
//        TextView friend_content = (TextView) rootview.findViewById(R.id.friend_content);
//        if (TextUtils.isEmpty(user.getIcon())) {
//            friend_icon.setImageResource(R.drawable.wall);
//        } else {
//            Glide.with(context).load(user.getIcon()).into(friend_icon);
//        }
//
//        if (TextUtils.isEmpty(user.getNick())){
//            friend_name.setText("无名人士");
//        }
//        else {
//            friend_name.setText(user.getNick());
//        }
//
//        if (TextUtils.isEmpty(user.getTel())){
//            friend_content.setText("谢谢您使用表白墙APP！！");
//        }
//        else {
//            friend_content.setText(user.getTel());
//        }
        ViewHoder hoder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_tab2_item, null);
            hoder = new ViewHoder();
            hoder.user_icon= (CircleImageView) convertView.findViewById(R.id.friend_icon);
            hoder.user_name = (TextView) convertView.findViewById(R.id.friend_name);
            hoder.confess_content = (TextView) convertView.findViewById(R.id.friend_content);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        if(TextUtils.isEmpty(user.getIcon())){
            hoder.user_icon.setImageResource(R.drawable.wall);
        }else {
            Glide.with(context).load(user.getIcon()).into(hoder.user_icon);
        }

        if (TextUtils.isEmpty(user.getNick())){
            hoder.user_name.setText("无名人士");
        }
        else {
            hoder.user_name.setText(user.getNick());
        }
        hoder.confess_content.setText(user.getTel());
        return convertView;
    }

    class ViewHoder {
        private CircleImageView user_icon;
        private TextView user_name;
        private TextView confess_content;
    }
}
