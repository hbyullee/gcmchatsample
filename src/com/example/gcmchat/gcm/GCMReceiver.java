package com.example.gcmchat.gcm;

import java.util.List;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class GCMReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(final Context context, Intent intent) {
		if (intent.getAction() == null) {
			return;
		}
		
		if("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())){
			//google push를 사용하고 있네? 등록에 대한 응답이 왔다.
			String registrationId = intent.getStringExtra("registration_id");
			String error = intent.getStringExtra("error");
			if(registrationId != null){						//등록에 성공했다.
                Log.i(context.getPackageName(), "regId = "+registrationId);
                
                //parse에 등록하자.
                insertToParse(context, registrationId);
			}
			
			else{							//뭔가 문제가 발생했다.
				Log.e(context.getPackageName(), " GCMReceiver - onReceive() error: " + error);
			}
		}
		else if("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())){
			//google push를 사용하고 있네? 뭔가 푸쉬가 왔다.
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
			String msgType = gcm.getMessageType(intent);
			if(msgType.equals("gcm")){//정상 메시지일 경우만 app에 전달하자.
				StringBuilder sb = new StringBuilder();
				
				for (String key : intent.getExtras().keySet()) {
					Log.i(context.getPackageName(), key+"/"+intent.getStringExtra(key));
					sb.append(key+"/"+intent.getStringExtra(key)).append("\n");
				}
				Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
			}
			else{
				//TODO : 예외 처리(send_error, deleted_messages, service_not_available인데, 이걸 해 줘야 하나 말아야 하나?
			}
		}
		else {
			Log.e(context.getPackageName(), "Unknown action : " + intent.getAction());
			// 알 수 없는 액션.
			// 가이드되지 않은 액션을 manifest 에 포함시킨 경우.
			// 우리가 해석할 수 있는 포맷이 아닌, 다른 액션이다.
		}
	}

	private void insertToParse(final Context context, final String registrationId) {
		//등록하기 전에 먼저 이미 data가 있는지 확인.
		ParseQuery<ParseObject> checkQuery = new ParseQuery<ParseObject>("GCMInfo");
        checkQuery.whereEqualTo("phoneNumber", ParseUser.getCurrentUser().getString("phoneNumber"));
        checkQuery.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> findedList, ParseException e) {
				if(e != null){
					e.printStackTrace();
				}
				else{
					boolean needInsert = true;
					//이미 data가 있는 것이 있다면 현재 값이랑 비교해서 처리하자.
					if(findedList.size() > 0){
						for (ParseObject parseObject : findedList) {
							String preRegId = parseObject.getString("regId");
							if(!registrationId.equals(preRegId)){
								parseObject.deleteInBackground();
							}
							else{	//같은 data가 이미 저장되어 있으므로 insert해줄 필요 없다.
								Log.i(context.getPackageName(), "Already exist GCM info.");
								needInsert = false;
							}
						}
					}
					
					if(needInsert){
						ParseObject pObj = new ParseObject("GCMInfo");
		                pObj.put("phoneNumber", ParseUser.getCurrentUser().getString("phoneNumber"));
		                pObj.put("regId", registrationId);
		                
		                //ACL은 알아서 설정될 것이다.
		                pObj.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								if(e != null){
									e.printStackTrace();
								}
								else{
									Log.i(context.getPackageName(), "Success to save the regId.");
								}
							}
						});
					}
					
				}
			}
		});
	}

}
