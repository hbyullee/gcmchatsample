package com.example.gcmchat;

import java.util.ArrayList;
import java.util.List;

import com.example.gcmchat.adapter.UserInfo;
import com.example.gcmchat.adapter.UserListAdapter;
import com.example.gcmchat.parse.UserDataHandler;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class UserListActivity extends Activity {
	
	private LinearLayout mPrgLayout = null;
	private ListView mListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		init();
	}
	
	private void init(){
		mPrgLayout = (LinearLayout) findViewById(R.id.ll_user_list_prg_container);
		mListView = (ListView) findViewById(R.id.lv_user_list);
		UserDataHandler.getUserLists(this, new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				ArrayList<UserInfo> infos = UserDataHandler.convertUserInfo(arg0);
				UserListAdapter adapter = new UserListAdapter(UserListActivity.this, infos);
				mPrgLayout.setVisibility(View.GONE);
				mListView.setAdapter(adapter);
			}
		});
	}

}
