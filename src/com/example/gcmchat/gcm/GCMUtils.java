package com.example.gcmchat.gcm;

import java.io.IOException;

import android.content.Context;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMUtils {
	
	final static String SENDER_ID = "???????"; // GCM 프로젝트 생성 시 발급 받은 // Project_number
	
	public static void registerGCM(final Context context) {
		new Thread(){
			public void run() {
				
				/*
				 * GCM의 하위 호환을 위해 신/구 방식을 모두 채용하면서 callback은 receiver 방식으로 통함함.
				 */
                String regId     = null;
                try {
					regId = GoogleCloudMessaging.getInstance(context).register(SENDER_ID);
				} catch (IOException e) {
					e.printStackTrace();
				}

				int serviceEnable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

				/*
				 * 2013.11.20 JuL GooglePlayService 내에 정의되어있는 값은 아래와 같다.
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
				if (regId == null && serviceEnable != 0){	//google play service가 동작하지 않는 단말일 경우
					android.util.Log.e(context.getPackageName(), GooglePlayServicesUtil.getErrorString(serviceEnable));
					GCMRegistrar.register(context, SENDER_ID);
				}
				
				
			};
		}.start();
	}
}
