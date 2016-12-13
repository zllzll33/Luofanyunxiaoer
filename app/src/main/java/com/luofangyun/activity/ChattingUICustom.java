package com.luofangyun.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.conversation.YWConversation;
import com.luofangyun.R;
import com.luofangyun.config.Constant;
import com.luofangyun.config.HttpMap;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by win7 on 2016/6/23.
 */
public class ChattingUICustom extends IMChattingPageUI {
    private String username, url, userid, uid, rank;
    ImageView rankView;
    public ChattingUICustom(Pointcut pointcut) {
        super(pointcut);
    }
    TextView title;
    //自定义标题栏
    @Override
    public View getCustomTitleView(final Fragment fragment,
                                   final Context context, LayoutInflater inflater,
                                   final YWConversation conversation) {
//     Log.e("chat_id",conversation.getConversationId());
        String targetId = conversation.getConversationId();
        if (targetId.contains("user")) {
            uid = targetId.substring(targetId.indexOf("user") + 4);

        } else if (targetId.contains("sj"))
            uid = targetId.substring(targetId.indexOf("sj") + 2);
//        Log.e("uid", uid);
        // 单聊和群聊都会使用这个方法，所以这里需要做一下区分
        // 本demo示例是处理单聊，如果群聊界面也支持自定义，请去掉此判断
        Activity activity = (Activity) context;
        Intent intent = activity.getIntent();
        userid = intent.getStringExtra("userid");
        username = intent.getStringExtra("username");
        url = intent.getStringExtra("url");
        rank = intent.getStringExtra("rank");
        //TODO 重要：必须以该形式初始化view---［inflate(R.layout.**, new RelativeLayout(context),false)］------，以让inflater知道父布局的类型，否则布局**中的高度和宽度无效，均变为wrap_content
        View view = inflater.inflate(R.layout.chat_title, new RelativeLayout(context), false);
        ImageView back = (ImageView) view.findViewById(R.id.back);
        title = (TextView) view.findViewById(R.id.title);
        rankView = (ImageView) view.findViewById(R.id.rank);
        ImageView des = (ImageView) view.findViewById(R.id.des);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                activity.finish();
            }
        });
        des.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("Url", url);
                context.startActivity(intent);
            }
        });
        url = HttpMap.server_user + "/User/myaccount/mid/"+ Constant.mid+"/uid/" + uid;
//        Log.e("im_detail",url);
        if (TextUtils.isEmpty(userid)) {
            httpInfo();
        } else {
            setRank();
            title.setText(username);
        }
        return view;
    }

    public void setRank() {
        if (TextUtils.isEmpty(rank) || rank.equals("0")) {
            rankView.setVisibility(View.VISIBLE);
        }
        if (rank.equals("1"))
            rankView.setImageResource(R.mipmap.rank1);
        if (rank.equals("2"))
            rankView.setImageResource(R.mipmap.rank2);
        if (rank.equals("3"))
            rankView.setImageResource(R.mipmap.rank3);
    }
    public void httpInfo() {
        HttpMap httpMap = new HttpMap();
        httpMap.putDataMap("uid", uid);
        httpMap.setHttpListener("http://a.luofangyun.com/Api/linkUser", new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, int status) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    url=jsonObject.optString("mabbreurl");
                    rank=jsonObject.optString("mabbrerank");
                    username=jsonObject.optString("mlptel");
                    setRank();
                    title.setText(username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, true);
    }
    //聊天标题栏下 添加
        /* View view=  LayoutInflater.from(MainActivity.this).inflate(R.layout.title,null,false);
                                mIMKit.showCustomView(view);*/
    //是否隐藏标题栏
    @Override
    public boolean needHideTitleView(Fragment fragment, YWConversation conversation) {
        return false;
    }
}
