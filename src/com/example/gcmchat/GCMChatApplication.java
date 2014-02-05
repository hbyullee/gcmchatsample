package com.example.gcmchat;

import com.example.gcmchat.constant.Constants;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Application;

public class GCMChatApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initParse();

	    
		
	}

	private void initParse() {
	
		Parse.initialize(this, Constants.APPLICATION_ID, Constants.CLIENT_KEY);
		
	}
}
