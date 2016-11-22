package com.luofangyun.model;

import java.util.List;

public class MessageDetailModel {
    public Page page;
public List<Detail> data;
public static class Detail{
public 	String time;
public List<Value> value;

}
    public class  Page{
        public int total_count,current_page,total_page;
    }
public static class Value{
public 	String mid,imgurl,ptitle,pcontent,set_time,url;
}
}
