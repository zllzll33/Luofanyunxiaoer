package com.luofangyun.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luofangyun.R;
import com.luofangyun.config.Constant;
import com.luofangyun.config.HttpMap;
import com.luofangyun.util.TypeUtil;
import com.zxing.camera.CameraManager;
import com.zxing.camera.MipcaActivityCapture;
import com.zxing.view.ViewfinderView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/11/11.
 */
public class MipCaptureActitvity extends MipcaActivityCapture {
    boolean animFlag = false;
    @InjectView(R.id.preview_view)
    SurfaceView previewView;
    @InjectView(R.id.viewfinder_view)
    ViewfinderView viewfinderView;
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.edit1)
    EditText edit1;
    @InjectView(R.id.comfirm)
    TextView comfirm;
    @InjectView(R.id.ll_edit)
    LinearLayout llEdit;
    @InjectView(R.id.cover)
    View cover;
    @InjectView(R.id.surface_cover)
    View surfaceCover;

    @Override
    protected int layoutId(int resId) {
        return R.layout.act_mipcapture;
    }
    @Override
    protected void init() {
        ButterKnife.inject(this);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        edit1.setKeyListener(null);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewfinderView.setVisibility(View.GONE);
                if (!animFlag) {
                    animFlag = true;
                    surfaceCover.setVisibility(View.VISIBLE);
//                    CameraManager.get().closeDriver();
//                    CameraManager.get().stopPreview();
//                    clearDraw();
                    edit_anim();
                }
            }
        });
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit1.getText().toString())) {
                    TypeUtil.showToast("请输入核销码");
                    return;
                }
                httpCode(edit1.getText().toString());
            }
        });
    }

    public void clearDraw() {
        Canvas canvas = null;
        try {
            canvas = previewView.getHolder().lockCanvas(null);
            canvas.drawColor(Color.BLACK);
        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                previewView.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    public void edit_anim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, -800);
        animator.setTarget(llEdit);
        animator.setDuration(500).start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cover.setVisibility(View.GONE);
                edit1.setFocusable(true);
                edit1.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) edit1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(edit1, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                llEdit.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    @Override
    public void dealResult(String result) {
        String reg = "^[0-9]*$";
//        Log.e("scan_result",result);
        if (result.matches(reg)) {
            httpCode(result);
        }
    }

    public void httpCode(String code) {

        HttpMap httpMap1 = new HttpMap();
        httpMap1.putDataMap("mid", Constant.mid);
        httpMap1.putDataMap("code", code);
        httpMap1.setHttpListener("/ScanConfirm", new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, int status) {
//                  response="{\"status\":\"1\",\"info\":\"验证成功\",\"data\":{\"url\":\"http://www.baidu.com\"}}";
                if (status == 1)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject data = jsonObject.optJSONObject("data");
                        ScanResultActivity.url = data.optString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(MipCaptureActitvity.this, ScanResultActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}
