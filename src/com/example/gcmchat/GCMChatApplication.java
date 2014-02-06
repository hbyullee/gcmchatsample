package com.example.gcmchat;

import android.app.Application;

import com.example.gcmchat.constant.Constants;
import com.parse.Parse;
import com.parse.ParseACL;

public class GCMChatApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initParse();

	    
		
	}

	private void initParse() {
	
		Parse.initialize(this, Constants.APPLICATION_ID, Constants.CLIENT_KEY);
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}
}
