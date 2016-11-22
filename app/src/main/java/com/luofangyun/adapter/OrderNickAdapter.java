package com.luofangyun.adapter;

import java.util.List;

import com.luofangyun.R;
import com.luofangyun.customeview.MyListView;
import com.luofangyun.model.OrderDetailModel;
import com.luofangyun.util.TypeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderNickAdapter extends BaseAdapter{
	Activity activity;
	 List<OrderDetailModel.Value> ListDate;
	 public  OrderNickAdapter(List<OrderDetailModel.Value> ListDate, Activity activity)
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
		View view=LayoutInflater.from(activity).inflate(R.layout.item_order_nick, null);
		ImageView header=(ImageView)view.findViewById(R.id.header);
		TypeUtil.setImageView(header,ListDate.get(position).applogo);
		TextView nick=(TextView)view.findViewById(R.id.nick);
		nick.setText(ListDate.get(position).u_name);
		TextView pay_status=(TextView)view.findViewById(R.id.pay_status);
		
		if(ListDate.get(position).o_pstatus.equals("1"))
		   pay_status.setText("已支付");
		else
		   pay_status.setText("未支付");
		TextView price=(TextView)view.findViewById(R.id.price);
		TextView number=(TextView)view.findViewById(R.id.number);
		price.setText("总价 ￥"+ListDate.get(position).o_price);
		number.setText("共"+ListDate.get(position).o_number+"件商品");
		MyListView m_listview=(MyListView)view.findViewById(R.id.m_listview);
		OrderDetailListAdapter orderDetailListAdapter=new OrderDetailListAdapter(ListDate.get(position).goods, activity,ListDate.get(position).url);
		m_listview.setAdapter(orderDetailListAdapter);	  
		return view;
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
