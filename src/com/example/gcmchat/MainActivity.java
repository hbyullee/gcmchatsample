package com.example.gcmchat;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.gcmchat.gcm.GCMSender;
import com.example.gcmchat.gcm.GCMUtils;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class MainActivity extends Activity implements OnClickListener {
	Button btn;
	static final Handler mhandler= new Handler();
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);

	  GCMRegistrar.checkDevice(this);
	  GCMRegistrar.checkManifest(this);
	  String regId=GCMRegistrar.getRegistrationId(this);
	  
	  if(regId.equals("")){
		  GCMRegistrar.register(this, GCMUtils.SENDER_ID);
	  }else {
		  
	  }
	  
	  btn =(Button)findViewById(R.id.button1);
	  btn.setOnClickListener(new OnClickListener() {
		
		@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						Sender sender = new Sender(GCMSender.API_KEY);
						com.google.android.gcm.server.Message message = new com.google.android.gcm.server.Message.Builder()
								.addData("title", "welcome")
								.addData("msg", "introduce").build();
						try {
							String regId = GCMRegistrar
									.getRegistrationId(MainActivity.this);
							Result result = sender.send(message, regId, 5);
							Log.v("abc", regId);
							// TODO Auto-generated method stub
							Log.v("abc", regId);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				// TODO Auto-generated method stub

			}
	});
	  
//	  GCMUtils.registerGCM(this);
	
	 }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_show_users:
			startUserListActivity();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void startUserListActivity(){
		Intent i = new Intent(this, UserListActivity.class);
		startActivity(i);
	}
	

	/* public void registerGcm() {
	  GCMRegistrar.checkDevice(this);
	  GCMRegistrar.checkManifest(this);

	  final String regId = GCMRegistrar.getRegistrationId(this);

	  if (regId.equals("")) {
	   GCMRegistrar.register(this, "�꾨줈�앺듃 �꾩씠��);
	  } else {
	   Log.e("reg_id", regId);
	  }
	 }*/
	
	
	}
