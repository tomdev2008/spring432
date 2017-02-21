package com.mvw.websocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Websocket支持
 */
@ServerEndpoint(value = "/websocket/{id}")
public class WebsocketProvider {
	
	private String userId;

	public WebsocketProvider() {
	    super();
    }

	//建立会话
	@OnOpen
	public void open(Session session, @PathParam(value = "id") String id) {
		System.out.println("新用户接入:"+id);
		userId=id;
		try {
			session.getBasicRemote().sendText("hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//消息
	@OnMessage
	public void inMessage(String message,Session session) throws IOException {
		System.out.println("你有新的消息:"+message);
		try {
			session.getBasicRemote().sendText("hello:"+message);
			if("ok".equalsIgnoreCase(message)){
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//关闭
	@OnClose
	public void onClose(Session session,CloseReason reason) {
		System.out.println("有用户退出:"+userId);
	}
}
