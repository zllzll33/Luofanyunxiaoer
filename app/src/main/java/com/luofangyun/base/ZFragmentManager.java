package com.luofangyun.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/4/29.
 */
public class ZFragmentManager {
 private FragmentManager mFragmentManager;
    private FragmentTransaction transaction;
    private List<ShowTag> showTagList;
    public ZFragmentManager(FragmentManager mFragmentManager)
    {
        this.mFragmentManager=mFragmentManager;
        showTagList=new ArrayList<ShowTag>();
    }
    public void replace(int id,Fragment mFragment)
    {
        transaction =mFragmentManager.beginTransaction();
        transaction.replace(id,mFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void rtoggle(int id,Fragment mFragment)
    {
        if(mFragment.isAdded())
          remove(mFragment);
        replace(id,mFragment);
    }
    public void atoggle(int id,Fragment mFragment)
    {
        Fragment showFragment=mFragmentManager.findFragmentById(id);
        if(showFragment!=null)
            hide(showFragment);
        if(mFragment.isAdded())
        {
            show(mFragment);
        }
        else{
            add(id,mFragment);
        }

    }
    public void atoggle(int id, Fragment mFragment, String tag)
    {
//        Fragment   showFragment=mFragmentManager.findFragmentById(id);
        String hastag=null;
        for(ShowTag showTag:showTagList)
        {
            if(showTag.id==id)
            {
                hastag=showTag.showFrag;
            }
        }
       if(hastag!=null)
       {
           Fragment showFragment=mFragmentManager.findFragmentByTag(hastag);
           hide(showFragment);
       }
     /*   if(showFragment!=null)
            hide(showFragment);*/
        if(mFragment.isAdded())
        {
            show(mFragment);
            for(ShowTag showTag:showTagList)
            {
                if(showTag.id==id)
                {
                    showTag.showFrag= tag;
                }
            }
        }
        else{
            add(id,mFragment,tag);
            ShowTag showTag=new ShowTag(id,mFragment.getClass().getSimpleName());
            showTagList.add(showTag);
        }


    }
    public void stoggle(int id,Class<? extends Fragment> showFragmentClass)
    {
        final String tag = showFragmentClass.getSimpleName();
        Fragment mFragment = mFragmentManager.findFragmentByTag(tag);
        if(mFragment==null)
        {
           try {
               mFragment= showFragmentClass.newInstance();
               atoggle(id, mFragment,tag);
           }catch (InstantiationException e)
           {}
            catch (IllegalAccessException e)
            {}
        }
        else {
            atoggle(id, mFragment,tag);
        }
    }
    public void stoggle(int id,Fragment showFragment)
    {
        final String tag = showFragment.getClass().getSimpleName();
        Fragment mFragment = mFragmentManager.findFragmentByTag(tag);
        if(mFragment==null)
        {
            mFragment= showFragment;
            atoggle(id, mFragment,tag);
        }
        else {
            atoggle(id, mFragment,tag);
        }
    }
    public void remove(Fragment fragment)
    {
        transaction =mFragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
    public void add(int id,Fragment fragment)
    {
        transaction =mFragmentManager.beginTransaction();
        transaction.add(id,fragment);
        transaction.commit();
    }
    public void add(int id, Fragment fragment, String tag)
    {
        transaction =mFragmentManager.beginTransaction();
        transaction.add(id,fragment,tag);
        transaction.commit();
    }
    public void hide(Fragment fragment)
    {
        transaction =mFragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }
    public void show(Fragment fragment)
    {
        transaction =mFragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }
public class ShowTag{
    public ShowTag(int id,String showFrag)
    {
        this.id=id;
        this.showFrag=showFrag;
    }
    public int id;
    public String showFrag;
}
}
