package com.confress.lovewall.BroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.confress.lovewall.Activity.LookTrickActivity;
import com.confress.lovewall.R;
import com.confress.lovewall.model.MsgGosn;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

public class MyPushMessageReceiver extends BroadcastReceiver {
	public static MsgGosn msgGosn;
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			String msg = intent
					.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
			Log.e("bmob", "客户端收到推送内容：" + msg);
			 String content = null;
//			MsgGosn msgGosn=null;
			try {
				JSONObject object=new JSONObject(msg);
				content= object.getString("alert");
				Gson gson=new Gson();
				msgGosn=gson.fromJson(content, MsgGosn.class);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			NotificationManager	notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification.Builder builder = new Notification.Builder(context);
			Intent mIntent = new Intent(context, LookTrickActivity.class);
//			mIntent.putExtra("msgGosn",msgGosn);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
			builder.setContentIntent(pendingIntent);
			builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.wall));
			if(TextUtils.isEmpty(msgGosn.getNick())){
				builder.setContentTitle("LoveWall");
			}else{
				builder.setContentTitle(msgGosn.getNick());
			}
			builder.setSmallIcon(R.drawable.icon_to_send);
			builder.setAutoCancel(true);
			builder.setContentText(msgGosn.getMsg());
			notificationManager.notify(0, builder.build());
		}
	}
}
