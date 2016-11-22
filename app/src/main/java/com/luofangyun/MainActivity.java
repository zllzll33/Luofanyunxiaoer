package com.luofangyun;

import com.igexin.sdk.PushManager;
import com.luofangyun.base.ZBaseActivity;
import com.luofangyun.base.ZBaseJSBridgeFragment;
import com.luofangyun.util.TypeUtil;

public class MainActivity extends ZBaseActivity {
    private long mExitTime = 0;
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void Init() {
        super.Init();
        PushManager.getInstance().initialize(this.getApplicationContext());
        ZBaseJSBridgeFragment zBaseJSBridgeFragment=new ZBaseJSBridgeFragment();
        zBaseJSBridgeFragment.setWebUrl("http://a.luofangyun.com/Public/login");
//        zBaseJSBridgeFragment.setWebUrl("file:///android_asset/test.html");
//        zBaseJSBridgeFragment.setWebUrl("file:///android_asset/Index_index.html");
        getZFragmentManager().replace(R.id.fram, zBaseJSBridgeFragment);
    }
    @Override
    public void onBackPressed() {
        exitApp();
    }
    private void exitApp() {

        if (System.currentTimeMillis() - mExitTime > 2000) {
            TypeUtil.showToast("再按一次退出!");
        } else {
            System.exit(0);
        }
        mExitTime = System.currentTimeMillis();
    }

}
