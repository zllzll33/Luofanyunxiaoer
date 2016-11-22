package com.luofangyun.adapter;

import java.util.List;

import com.luofangyun.R;
import com.luofangyun.customeview.MyListView;
import com.luofangyun.model.OrderDetailModel;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderDetailAdapter extends BaseAdapter{
	 Activity activity;
	 List<OrderDetailModel.Detail> ListDate;
	 public OrderDetailAdapter(List<OrderDetailModel.Detail> ListDate, Activity activity)
	 {
	 	this.ListDate=ListDate;
	 	this.activity=activity;
	 }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(activity).inflate(R.layout.item_merch_detail, null);
		TextView time=(TextView)view.findViewById(R.id.time);
		time.setText(ListDate.get(position).time);
		MyListView m_listview=(MyListView)view.findViewById(R.id.m_listview);
		OrderNickAdapter orderNickAdapter=new OrderNickAdapter(ListDate.get(position).value,activity);
		 m_listview.setAdapter(orderNickAdapter);
		 return view;
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
}
