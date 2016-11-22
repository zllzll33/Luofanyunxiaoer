package com.luofangyun.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luofangyun.R;
import com.luofangyun.config.Constant;
import com.luofangyun.config.HttpMap;
import com.luofangyun.model.MessageModel;

import butterknife.OnClick;
/**
 * Created by win7 on 2016/11/16.
 */
public class MessageActivity extends BaseActivity {
    @butterknife.InjectView(R.id.interactive_icon)
    ImageView interactiveIcon;
    @butterknife.InjectView(R.id.interactive_dot)
    TextView interactiveDot;
    @butterknife.InjectView(R.id.interactive_title)
    TextView interactiveTitle;
    @butterknife.InjectView(R.id.interactive_content)
    TextView interactiveContent;
    @butterknife.InjectView(R.id.interactive_go)
    ImageView interactiveGo;
    @butterknife.InjectView(R.id.rl_interactive)
    RelativeLayout rlInteractive;
    @butterknife.InjectView(R.id.merch_icon)
    ImageView merchIcon;
    @butterknife.InjectView(R.id.merch_dot)
    TextView merchDot;
    @butterknife.InjectView(R.id.merch_title)
    TextView merchTitle;
    @butterknife.InjectView(R.id.merch_content)
    TextView merchContent;
    @butterknife.InjectView(R.id.merch_go)
    ImageView merchGo;
    @butterknife.InjectView(R.id.rl_merch)
    RelativeLayout rlMerch;
    @Override
    protected int layoutId() {
        return R.layout.act_message;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("消息");
        httpMessage();
    }
    @OnClick(R.id.rl_interactive)
    void interactive(View view) {
        Intent intent1 =new Intent(MessageActivity.this,MessageDetailActivity.class);
        startActivity(intent1);
        interactiveDot.setVisibility(View.GONE);
    }
    @OnClick(R.id.rl_merch)
    void merch(View view) {
        Intent intent2 =new Intent(MessageActivity.this,OrderDetailActivity.class);
        startActivity(intent2);
        merchDot.setVisibility(View.GONE);
    }
    public void httpMessage() {
        HttpMap httpMap1 = new HttpMap();
        httpMap1.putDataMap("mid", Constant.mid);
        httpMap1.putDataMap("current_page", "0");
        httpMap1.putDataMap("per_page", "10");
        httpMap1.setHttpListener("/messageList", new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, int status) {
                if (status == 1) {
                    Gson gson = new Gson();
                    MessageModel messageModel = gson.fromJson(response, MessageModel.class);
                    if (messageModel.data.msg != null) {
                        rlInteractive.setVisibility(View.VISIBLE);
                        interactiveTitle.setText(messageModel.data.msg.title);
                        interactiveContent.setText(messageModel.data.msg.content);
                        if (messageModel.data.msg.status != 0) {
                            interactiveDot.setVisibility(View.VISIBLE);
                            interactiveDot.setText(String.valueOf(messageModel.data.msg.status));
                        } else
                            interactiveDot.setVisibility(View.GONE);
                    }
                    if (messageModel.data.order != null) {
                        rlMerch.setVisibility(View.VISIBLE);
                        merchTitle.setText(messageModel.data.order.title);
                        merchContent.setText(messageModel.data.order.content);
                        if (messageModel.data.order.status != 0) {
                            merchDot.setVisibility(View.VISIBLE);
                            merchDot.setText(String.valueOf(messageModel.data.order.status));
                        } else
                            merchDot.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
