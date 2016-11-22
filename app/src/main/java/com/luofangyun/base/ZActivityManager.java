package com.luofangyun.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by win7 on 2016/5/25.
 */
public class ZActivityManager {
    private static Stack<Activity> mStackActivity;
    private static ZActivityManager zActivityManager;
    public ZActivityManager()
    {
        mStackActivity = new Stack<Activity>();
    }
    public static ZActivityManager getInstance()
    {
        if(zActivityManager==null)
            zActivityManager=new ZActivityManager();
        return zActivityManager;
    }
    public Activity getLastActivity()
    {
        Activity activity = mStackActivity.lastElement();
        return activity;
    }
    public void addActivity(Activity activity)
    {
        if (!mStackActivity.contains(activity))
        {
            mStackActivity.add(activity);
        }
    }
    public void removeActivity(Activity activity)
    {
        if (activity != null)
        {
            mStackActivity.remove(activity);
        }
    }
    public void finishLastActivity()
    {
        Activity activity = getLastActivity();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity)
    {
        if (activity != null)
        {
            mStackActivity.remove(activity);
            activity.finish();
        }
    }
    public void finishAllActivity()
    {
        for (Activity activity : mStackActivity)
        {
            if (activity != null)
            {
                activity.finish();
            }
        }
        mStackActivity.clear();
    }
}
