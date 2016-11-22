package com.luofangyun.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.luofangyun.R;
import com.luofangyun.base.ZBaseActivity;
/**
 * Created by win7 on 2016/4/28.
 */
public abstract class BaseActivity extends ZBaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    protected View content;
    protected int layoutId() {
        return 0;
    }
    protected void Init() {
        super.Init();
    }
    public void initUI() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        title = (TextView) findViewById(R.id.title);
    }
    protected void setTitle(String titleText) {
        initUI();
        title.setText(titleText);
    }
    protected void finishActivity() {
        finish();
    }
    protected void setTitle(String titleText, boolean isHideBack) {
        initUI();
        title.setText(titleText);
        if (isHideBack)
            back.setVisibility(View.GONE);
    }
    protected void hideBack() {
        back.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
    }
}
