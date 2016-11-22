package com.luofangyun.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.mobileim.appmonitor.ResourceUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
/**
 * Created by win7 on 2016/5/25.
 */
public abstract class ZBaseActivity extends FragmentActivity {
    private ZFragmentManager mFragmentManager;
    protected Handler zHandler;
    private ZHandlerLisenter zHandlerLisenter;
    private int sysBarColor=0xededed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeView();
        setContentView(layoutId());
        ButterKnife.inject(this);
        initHandler();
        Init();
    }
    protected void initBeforeView()
    {
//        InitSysBar();
    }
    protected void InitSysBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(0xededed);
        tintManager.setNavigationBarTintColor(0x000000);
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @Override
    protected void onResume() {
        ZActivityManager.getInstance().addActivity(this);
        super.onResume();
    }
    protected   abstract    int layoutId();
    protected  void Init()
    {
//        singleTop
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
    private void initHandler()
    {
        zHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        break;
                    default:break;
                }
                if(zHandlerLisenter!=null)
                    zHandlerLisenter.Message(msg,msg.what);
                super.handleMessage(msg);
            }
        };
    }
    public ZFragmentManager getZFragmentManager()
    {
        if (mFragmentManager == null)
        {
            mFragmentManager = new ZFragmentManager(getSupportFragmentManager());
        }
        return mFragmentManager;
    }
    @Override
    protected void onDestroy() {
        ZActivityManager.getInstance().removeActivity(this);
//        ButterKnife.unbind(this);
        ButterKnife.reset(this);
        super.onDestroy();
    }
public void setZHandlerLisener(ZHandlerLisenter zHandlerLisener)
{
    this.zHandlerLisenter=zHandlerLisener;
}
    interface ZHandlerLisenter{
        public void Message(Message msg, int what);
    }
}
