package com.luofangyun.base;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.luofangyun.R;


/**
 * Created by win7 on 2016/6/22.
 */
public class ZWebJSBridgeFragment extends ZBaseFragment {
    BridgeWebView wv;
    private FragmentActivity activity;
    private WebActionListener   webActionListener=null;
    public static String webUrl="http://h.luofangyun.com/Index/index/opentype/app/jid/4.html";
    @Override
    protected int layoutId() {
        return R.layout.frag_webjsbridge;
    }
    @Override
    protected void Init() {
        super.Init();
        wv = (BridgeWebView)view.findViewById(R.id.jsbridge);
        initUrl(webUrl);
        initWebView(wv);
        wv.loadUrl(webUrl);
        activity=getActivity();
        setListener();
        ActWebview();
    }
    protected void ActWebview()
    {

    }
    public BridgeWebView getWebview()
    {
        return wv;
    }
    protected void initWebView(BridgeWebView wv)
    {
        wv.setBackgroundColor(0);
//        wv.getSettings().setCacheMode(wv.getSettings().LOAD_NO_CACHE);
        wv.getSettings().setCacheMode(wv.getSettings().LOAD_NO_CACHE);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
        wv.getSettings().setBlockNetworkImage(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setBlockNetworkImage(false);
        // 当连接点击后重新载入到当前窗口，防止跳到
        WebSettings webSettings = wv.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setJavaScriptEnabled(true);// 可用JS
        webSettings.setAllowFileAccess(true); // 设置可访问文件
//        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//网页缓存
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);// 缓存
        webSettings.setBlockNetworkImage(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);

        webSettings.setDomStorageEnabled(true);
        //DomStorage
     /*   String appCachePath = App.getInstance().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);*/


    }
    public void setWebUrl(String webUrl)
    {
        this.webUrl=webUrl;
    }
    protected void initUrl(String webUrl)
    {
        this.webUrl=webUrl;
    }
    public void setListener() {
        wv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {  //表示按返回键时的操作
                        wv.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    /*    wv.setWebViewClient(new WebViewClient() {
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

        });*/
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)// 设置 加载进程
            {
//                Log.e("h5", "progress="+ String.valueOf(progress));
                super.onProgressChanged( view,  progress);
            }
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                Log.e("5", "TITLE=" + title);

            }
        });

    }
    @Override
    public void onDestroy()
    {
        getActivity().finish();
        super.onDestroy();
    }
    public void commonAction(String url)
    {
        wv.loadUrl(url);// 二级网页，及二级网页以下右webview载入网页
    }
    public void putString(String key, String value)
    {
        getArguments().putString(key, value);
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
    public void setWebActionListener(WebActionListener webActionListener)
    {
        this.webActionListener=webActionListener;
    }

    public interface WebActionListener
    {
        public void webAction(WebView webview, String url);
    }
    class AoidFlashThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.finish();
        }
    }
}
