package net.cgt.weixin.view.listener;

import net.cgt.weixin.GlobalParams;
import net.cgt.weixin.view.manager.XmppManager;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

/**
 * 单人聊天信息监听类
 * 
 * @author lijian
 * @date 2014-11-23
 */
public class TaxiChatManagerListener implements ChatManagerListener {

	@Override
	public void chatCreated(Chat chat, boolean arg1) {
		chat.addMessageListener(new MessageListener() {

			@Override
			public void processMessage(Chat arg0, Message msg) {//登录用户
				StringUtils.parseName(XmppManager.getInstance().getConnection().getUser());
				//发送消息用户
				msg.getFrom();
				//消息内容
				String body = msg.getBody();
				System.out.println("body--->" + body);
				boolean left = body.substring(0, 1).equals("{");
				boolean right = body.substring(body.length() - 1, body.length()).equals("}");
				if (left && right) {
					try {
						JSONObject obj = new JSONObject(body);
						String type = obj.getString("messageType");
						String chanId = obj.getString("chanId");
						String chanName = obj.getString("chanName");

						System.out.println("---body--->" + body);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				
				Intent intent = new Intent("net.cgt.weixin.chat");
				intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);// 包含从未启动过的应用
				intent.putExtra("from", msg.getFrom());
				intent.putExtra("body", body);
				GlobalParams.activity.sendBroadcast(intent);
			}
		});
	}

}
