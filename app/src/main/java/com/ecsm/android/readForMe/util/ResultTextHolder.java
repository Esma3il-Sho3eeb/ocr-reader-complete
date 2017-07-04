package com.ecsm.android.readForMe.util;


import android.app.Activity;
import android.support.annotation.NonNull;

import com.ecsm.android.readForMe.listener.OcrResultListener;

public class ResultTextHolder {
    private String mText = "";
    private OcrResultListener mOcrResultListener;
    public ResultTextHolder( @NonNull OcrResultListener ocrResultListener) {
        mText = "";
        mOcrResultListener = ocrResultListener;
    }

    public String getText() {

        return mText;
    }

    public void onResult(final String newResult) {
        mOcrResultListener.onResult(newResult);
    }

    public void setText(String text) {
        mText = text;
    }

    public void clear() {
        mText = "";
    }

    public void append(String text) {
        mText += text;
    }
}
