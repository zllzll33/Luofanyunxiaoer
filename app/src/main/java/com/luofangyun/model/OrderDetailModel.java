package com.luofangyun.model;

import java.util.List;

import android.text.StaticLayout;


public class OrderDetailModel {
	public Page page;
	public  List<Detail> data;
	public static class Detail{
	public 	String time;
	public List<Value> value;	
	}
	public static class Value{
	public 	String o_id,u_name,applogo,url,o_price,o_pstatus,o_number;
	public  List<GoodsList> goods;
	}
	public static class GoodsList{
	public  String 	sp_name,sp_gdprice,sp_number,sp_img;
	}
	public class  Page{
		public int total_count,current_page,total_page;
	}
}
