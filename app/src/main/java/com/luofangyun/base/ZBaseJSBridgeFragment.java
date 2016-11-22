package com.luofangyun.base;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.IYWP2PPushListener;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.login.IYWConnectionListener;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.luofangyun.activity.MessageActivity;
import com.luofangyun.activity.MipCaptureActitvity;
import com.luofangyun.config.Constant;
import com.luofangyun.config.HttpMap;
import com.luofangyun.getui.GPushReceiver;
import com.luofangyun.util.PreferenceUtil;
import com.luofangyun.util.TypeUtil;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/6/22.
 */
public class ZBaseJSBridgeFragment extends ZWebJSBridgeFragment {
    DefaultHandler defaultHandler;
    YWIMKit mIMKit;
    int unReadNum;
   public  boolean imStatus=false;
    String utype;
    @Override
    protected void initWebView(final BridgeWebView wv) {
        super.initWebView(wv);
        defaultHandler=new DefaultHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    Log.e("web_data",data);
                  JSONObject jsObject=new JSONObject(data);
                    String type=jsObject.optString("type");
                      if(type.equals("loginOut"))
                    {
                        PreferenceUtil.putBoolean("loginStatus",false);
                    }
                    else if(type.equals("scan"))
                    {
                        Intent intent=new Intent(getActivity(), MipCaptureActitvity.class);
                        startActivity(intent);
                    }
                    else if(type.equals("merchant"))
                    {
                        Constant.mid=jsObject.optString("data");
                        utype=jsObject.optString("utype");
                        ImLoginCheck();
                        httpCid();
                    }
                    else if(type.equals("WCA_Message"))
                    {
                        Intent intent=new Intent(getActivity(), MessageActivity.class);
                        startActivity(intent);
                    }
                    else if(type.equals("WCA_Version"))
                    {
                        function.onCallBack("2");
                    }
                    else if(type.equals("WCA_Mid"))
                    {
                        function.onCallBack(Constant.mid);
                    }
                    else if(type.equals("WCA_CloseWin"))
                    {
                        if(wv.canGoBack())
                            wv.goBack();
                        else
                          getActivity().finish();
                    }
                  else  if(type.equals("user"))
                    {
                        ImLoginCheck();
                        if(imStatus) {
                            String im_id = jsObject.optString("im_id");
                            String name = jsObject.optString("name");
                            String url = jsObject.optString("url");
                            String rank = jsObject.optString("rank");
                            Intent intent = mIMKit.getChattingActivityIntent(im_id, Constant.openIMKey);
                            intent.putExtra("userid",im_id);
                            intent.putExtra("username",name);
                            intent.putExtra("url",url);
                            intent.putExtra("rank",rank);
                            startActivity(intent);
                        }
                    }
                    else if(type.equals("baichuanchat"))
                    {
                        ImLoginCheck();
                        {
                            Intent intent = mIMKit
                                    .getConversationActivityIntent();
                            startActivity(intent);
                        }
                    }
                 else  if(type.equals("WCA_Login"))
                    {


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                function.onCallBack("hello world");
            }
        };
        wv.setDefaultHandler(defaultHandler);
        wv.registerHandler(null,defaultHandler);
      /*  wv.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("jsbridge2",data);
                function.onCallBack(" nice");
            }
        });
        */
    }
    public void httpCid()
    {
        if(!GPushReceiver.isAdd) {
            HttpMap httpMap1 = new HttpMap();
            httpMap1.putDataMap("mid", Constant.mid);
            httpMap1.putDataMap("clientid", PreferenceUtil.getString("cid", ""));
            httpMap1.putDataMap("type", "2");
            httpMap1.setHttpListener("/MerchantClientid", new HttpMap.HttpListener() {

                @Override
                public void onSuccess(String response, int status) {
                if(status==1)
                {
                    GPushReceiver.isAdd=true;
                }

                }
            });
        }
    }
    @Override
    public void onResume()
    {
        if(mIMKit!=null)
        {
//            Log.e("纬度消息数",String.valueOf(mIMKit.getConversationService().getAllUnreadCount()));
            if(unReadNum!=mIMKit.getConversationService().getAllUnreadCount())
            {
                UnreadNumChange();
            }
        }

        super.onResume();
    }
    public void ImLoginCheck()
    {
        if(!imStatus)
        {
            String userid=  PreferenceUtil.getString("im_id","");
            String password= PreferenceUtil.getString("im_pw","");
            if(!TextUtils.isEmpty(userid))
                IMLogin(userid,password);
            else
                httpPW(Constant.mid,utype);
        }
    }
  public void httpPW(String mid,String type)
  {
      String userkey = TypeUtil.md5(Constant.openIMKey
              +mid + type + "luofangyun");
      HttpMap httpMap=new HttpMap();
      httpMap.putDataMap("userid",mid);
      httpMap.putDataMap("type",type);
      httpMap.putDataMap("userkey",userkey);
      httpMap.putDataMap("mobile_user","1");
      httpMap.setHttpListener("http://h.luofangyun.com/Api/baichuanUser", new HttpMap.HttpListener() {
          @Override
          public void onSuccess(String response, int status) {
              try {
                  JSONObject jsonObject=new JSONObject(response);
                  String userid=jsonObject.optString("userid");
                  String password=jsonObject.optString("password");
                  PreferenceUtil.putString("im_id",userid);
                  PreferenceUtil.putString("im_pw",password);
                  IMLogin(userid,password);
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      },true);
  }
    public void IMLogin(String userid,String password)
    {
        mIMKit = YWAPI.getIMKitInstance(userid, Constant.openIMKey);
        IYWConversationService conversationService = mIMKit.getConversationService();
        conversationService.removeP2PPushListener(mP2PListener);
        conversationService.addP2PPushListener(mP2PListener);
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
        loginService.login(loginParam, new IWxCallback() {
            @Override
            public void onSuccess(Object... arg0) {
                imStatus=true;
                Log.e("baichuan","登录成功");
                mIMKit.getIMCore().addConnectionListener(new IYWConnectionListener() {
                    @Override
                    public void onDisconnect(int i, String s) {
                        //掉线
                        imStatus=false;
                        Log.e("im","掉线");
//                        TypeUtil.showToast("掉线");
                    }

                    @Override
                    public void onReConnecting() {
                        //正在重登
//                        TypeUtil.showToast("正在重登");
                        Log.e("im","正在重登");
                    }
                    @Override
                    public void onReConnected() {
                        //重登成功
//                        TypeUtil.showToast("重登成功");
                        Log.e("im","重登成功");
                    }
                });
            }
            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int errCode, String description) {

            }
        });
    }
    private IYWP2PPushListener mP2PListener = new IYWP2PPushListener() {
        @Override
        public void onPushMessage(IYWContact contact, YWMessage message) {
            String user=message.getAuthorUserName();
        }
    };
    private boolean isInstallByread(String packageName)
    {
        return new File("/data/data/" + packageName).exists();
    }
    public  void UnreadNumChange()
    {
        unReadNum=mIMKit.getConversationService().getAllUnreadCount();
        String  data="{\"type\":\"ACW_Messge\",\"num\":"+String.valueOf(unReadNum)+"}";
        wv.send(data, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

}
