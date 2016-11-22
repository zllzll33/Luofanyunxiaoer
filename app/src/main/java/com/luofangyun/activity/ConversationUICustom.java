package com.luofangyun.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;
import com.luofangyun.R;

/**
 * Created by win7 on 2016/11/11.
 */

public class ConversationUICustom extends IMConversationListUI {
    public ConversationUICustom(Pointcut pointcut) {
        super(pointcut);
    }
    @Override
    public View getCustomConversationListTitle(Fragment fragment,final Context context, LayoutInflater inflater) {
        View view= inflater.inflate(R.layout.title, new RelativeLayout(context), false);
        TextView title=(TextView)view.findViewById(R.id.title);
        title.setText("云小二");
        ImageView back=(ImageView)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity=	(Activity)context;
                activity.finish();
            }
        });
        return view;
    }
    public boolean needHideTitleView(Fragment fragment) {
        return false;
    }
}
