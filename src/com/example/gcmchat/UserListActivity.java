package com.example.gcmchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.gcmchat.adapter.UserInfo;
import com.example.gcmchat.adapter.UserListAdapter;
import com.example.gcmchat.gcm.GCMSender;
import com.example.gcmchat.parse.UserDataHandler;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class UserListActivity extends Activity implements OnItemClickListener{
	
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
				mListView.setOnItemClickListener(UserListActivity.this);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final UserInfo info = (UserInfo) mListView.getAdapter().getItem(position);
		showMessageDialog(info);
	}
	
	private void showMessageDialog(final UserInfo info){
		AlertDialog.Builder builder = new Builder(this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dlg_message_input, null);
		final EditText et = (EditText) v.findViewById(R.id.et_dlg_message_input);
		builder.setView(v);
		builder.setPositiveButton("Send", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendMessage(info, et.getText().toString());
			}
		});
		builder.create().show();
	}
	
	private void sendMessage(UserInfo uInfo, String message) {
		GCMSender sender = GCMSender.getInstance();
		HashMap<String, String> payload = new HashMap<String, String>();
		payload.put("text", message);
		sender.sendMessage(uInfo.id, payload);
	}
}
