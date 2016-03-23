package com.confress.lovewall.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;
import com.confress.lovewall.model.MessageWall;
import com.confress.lovewall.view.CustomView.RoundImageView;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2016/3/13.
 */
public class NewsAndHotAdapter extends BaseAdapter {
    private List<MessageWall> messageWalls;
    private Context context;



    public NewsAndHotAdapter(List<MessageWall> messageWalls, Context context) {
        this.messageWalls = messageWalls;
        this.context = context;
    }

    public OnMyItemClickListener mOnItemClickListener;

    public void setMyOnItemClickListener(OnMyItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public interface OnMyItemClickListener {
        void onSupportClick(View view, int position,MessageWall messageWall,boolean isClick,ImageView imageView,TextView textView);
        void onCollectionClick(View view, int position,MessageWall messageWall,boolean isClick,ImageView imageView,TextView textView);
        void onConmmentClick(View view, int position,MessageWall messageWall);
        void onContentClick(View view, int position,MessageWall messageWall);
        void onUserIconClick(View view, int position,MessageWall messageWall);
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
        final MessageWall messageWallitem = messageWalls.get(position);
        final boolean[] iscollectionclick = new boolean[1];
        final boolean[] issupportclick = new boolean[1];

        ViewHoder hoder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.newhotfragmebt_item, null);
            hoder = new ViewHoder();
            hoder.content= (LinearLayout) convertView.findViewById(R.id.id_content);
            hoder.user_icon = (CircleImageView) convertView.findViewById(R.id.user_icon);
            hoder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            hoder.update_time = (TextView) convertView.findViewById(R.id.update_time);
            hoder.confess_content = (TextView) convertView.findViewById(R.id.confess_content);
            hoder.collection_count = (TextView) convertView.findViewById(R.id.collection_count);
            hoder.message_count = (TextView) convertView.findViewById(R.id.message_count);
            hoder.support_count = (TextView) convertView.findViewById(R.id.support_count);

            hoder.confess_image = (ImageView) convertView.findViewById(R.id.confess_image);
            hoder.collection_image = (ImageView) convertView.findViewById(R.id.collection_image);
            hoder.message_iamge = (ImageView) convertView.findViewById(R.id.message_iamge);
            hoder.support_image = (ImageView) convertView.findViewById(R.id.support_image);


            hoder.collection = (CardView) convertView.findViewById(R.id.collection);
            hoder.message = (CardView) convertView.findViewById(R.id.message);
            hoder.support = (CardView) convertView.findViewById(R.id.support);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        //需要查询User的相关数据


        Log.e("icon",position+"   "+messageWallitem.getUser().getIcon());
        if(TextUtils.isEmpty(messageWallitem.getUser().getIcon())){
            hoder.user_icon.setImageResource(R.drawable.wall);
        }else {
            Glide.with(context).load(messageWallitem.getUser().getIcon()).into(hoder.user_icon);
        }
        if (!TextUtils.isEmpty(messageWallitem.getConfess_image())) {
            hoder.confess_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(messageWallitem.getConfess_image()).into(hoder.confess_image);
        } else {
            hoder.confess_image.setVisibility(View.GONE);
        }
//        Log.e("nick",position+"   "+messageWallitem.getUser());
        if (TextUtils.isEmpty(messageWallitem.getUser().getNick())){
            hoder.user_name.setText("无名人士");
        }
        else {
            hoder.user_name.setText(messageWallitem.getUser().getNick());
        }

        hoder.update_time.setText(messageWallitem.getUpdatedAt());
        hoder.confess_content.setText(messageWallitem.getConfess_content());
        hoder.collection_count.setText(messageWallitem.getCollection_count() + "");
        hoder.message_count.setText(messageWallitem.getComment_count() + "");
        hoder.support_count.setText(messageWallitem.getSupport_count() + "");

        final ViewHoder finalHoder = hoder;
        hoder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iscollectionclick[0] =!iscollectionclick[0];
                mOnItemClickListener.onCollectionClick(v, position,messageWallitem, iscollectionclick[0], finalHoder.collection_image,finalHoder.collection_count);
            }
        });
        hoder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onConmmentClick(v, position,messageWallitem);
            }
        });
        hoder.support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                issupportclick[0] =!issupportclick[0];
                mOnItemClickListener.onSupportClick(v, position,messageWallitem, issupportclick[0],finalHoder.support_image,finalHoder.support_count);
            }
        });
        //点击内容
        hoder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onContentClick(v, position,messageWallitem);
            }
        });
        //点击人头像
        hoder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onUserIconClick(v, position,messageWallitem);
            }
        });

        return convertView;
    }


    class ViewHoder {
        private LinearLayout content;

        private CircleImageView user_icon;

        private TextView user_name;
        private TextView update_time;
        private TextView confess_content;
        private TextView collection_count;
        private TextView message_count;
        private TextView support_count;

        private ImageView confess_image;

        private ImageView collection_image;
        private ImageView message_iamge;
        private ImageView support_image;

        private CardView collection;
        private CardView message;
        private CardView support;
    }
}
