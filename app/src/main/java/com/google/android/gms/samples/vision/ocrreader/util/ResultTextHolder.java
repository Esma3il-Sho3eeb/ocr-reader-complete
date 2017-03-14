package com.google.android.gms.samples.vision.ocrreader.util;


public class ResultTextHolder {
    String mText = "";

    public ResultTextHolder() {
        mText = "";
    }

    public String getText() {

        return mText;
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
