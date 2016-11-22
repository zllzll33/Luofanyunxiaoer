package com.luofangyun.config;

import android.app.Application;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.wxlib.util.SysUtil;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.luofangyun.activity.ChattingUICustom;
import com.luofangyun.activity.ConversationUICustom;
import com.luofangyun.activity.NotificationCustomUI;

/**
 * Created by win7 on 2016/11/10.
 */
public class App extends Application{
    private static App app;
    private static RequestQueue mQueue=null;
    public static  App getInstance()
    {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initYW_IM();
    }
    private void initYW_IM()
    {
        SysUtil.setApplication(this);
        if(SysUtil.isTCMSServiceProcess(this)){
            return;
        }
        if(SysUtil.isMainProcess()){
            YWAPI.init(this, Constant.openIMKey);
        }
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, ConversationUICustom.class);
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChattingUICustom.class);
        AdviceBinder.bindAdvice(PointCutEnum.NOTIFICATION_POINTCUT, NotificationCustomUI.class);
    }
    public void HttpPost(StringRequestPost request)
    {
        if(mQueue==null)
            mQueue = Volley.newRequestQueue(App.getInstance());
        mQueue.add(request);
    }
}
