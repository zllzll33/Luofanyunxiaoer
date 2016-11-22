package com.luofangyun.activity;

import android.content.Intent;

import com.luofangyun.R;
import com.luofangyun.base.ZBaseActivity;
import com.luofangyun.base.ZBaseJSBridgeFragment;

/**
 * Created by win7 on 2016/11/11.
 */
public class WebActivity extends ZBaseActivity{
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void Init() {
        super.Init();
        Intent intent=getIntent();
        String webUrl=intent.getStringExtra("Url");
        ZBaseJSBridgeFragment zBaseJSBridgeFragment=new ZBaseJSBridgeFragment();
        zBaseJSBridgeFragment.setWebUrl(webUrl);
        getZFragmentManager().replace(R.id.fram, zBaseJSBridgeFragment);
    }
}
