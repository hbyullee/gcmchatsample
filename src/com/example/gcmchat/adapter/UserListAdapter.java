package com.example.gcmchat.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.gcmchat.R;
import com.example.gcmchat.constant.Constants;
import com.example.gcmchat.gcm.GCMSender;
import com.google.android.gcm.server.Sender;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter {
	private UserInfo user;
	private Context mContext = null;
	private LayoutInflater mInflater = null;
	
	private ArrayList<UserInfo> mData = null;
	
	public UserListAdapter(Context context, ArrayList<UserInfo> userInfos) {
		this.mContext = context;
		this.mData = userInfos;
		init();
	}
	
	private void init(){
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData == null ? null : mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_user, null);
		}
		user = mData.get(position);
		
		TextView tvPhoneNum = (TextView) convertView.findViewById(R.id.tv_list_item_user_phone_num);
		tvPhoneNum.setText(user.phoneNum);
		
		return convertView;
	}

}
