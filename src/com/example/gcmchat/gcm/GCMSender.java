package com.example.gcmchat.gcm;

import java.io.IOException;
import java.util.Map;

import android.test.suitebuilder.annotation.Suppress;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMSender extends Sender{
	private Result 				gcmResult;				//GCM Result(?�일?�송)
	
	public static String 		API_KEY = "AIzaSyCTo2AI8fazZYSonbOzgFmmdgZ-QCsOuug";
	//메세지의 고유 ID(?)정도로 생각하면 된다. 메세지의 중복수신을 막기 위해 랜덤값을 지정
	private static String 		COLLAPSE_KEY = String.valueOf(Math.random() % 100 + 1);
	//기기가 활성화 상태일 때  보여줄 것인지.
	private static boolean 	DELAY_WHILE_IDLE = true;
	//기기가 비활성화  상태일 때 GCM Storage에 보관되는 시간
	private static int			TIME_TO_LIVE = 3;
	//메세지  전송 실패시 재시도할 횟수
	private static int 			RETRY = 3;
	
	private static GCMSender mInstance;
	
	private GCMSender(){
		super(API_KEY);
	}
	
	public static GCMSender getInstance(){
		if(mInstance == null){
			mInstance = new GCMSender();
		}
		
		return mInstance;
	}
	
	public static Message buildMessage(Map<String, String> payload){
		Message.Builder builder = new Message.Builder()
	    .collapseKey(COLLAPSE_KEY)
	    .delayWhileIdle(DELAY_WHILE_IDLE)
	    .timeToLive(TIME_TO_LIVE);
		
		for (String key : payload.keySet()) {
			builder.addData(key, payload.get(key));
		}
		
		return builder.build();
	}
	
	public void sendMessage(final String regId, final Map<String, String> payload){
		new Thread() {
			
			@Override
			public void run() {
				try {
					gcmResult = send(buildMessage(payload), regId, RETRY);
				} catch(IOException e) {
					e.printStackTrace();
				}
				
			}
		}.start();
		
		
	}
}
