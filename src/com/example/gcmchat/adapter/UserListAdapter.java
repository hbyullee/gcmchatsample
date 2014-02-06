package com.example.gcmchat.adapter;

import java.util.ArrayList;

import com.example.gcmchat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 이 앱을 설치한사람들의 리스트를 보여주기 위한 클래스.
 */
public class UserListAdapter extends BaseAdapter {

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
		UserInfo user = mData.get(position);
		
		TextView tvPhoneNum = (TextView) convertView.findViewById(R.id.tv_list_item_user_phone_num);
		tvPhoneNum.setText(user.phoneNum);
		
		return convertView;
	}

}
