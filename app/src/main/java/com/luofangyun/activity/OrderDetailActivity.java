package com.luofangyun.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.luofangyun.R;
import com.luofangyun.adapter.MMerchDetailAdapter;
import com.luofangyun.adapter.OrderDetailAdapter;
import com.luofangyun.config.Constant;
import com.luofangyun.config.HttpMap;
import com.luofangyun.model.MessageDetailModel;
import com.luofangyun.model.OrderDetailModel;

public class OrderDetailActivity extends MessageDetailActivity{

	List<OrderDetailModel.Detail> list=new ArrayList<>();
	OrderDetailAdapter orderDetailAdapter;
	@Override
	protected int layoutId() {
		return R.layout.act_order_detail;
	}

	@Override
	protected void Init() {
		super.Init();
		setTitle("订单消息");
	}

	@Override
	  public void httpDetail()
	   {
		   HttpMap httpMap1=new HttpMap();
		   //mid lin
		   httpMap1.putDataMap("mid", Constant.mid);
		   httpMap1.putDataMap("current_page",String.valueOf(current_page));
		   httpMap1.putDataMap("per_page", "10");
		   httpMap1.setHttpListener("/MyorderList", new HttpMap.HttpListener() {
			@Override
			public void onSuccess(String response, int status) {
			if(status==1)
			{
				hasHttp=false;
				OrderDetailModel	 orderDetailModel=new Gson().fromJson(response,OrderDetailModel.class );
				total_page = orderDetailModel.page.total_page;
				current_page++;
				for (int i = 0; i < orderDetailModel.data.size(); i++) {
					list.add(orderDetailModel.data.get(i));
				}
				if (orderDetailModel.page.current_page==1) {
					orderDetailAdapter = new OrderDetailAdapter(list, OrderDetailActivity.this);
					mListView.setAdapter(orderDetailAdapter);
				} else {
					orderDetailAdapter.notifyDataSetChanged();
				}
			}		
			}
		});
	   }
}
