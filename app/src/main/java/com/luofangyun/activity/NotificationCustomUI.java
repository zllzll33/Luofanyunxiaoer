package com.luofangyun.activity;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMNotification;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.utility.IMSmilyCache;
import com.luofangyun.R;
import com.luofangyun.base.ZBaseJSBridgeFragment;
import com.luofangyun.config.App;
import com.luofangyun.config.Constant;

/**
 * Created by win7 on 2016/11/15.
 */
public class NotificationCustomUI extends IMNotification {
    public NotificationCustomUI(Pointcut pointcut) {
        super(pointcut);
    }
    public String getAppName() {
        return   App.getInstance().getResources().getString(R.string.app_name);
    }
public String getTicker(YWConversation conversation, YWMessage message, int totalUnReadCount) {
    String content;
    if(message.getContent().toString().startsWith("/:"))
        content="[表情]";
    else
    content=message.getContent();
    return message.getAuthorUserName()+" :  "+content;
}
}
