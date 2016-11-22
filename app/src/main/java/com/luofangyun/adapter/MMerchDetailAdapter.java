package com.luofangyun.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luofangyun.R;
import com.luofangyun.customeview.MyListView;
import com.luofangyun.model.MessageDetailModel;


public class MMerchDetailAdapter extends BaseAdapter{
	 Activity activity;
	 List<MessageDetailModel.Detail> ListDate;
	 public MMerchDetailAdapter(List<MessageDetailModel.Detail> ListDate, Activity activity)
	 {
	 	this.ListDate=ListDate;
	 	this.activity=activity;
	 }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListDate.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ListDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(activity).inflate(R.layout.item_merch_detail, null);
		TextView time=(TextView)view.findViewById(R.id.time);
		time.setText(ListDate.get(position).time);
		MyListView m_listview=(MyListView)view.findViewById(R.id.m_listview);
		 MerchDetailListAdapter merchDetailListAdapter=new MerchDetailListAdapter(ListDate.get(position).value,activity);
		 m_listview.setAdapter(merchDetailListAdapter);
		 return view;
	}
 
}
