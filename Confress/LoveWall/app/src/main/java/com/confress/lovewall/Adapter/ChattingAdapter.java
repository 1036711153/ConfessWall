package com.confress.lovewall.Adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.confress.lovewall.R;
import com.confress.lovewall.model.ChattingMsg;

import cn.bmob.v3.Bmob;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingAdapter extends BaseAdapter{

	private List<ChattingMsg> trickses;
    private Context context;
	private String my_icon_path;
	private String friend_icon_path;
	private String uid;

	public ChattingAdapter(List<ChattingMsg> trickses, Context context, String my_icon_path, String friend_icon_path
	,String uid) {
		this.trickses = trickses;
		this.context = context;
		this.my_icon_path = my_icon_path;
		this.friend_icon_path = friend_icon_path;
		this.uid=uid;
	}

	@Override
	public int getCount() {
		return trickses.size();
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
		ChattingMsg tricks=trickses.get(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.chatting_item, null);
			viewHolder = new ViewHolder();
			viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
			viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
			viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
			viewHolder.my_icon= (CircleImageView) view.findViewById(R.id.my_icon);
			viewHolder.friend_icon= (CircleImageView) view.findViewById(R.id.friend_icon);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		if (!uid.equals(tricks.getUid1())) {
			viewHolder.rightLayout.setVisibility(View.GONE);
			viewHolder.my_icon.setVisibility(View.GONE);
			viewHolder.friend_icon.setVisibility(View.VISIBLE);
			viewHolder.leftLayout.setVisibility(View.VISIBLE);
			viewHolder.leftMsg.setText(tricks.getContent());
			if(TextUtils.isEmpty(friend_icon_path)){
				viewHolder.friend_icon.setImageResource(R.drawable.wall);
			}else {
				Glide.with(context).load(friend_icon_path).into(viewHolder.friend_icon);
			}
		} else if(uid.equals(tricks.getUid1())) {
			viewHolder.leftLayout.setVisibility(View.GONE);
			viewHolder.friend_icon.setVisibility(View.GONE);

			viewHolder.my_icon.setVisibility(View.VISIBLE);
			viewHolder.rightLayout.setVisibility(View.VISIBLE);
			viewHolder.rightMsg.setText(tricks.getContent());
			if(TextUtils.isEmpty(my_icon_path)){
				viewHolder.my_icon.setImageResource(R.drawable.wall);
			}else {
				Glide.with(context).load(my_icon_path).into(viewHolder.my_icon);
			}
		}
		return view;
	}
	class ViewHolder {
		LinearLayout leftLayout;
		LinearLayout rightLayout;
		CircleImageView my_icon;
		CircleImageView friend_icon;
		TextView leftMsg;
		TextView rightMsg;
		
	}

}
