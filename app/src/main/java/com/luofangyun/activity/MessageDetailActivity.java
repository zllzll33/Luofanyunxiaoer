package com.luofangyun.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.luofangyun.R;
import com.luofangyun.adapter.MMerchDetailAdapter;
import com.luofangyun.config.Constant;
import com.luofangyun.config.HttpMap;
import com.luofangyun.model.MessageDetailModel;
import com.luofangyun.util.TypeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/11/16.
 */
public class MessageDetailActivity extends BaseActivity {
    @InjectView(R.id.mListView)
    ListView mListView;
    List<MessageDetailModel.Detail> list=new ArrayList<>();
    MMerchDetailAdapter merchDetailAdapter;
   protected int current_page=1,total_page=0;
    protected boolean hasHttp=false;
    @Override
    protected int layoutId() {
        return R.layout.act_merchs_detail;
    }
    @Override
    protected void Init() {
        super.Init();
        setTitle("系统消息");
        httpDetail();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem==0){
//                    Log.e("log", "滑到顶部");
                }
                if(visibleItemCount+firstVisibleItem==totalItemCount){
//                    Log.e("log", "滑到底部");
                    if(!hasHttp) {
                        hasHttp=true;
                        if(total_page>=current_page) {
                            httpDetail();
                        }
                        else if(current_page!=1)
                            TypeUtil.showToast("没有更多数据了");
                    }
                }
            }
        });
    }
    public void httpDetail() {
            HttpMap httpMap1 = new HttpMap();
            httpMap1.putDataMap("mid", Constant.mid);
            httpMap1.putDataMap("current_page", String.valueOf(current_page));
            httpMap1.putDataMap("per_page", "10");
            httpMap1.setHttpListener("/SystemMsgList", new HttpMap.HttpListener() {
                @Override
                public void onSuccess(String response, int status) {
                    if (status == 1) {
                        hasHttp=false;
                        MessageDetailModel messageDetailModel = new Gson().fromJson(response, MessageDetailModel.class);
                        total_page = messageDetailModel.page.total_page;
                        current_page++;
                        for (int i = 0; i < messageDetailModel.data.size(); i++) {
                            list.add(messageDetailModel.data.get(i));
                        }
                        if (messageDetailModel.page.current_page==1) {
                            merchDetailAdapter = new MMerchDetailAdapter(list, MessageDetailActivity.this);
                            mListView.setAdapter(merchDetailAdapter);
                        } else {
                            merchDetailAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }




}
