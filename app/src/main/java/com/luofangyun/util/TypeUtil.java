package com.luofangyun.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.luofangyun.config.App;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by win7 on 2016/5/18.
 */
public class TypeUtil {
    final static int BUFFER_SIZE = 4096;
    public static void showToast(String str)
    {
        Toast.makeText(App.getInstance(),str,Toast.LENGTH_SHORT).show();
    }

    public static int  dp2px(int dp)
    {
        float  scale= App.getInstance().getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
    public static int px2dp(int px)
    {
        float  scale= App.getInstance().getResources().getDisplayMetrics().density;
        return (int)(px / scale + 0.5f);
    }
    public static String Map2JsonStr(Map map){
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            sb.append("\"").append(entry.getKey().toString()).append("\"").append( ":" ).append("\"").append(null==entry.getValue()?"":
                    entry.getValue().toString()).append("\"").append (iterator.hasNext() ? "," : "");
        }
        sb.append("}");
        return sb.toString();
    }

    public static Map String2Map(String mapString){
        Map map = new HashMap();
        StringTokenizer items;
        for(StringTokenizer entrys = new StringTokenizer(mapString, ":"); entrys.hasMoreTokens();
            map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), ",");
        return map;
    }
    public static  String telRegex = "[1][358]\\d{9}";
public static boolean isRightMobile(String mobile)
{
   return    mobile.matches(telRegex);
}
    public static void setImageView(ImageView imageView, String url)
    {
        setImageView(url, imageView, null, null, null);
    }
    private static void setImageView(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener,
                                     ImageLoadingProgressListener progressListener)
    {
        if (!canLoadImageFromUrl(uri))
        {
            return;
        }
        try
        {
            ImageLoader.getInstance().displayImage(uri.trim(), imageView, options, listener, progressListener);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private static boolean canLoadImageFromUrl(String url)
    {
        boolean mCanLoadImageFromUrl = true;
        if (mCanLoadImageFromUrl)// 可以从url加载图片
        {
            return true;
        } else
        {
            File cache = ImageLoader.getInstance().getDiskCache().get(url);
            if (cache != null && cache.exists() && cache.length() > 0)
            {
                return true;
            } else
            {
                return false;
            }
        }
    }
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
    public static int mNotificationId=5555;
    public static void sendNotification(int appNameRes,int logoRes,int small_logo,String title ,String content, Class<?> goClass,boolean isCover)
    {
        Context Rcontext=App.getInstance();
        NotificationManager mNotificationManager = (NotificationManager) Rcontext.getSystemService(Rcontext.NOTIFICATION_SERVICE);
        Notification mNotification = new Notification();
        Bitmap icon = BitmapFactory.decodeResource(Rcontext.getResources(), logoRes);
        NotificationCompat.Builder m_builder = new NotificationCompat.Builder(Rcontext);
        m_builder.setAutoCancel(true);
        m_builder.setLargeIcon(icon);
//		  m_builder.setContentInfo("?");
        m_builder.setSmallIcon(small_logo);
        m_builder.setWhen(System.currentTimeMillis());
        m_builder.setContentTitle(title);
        m_builder.setContentText(content);
        m_builder.setTicker(title);
        m_builder.setDefaults(Notification.DEFAULT_SOUND);
        if(!isCover)
            mNotificationId++;
        Intent completingIntent = new Intent(Rcontext,goClass);
        PendingIntent mPendingIntent = PendingIntent.getActivity(Rcontext,appNameRes, completingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.contentIntent = mPendingIntent;
        m_builder.setContentIntent(mPendingIntent);
        mNotificationManager.notify(mNotificationId, m_builder.build());
    }
    public static void LogE(String tag,String msg)
    {
        Log.e(tag,msg);
    }

}


