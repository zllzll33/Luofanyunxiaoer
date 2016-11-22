package com.luofangyun.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luofangyun.R;


/**
 * Created by win7 on 2016/4/29.
 */
public class ZWebviewFragment extends ZBaseFragment {
    protected WebView wv;
    private FragmentActivity activity;
    private WebActionListener   webActionListener=null;
    protected String webUrl="http://h.luofangyun.com/Index/index/opentype/app/jid/4.html";
    @Override
    protected int layoutId() {
        return R.layout.zwebfragment;
    }
    @Override
    protected void Init() {
     /*   if(getString("webUrl")!=null)
            webUrl=getString("webUrl");*/
        wv = (WebView)view.findViewById(R.id.wv);
        initUrl(webUrl);
        initWebView(wv);
        wv.loadUrl(webUrl);
        activity=getActivity();
        setListener();
        super.Init();
    }
protected void initUrl(String webUrl)
{
  this.webUrl=webUrl;
}
    public ZWebviewFragment()
    {
        if (getArguments() == null)
        {
            super.setArguments(new Bundle());
        }
    }
  public WebView getWebview()
  {
      return wv;
  }

    protected void initWebView(WebView wv)
    {
        wv.setBackgroundColor(0);
        wv.getSettings().setJavaScriptEnabled(true);// 可用JS
        wv.getSettings().setAllowFileAccess(true); // 设置可访问文件
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
        wv.getSettings().setCacheMode(wv.getSettings().LOAD_NO_CACHE);
//        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//网页缓存
        wv.getSettings().setBlockNetworkImage(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setBlockNetworkImage(false);
//         当连接点击后重新载入到当前窗口，防止跳到
        WebSettings webSettings = wv.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setJavaScriptEnabled(true);// 可用JS
        webSettings.setAllowFileAccess(true); // 设置可访问文件

//        webSettings.setCacheMode(wv.getSettings().LOAD_DEFAULT);// 缓存
        webSettings.setBlockNetworkImage(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);

    }
    @SuppressLint("JavascriptInterface")
    public void addJS(Object object, String name)
    {
        wv.addJavascriptInterface(object,name);
    }
    public void setListener()
    {
        wv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {  //表示按返回键时的操作
                        wv.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                    else if(keyCode == KeyEvent.KEYCODE_BACK )
                    {
                        getActivity().finish();
                    }
                }
                return false;
            }
        });
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {

                // 调用拨号程序
                if (url.startsWith("mailto:") || url.startsWith("geo:")
                        || url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(url));
                    startActivity(intent);
                    wv.goBack();
                    return true;
                }
                {
                    if (webActionListener == null) {
                        commonAction(url);
                        return false;
                    } else {
                        webActionListener.webAction(view, url);
                        return true;
                    }


                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }

        });
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)// 设置 加载进程
            {
//                Log.e("h5", "progress="+ String.valueOf(progress));
                 super.onProgressChanged( view,  progress);
            }
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("5", "TITLE=" + title);

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        wv.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wv.onPause();
    }
    public void commonAction(String url)
    {
        wv.loadUrl(url);// 二级网页，及二级网页以下右webview载入网页
    }
    public void putString(String key, String value)
    {
        getArguments().putString(key, value);
    }
 protected void webClientListen()
 {

 }
    public String getString(String key)
    {
        return getArguments().getString(key);
    }
  public void clearWebcache(Context context)
  {

      context.deleteDatabase("webview.db");
      context.deleteDatabase("webviewCache.db");
  }
public class JS{}
    @JavascriptInterface
    public void addjs()
    {

    }
    public void setWebActionListener(WebActionListener webActionListener)
    {
        this.webActionListener=webActionListener;
    }

public interface WebActionListener
{
    public void webAction(WebView webview, String url);
}
    //防止finish闪烁
    class AoidFlashThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            activity.finish();
        }
    }
}
