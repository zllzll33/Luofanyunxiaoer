package com.luofangyun.adapter;

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
import com.luofangyun.model.MessageDetailModel;
import com.luofangyun.util.TypeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;



import java.util.List;

public class MerchDetailListAdapter extends BaseAdapter{
	 Activity activity;
	 List<MessageDetailModel.Value> ListDate;
	 
	 public MerchDetailListAdapter(List<MessageDetailModel.Value> ListDate, Activity activity)
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
		View view=LayoutInflater.from(activity).inflate(R.layout.item_time_detail, null);
		view.setOnClickListener(new VonClickListener(position)
		{
			@Override
			public void onClick(View v) {
			Intent intent =new Intent(activity,WebActivity.class);
			intent.putExtra("Url", ListDate.get(index).url);
			activity.startActivity(intent);
			}	
		});
	TextView title=(TextView)view.findViewById(R.id.title);
	TextView content=(TextView)view.findViewById(R.id.content);
	ImageView img=(ImageView)view.findViewById(R.id.img);
	title.setText(ListDate.get(position).ptitle);
	content.setText(ListDate.get(position).pcontent);
		TypeUtil.setImageView(img,ListDate.get(position).imgurl);
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
