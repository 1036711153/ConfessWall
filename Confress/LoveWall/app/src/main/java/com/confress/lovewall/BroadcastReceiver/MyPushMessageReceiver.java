package com.confress.lovewall.BroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.confress.lovewall.Activity.ShowChattingMsgActivity;
import com.confress.lovewall.R;
import com.confress.lovewall.model.MsgGosn;
import com.confress.lovewall.view.CustomView.CustomDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

public class MyPushMessageReceiver extends BroadcastReceiver {
	public static MsgGosn msgGosn;
	private PushMessageReceiverPresenter presenter;
	@Override
	public void onReceive(final Context context, Intent intent) {
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			String msg = intent
					.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
			Log.e("bmob", "客户端收到推送内容：" + msg);
			 String content = null;
			try {
				JSONObject object=new JSONObject(msg);
				content= object.getString("alert");
				Gson gson=new Gson();
				msgGosn=gson.fromJson(content, MsgGosn.class);
				//添加好友邀请
				if (msgGosn.isaddfriend()==true){
					CustomDialog.Builder builder = new CustomDialog.Builder(context);
					builder.setMessage("“"+msgGosn.getNick()+"”"+"想添加你为好友");
					builder.setTitle("表白墙APP");
					builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							//添加好友操作
							presenter=new PushMessageReceiverPresenter(context,msgGosn);
                            presenter.ReceiveRequest();
						}
					});
					builder.setNegativeButton("拒绝",
							new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
					CustomDialog ad = builder.create();
					ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					ad.setCanceledOnTouchOutside(false);                                   //点击外面区域不会让dialog消失
					ad.show();
                   return;
				}
				if (msgGosn.isRecieveaddfriend()==true){
					//收到好友邀请之后。。。
					presenter=new PushMessageReceiverPresenter(context,msgGosn);
					presenter.RequestSuccess();
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
            //说明这个Activity在栈顶
			if (ShowChattingMsgActivity.class!=null&&ShowChattingMsgActivity.chattingMsgs!=null) {
				ShowChattingMsgActivity.getRequest(msgGosn);
			}else {
				//否则使用通知栏信息通知提示
				NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification.Builder builder = new Notification.Builder(context);
				Intent mIntent = new Intent(context, ShowChattingMsgActivity.class);
				mIntent.putExtra("friend_user_icon", msgGosn.getIcon());
				mIntent.putExtra("friend_user_id", msgGosn.getId());
				PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
				builder.setContentIntent(pendingIntent);
				builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.wall));
				if (TextUtils.isEmpty(msgGosn.getNick())) {
					builder.setContentTitle("LoveWall");
				} else {
					builder.setContentTitle(msgGosn.getNick());
				}
				builder.setSmallIcon(R.drawable.icon_to_send);
				builder.setAutoCancel(true);
				builder.setContentText(msgGosn.getMsg());
				notificationManager.notify(0, builder.build());
			}
		}
	}
}
