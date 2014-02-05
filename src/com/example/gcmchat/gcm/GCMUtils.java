package com.example.gcmchat.gcm;

import java.io.IOException;

import android.content.Context;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMUtils {
	
	final static String SENDER_ID = "64008216994"; // GCM �꾨줈�앺듃 �앹꽦 ��諛쒓툒 諛쏆� // Project_number
	
	public static void registerGCM(final Context context) {
		new Thread(){
			public void run() {
				
				/*
				 * GCM���섏쐞 �명솚���꾪빐 ��援�諛⑹떇��紐⑤몢 梨꾩슜�섎㈃��callback��receiver 諛⑹떇�쇰줈 �듯븿��
				 */
                String regId     = null;
                try {
					regId = GoogleCloudMessaging.getInstance(context).register(SENDER_ID);
				} catch (IOException e) {
					e.printStackTrace();
				}

				int serviceEnable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

				/*
				 * 2013.11.20 JuL GooglePlayService �댁뿉 �뺤쓽�섏뼱�덈뒗 媛믪� �꾨옒��媛숇떎.
				 * switch (errorCode)
				 * { case 0:
				 * 		return "SUCCESS";
				 *   case 1:
				 * 		return "SERVICE_MISSING";
				 *   case 2:
				 *   	return "SERVICE_VERSION_UPDATE_REQUIRED";
				 *   case 3:
				 *   	return "SERVICE_DISABLED";
				 *   case 9:
				 *   	return "SERVICE_INVALID"; }
				 *
				 * return "UNKNOWN_ERROR_CODE";
				 */
				if (regId == null && serviceEnable != 0){	//google play service媛��숈옉�섏� �딅뒗 �⑤쭚��寃쎌슦
					android.util.Log.e(context.getPackageName(), GooglePlayServicesUtil.getErrorString(serviceEnable));
					GCMRegistrar.register(context, SENDER_ID);
				}
				
				
			};
		}.start();
	}
}
