package com.example.gcmchat.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.gcmchat.R;
import com.example.gcmchat.adapter.UserInfo;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class UserDataHandler {
	
	private static final String TAG = "UserDataHandler";
	final static String SENDER_ID = "64008216994"; // GCM 프로젝트 생성 시 발급 받은 // Project_number
	
	 public static String getPrimaryEmailAddress(Context context) {
	        String email_addr = null;

	        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
	        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
	        for (Account account : accounts) {
	            if (emailPattern.matcher(account.name).matches()) {
	                email_addr = account.name;
	                break;
	            }
	        }

	        return email_addr;
	    }
	
	public static void loginParse(final Context context) {
        final String username = getPrimaryEmailAddress(context);

        if (TextUtils.isEmpty(username)) {
            Log.e(TAG, "Username is EMPTY!!!");
            return;
        }

        ParseUser.logInInBackground(username, username, new LogInCallback() {
            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if (user != null) {
                    ParseUser me = ParseUser.getCurrentUser();
                    Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
                    me.put("lastLogin", calendar.getTime());
                    try {
                        Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                        me.save();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    
                   registerGCM(context);
                    
                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    Toast.makeText(context, context.getString(R.string.account_not_registered) + "\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                    ParseUser user_signup = new ParseUser();

//                    byte[] photo_array = getUserProfilePhotoByteArray(context);
//                    if (photo_array != null) {
//                        ParseFile photoFile = new ParseFile(getUserProfileDisplayName(context).replaceAll("[^a-zA-Z0-9.-]", "_") + ".png", photo_array);
//                        try {
//                            photoFile.save();
//                            user_signup.put("photo", photoFile);
//                        } catch (ParseException e1) {
//                            e1.printStackTrace();
//                        }
//                    }

                    ParseACL acl = new ParseACL();
                    acl.setPublicReadAccess(true);
                    acl.setPublicWriteAccess(true);

                    user_signup.setUsername(username);
                    user_signup.setPassword(username);
                    user_signup.setEmail(username);
                    user_signup.put("phoneNumber", tm.getLine1Number());
                    user_signup.put("imei", tm.getDeviceId());
//                    user_signup.put("displayName", getUserProfileDisplayName(context));
                    user_signup.setACL(acl);

                    user_signup.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now.
                                Toast.makeText(context, R.string.account_registered, Toast.LENGTH_SHORT).show();
                                loginParse(context);
                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                Toast.makeText(context, context.getString(R.string.account_registered_fail) + "\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    // login failed. Look at the ParseException to see what happened.
                    Toast.makeText(context, context.getString(R.string.login_fail) + "\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
	
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
						Log.e(context.getPackageName(), GooglePlayServicesUtil.getErrorString(serviceEnable));
						GCMRegistrar.register(context, SENDER_ID);
					}
					
					
				};
			}.start();
		}
	 
	 public static void getUserLists(Context context, FindCallback<ParseObject> callback) {
		 //TODO implementation..
	 }
}
