package com.luofangyun.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by win7 on 2016/5/3.
 */
public abstract class ZBaseFragment extends Fragment {
    protected View view;
    private ZFragmentManager mFragmentManager;
     public static  Handler zHander;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(layoutId(), container, false) ;
        ButterKnife.inject(this,view);
        Init();
        zHander =new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                handerReceive(msg.what);
                super.handleMessage(msg);
            }
        };
        return view;
    }
     protected abstract int layoutId();
    protected void Init()
    {

    }
    protected void handerReceive(int index)
    {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    public ZFragmentManager getZFragmentManager()
    {

        if (mFragmentManager == null)
        {
            mFragmentManager = new ZFragmentManager(getChildFragmentManager());
        }
        return mFragmentManager;
    }

}
