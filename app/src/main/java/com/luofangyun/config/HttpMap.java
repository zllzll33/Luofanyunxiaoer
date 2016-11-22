package com.luofangyun.config;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.luofangyun.util.TypeUtil;

public class HttpMap {
	  public static  boolean Debug=true;
	    private String Url;
	    private Map<String, String> DataMap ;
	   private Response.ErrorListener errorListener;
	   private Response.Listener<String> listener;
	    private  HttpListener httpListener;
	    private String responsStr;
	  public static String  server_user="http://h.luofangyun.com";
	    private String Server="http://a.luofangyun.com/Api";
	    public HttpMap() {
	        DataMap = new HashMap<String, String>();
	        errorListener = new Response.ErrorListener() {
	            @Override
	            public void onErrorResponse(VolleyError error) {
					if (error instanceof TimeoutError) {
						TypeUtil.showToast("无网络连接~！");
					} else if (isServerProblem(error)) {
						TypeUtil.showToast("连接服务器失败~！");
					} else if (isNetworkProblem(error)) {
						TypeUtil.showToast("网络异常,请稍后再试~！");
					}
				}
	        };
	        listener = new Response.Listener<String>() {

	            @SuppressLint("NewApi")
				@Override
	            public void onResponse(String response) {
	                Log.e("response",response); 	              
	                    responsStr=response;
	                try {
	                    JSONObject json = new JSONObject(responsStr);
	                   String info=json.optString("info");  
//	                   Log.e("info",info);	                	
	                    int status = json.optInt("status");                          
	                    if(status==0)
							TypeUtil.showToast(info);
	                if(httpListener!=null)
	                    httpListener.onSuccess(responsStr,status);
	                }catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            }
	        };
	    }
	 
	    public Response.ErrorListener getErrorListener()
	    {
	        return errorListener;
	    }
	    public Response.Listener getListener()
	    {
	        return  listener;
	    }
	    public Map<String, String> getDataMap()
	    {
	        return DataMap;
	    }
	   
	    public void putDataMap(String type,String data)
	    {
	        DataMap.put(type,data);
	    }
	    public void setHttpListener(String url,HttpListener httpListener)
	    {
	        this.httpListener=httpListener;
	        SendHttp(Server+url);
	    }
	    public void setHttpListener(String url,HttpListener httpListener,boolean all_url)
	    {
	        this.httpListener=httpListener;
	        SendHttp(url);
	    }
	   public  interface  HttpListener
	   {
	       public void onSuccess(String response, int status);
	   }
	   
	   private void SendHttp(String url)
	   {
		   StringRequestPost request = new StringRequestPost(url,
	               getDataMap(),getListener()
	               ,getErrorListener());
	       App.getInstance().HttpPost(request);
	   }

	private static boolean isServerProblem(Object error) {
		return (error instanceof ServerError)
				|| (error instanceof AuthFailureError);
	}
	private static boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError)
				|| (error instanceof NoConnectionError);
	}
}
