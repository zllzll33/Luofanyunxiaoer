package com.luofangyun.config;

import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.luofangyun.util.TypeUtil;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StringRequestPost extends StringRequest {
	private String pStr;
	private Map<String, String> mParams = null;
	public StringRequestPost(int method, String url, Listener<String> listener,
							 ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}
	public StringRequestPost(String url, Map<String, String> params, Listener<String> listener,
							 ErrorListener errorListener) {
		super(Method.POST, url, listener, errorListener);
		this.mParams = params;
	}
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {

		 String str = null;
	        try {
	            str = new String(response.data,"utf-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Charset", "UTF-8");
		headers.put("Content-Type","application/x-www-form-urlencoded");
		return headers;
	}
	
	//发起post请求传递的参数
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		String mapStr= TypeUtil.Map2JsonStr(mParams);
		Log.e("postData",mapStr);
		return mParams;
	}


}
