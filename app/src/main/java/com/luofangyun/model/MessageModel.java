package com.luofangyun.model;

import java.util.List;

public class MessageModel {
	public Data data;
	public static class Data 
	{
		public Message msg, order;
		public List<Merch> merch;
	}
	public static class Message{
		public String title,content;
		public int status;
	}
	public static class Merch{
		public String m_uid,m_nickname,m_time,content_last,applogo,user_openimid,url;
		int rank;
		public int status;
	}
}
