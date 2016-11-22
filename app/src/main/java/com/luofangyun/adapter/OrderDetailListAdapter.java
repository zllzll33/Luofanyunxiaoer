package com.luofangyun.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luofangyun.R;
import com.luofangyun.activity.WebActivity;
import com.luofangyun.model.OrderDetailModel;
import com.luofangyun.util.TypeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;


public class OrderDetailListAdapter extends BaseAdapter{
	 Activity activity;
	 List<OrderDetailModel.GoodsList> ListDate;
	 String url;
	 public OrderDetailListAdapter(List<OrderDetailModel.GoodsList> ListDate, Activity activity, String url)
	 {
	 	this.ListDate=ListDate;
	 	this.activity=activity;
	 	this.url=url;
	 }	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=LayoutInflater.from(activity).inflate(R.layout.item_order_detail_list, null);
	
	TextView number=(TextView)view.findViewById(R.id.number);
	TextView content=(TextView)view.findViewById(R.id.content);
	ImageView img=(ImageView)view.findViewById(R.id.img);
	TextView price=(TextView)view.findViewById(R.id.price);
	price.setText("￥"+ListDate.get(position).sp_gdprice);
	number.setText("×"+ListDate.get(position).sp_number);
	content.setText(ListDate.get(position).sp_name);
		TypeUtil.setImageView(img,ListDate.get(position).sp_img);
	view.setOnClickListener(new VonClickListener(position)
	{
		@Override
		public void onClick(View v) {
		Intent intent =new Intent(activity,WebActivity.class);
		intent.putExtra("Url", url);
		activity.startActivity(intent);
		}	
	});
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
	public class VonClickListener implements View.OnClickListener
	{
	int  index;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		public  VonClickListener(int index)
		{
			this.index=index;
		}
	}
}
