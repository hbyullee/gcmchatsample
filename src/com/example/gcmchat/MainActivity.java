package com.example.gcmchat;

import java.io.IOException;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.gcmchat.gcm.GCMReceiver;
import com.example.gcmchat.gcm.GCMSender;
import com.example.gcmchat.gcm.GCMUtils;
import com.example.gcmchat.parse.UserDataHandler;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.android.gms.internal.c;
import com.parse.ParseUser;

public class MainActivity extends Activity implements OnClickListener {
	Button btn;
	static final Handler mhandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initParseUser();

	}
	
	private void initParseUser(){
		ParseUser curUser = ParseUser.getCurrentUser();
		if(curUser == null) {
			UserDataHandler.loginParse(this);
		}
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

	private void startUserListActivity() {
		Intent i = new Intent(this, UserListActivity.class);
		startActivity(i);
	}

	/*
	 * public void registerGcm() { GCMRegistrar.checkDevice(this);
	 * GCMRegistrar.checkManifest(this);
	 * 
	 * final String regId = GCMRegistrar.getRegistrationId(this);
	 * 
	 * if (regId.equals("")) { GCMRegistrar.register(this, "�꾨줈�앺듃 �꾩씠��); }
	 * else { Log.e("reg_id", regId); } }
	 */

}
