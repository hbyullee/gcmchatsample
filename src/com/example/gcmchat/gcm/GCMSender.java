package com.example.gcmchat.gcm;

import java.io.IOException;
import java.util.Map;

import android.test.suitebuilder.annotation.Suppress;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * GCM 발송???�스?�하�??�한 class
 * @author JuL
 *
 */
@Suppress
public class GCMSender extends Sender{
	private Result 				gcmResult;				//GCM Result(?�일?�송)
	
	private static String 		API_KEY = "AIzaSyCTo2AI8fazZYSonbOzgFmmdgZ-QCsOuug";
	//메세�?�� 고유 ID(?)?�도�??�각?�면 ?�다. 메세�?�� 중복?�신??막기 ?�해 ?�덤값을 �?��
	private static String 		COLLAPSE_KEY = String.valueOf(Math.random() % 100 + 1);
	//기기�??�성???�태???? 보여�?것인�?
	private static boolean 	DELAY_WHILE_IDLE = true;
	//기기�?비활?�화  ?�태????GCM Storage??보�??�는 ?�간
	private static int			TIME_TO_LIVE = 3;
	//메세�? ?�송 ?�패???�시?�할 ?�수
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
	
	private Message buildMessage(Map<String, String> payload){
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
