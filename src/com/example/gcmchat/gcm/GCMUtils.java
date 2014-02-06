package com.example.gcmchat.gcm;

import java.io.IOException;

import android.content.Context;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMUtils {
	
	final public static String SENDER_ID = "64008216994"; // GCM 占쎄쑬以덌옙�븍뱜 占쎌빘苑�占쏙옙獄쏆뮄��獄쏆룇占�// Project_number
	
	public static void registerGCM(final Context context) {
		new Thread(){
			public void run() {
				
				/*
				 * GCM占쏙옙占쎌꼷��占쎈챸�싷옙占쏙옙袁る퉸 占쏙옙�댐옙獄쎻뫗�뉛옙占쏙쭗�ㅻあ 筌�쑴�쒙옙�롢늺占쏙옙callback占쏙옙receiver 獄쎻뫗�뉛옙�곗쨮 占쎈벏釉울옙占�
				 */
                String regId     = null;
                try {
					regId = GoogleCloudMessaging.getInstance(context).register(SENDER_ID);
				} catch (IOException e) {
					e.printStackTrace();
				}

				int serviceEnable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

				/*
				 * 2013.11.20 JuL GooglePlayService 占쎈똻肉�占쎈벡�쏙옙�뤿선占쎈뜄��揶쏅�占�占쎄쑬�믭옙占썲첎�뉖뼄.
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
				if (regId == null && serviceEnable != 0){	//google play service揶쏉옙占쎌늿�됵옙�륅옙 占쎈봾��占썩뫀彛싷옙占썲칰�뚯뒭
					android.util.Log.e(context.getPackageName(), GooglePlayServicesUtil.getErrorString(serviceEnable));
					GCMRegistrar.register(context, SENDER_ID);
				}
				
				
			};
		}.start();
	}
}
