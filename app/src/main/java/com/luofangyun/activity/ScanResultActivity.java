package com.luofangyun.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luofangyun.R;

import butterknife.InjectView;
public class ScanResultActivity extends BaseActivity {
    public static String url = "";
    @InjectView(R.id.scan_ok)
    ImageView scanOk;
    @InjectView(R.id.back_return)
    TextView backReturn;
    @InjectView(R.id.go_order)
    TextView goOrder;
    @Override
    protected int layoutId() {
        return R.layout.act_scan_result;
    }



    @Override
    protected void Init() {
        super.Init();
        setTitle("扫一扫");
        backReturn=(TextView)findViewById(R.id.back_return);
        backReturn.setOnClickListener(this);
        goOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_return:
                finish();
                break;
            case R.id.go_order:
                Intent intent = new Intent(ScanResultActivity.this, WebActivity.class);
                intent.putExtra("Url", url);
//               intent.putExtra("Url",url);
                startActivity(intent);
                break;
        }
    }

}
