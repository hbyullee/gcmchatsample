package com.example.gcmchat.gcm;

import java.io.IOException;
import java.util.Map;

import android.test.suitebuilder.annotation.Suppress;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * GCM ë°œì†¡???ŒìŠ¤?¸í•˜ê¸??„í•œ class
 * @author JuL
 *
 */
@Suppress
public class GCMSender extends Sender{
	private Result 				gcmResult;				//GCM Result(?¨ì¼?„ì†¡)
	
	private static String 		API_KEY = "AIzaSyCTo2AI8fazZYSonbOzgFmmdgZ-QCsOuug";
	//ë©”ì„¸ì§?˜ ê³ ìœ  ID(?)?•ë„ë¡??ê°?˜ë©´ ?œë‹¤. ë©”ì„¸ì§?˜ ì¤‘ë³µ?˜ì‹ ??ë§‰ê¸° ?„í•´ ?œë¤ê°’ì„ ì§? •
	private static String 		COLLAPSE_KEY = String.valueOf(Math.random() % 100 + 1);
	//ê¸°ê¸°ê°??œì„±???íƒœ???? ë³´ì—¬ì¤?ê²ƒì¸ì§?
	private static boolean 	DELAY_WHILE_IDLE = true;
	//ê¸°ê¸°ê°?ë¹„í™œ?±í™”  ?íƒœ????GCM Storage??ë³´ê??˜ëŠ” ?œê°„
	private static int			TIME_TO_LIVE = 3;
	//ë©”ì„¸ì§? ?„ì†¡ ?¤íŒ¨???¬ì‹œ?„í•  ?Ÿìˆ˜
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
