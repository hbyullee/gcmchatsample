package com.example.gcmchat;

import com.example.gcmchat.gcm.GCMUtils;
import com.google.android.gcm.GCMRegistrar;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	GCMUtils.registerGCM(this);
	 }


	/* public void registerGcm() {
	  GCMRegistrar.checkDevice(this);
	  GCMRegistrar.checkManifest(this);

	  final String regId = GCMRegistrar.getRegistrationId(this);

	  if (regId.equals("")) {
	   GCMRegistrar.register(this, "프로젝트 아이디");
	  } else {
	   Log.e("reg_id", regId);
	  }
	 }*/
	}
