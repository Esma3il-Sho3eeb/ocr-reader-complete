package com.ecsm.android.readForMe.util;

import android.app.Activity;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.ecsm.android.readForMe.listener.SoundListener;


// Each utterance is associated with a call to speak(CharSequence, int, Bundle, String) or synthesizeToFile(CharSequence, Bundle, File, String)
public class SoundUtteranceListener extends UtteranceProgressListener {
    Activity context;
    private SoundListener mSoundListener;

    public SoundUtteranceListener(Activity ctx, SoundListener xCom) {
        context = ctx;
        mSoundListener = xCom;
    }

    @Override
    public void onStart(String utteranceId) {
        // TODO Auto-generated method stub
        Log.e("HelloTextToSpeech", "Text To Speech Started ->" + utteranceId);
        //LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(START_PLAYING_BROADCAST));
    }

    @Override
    public void onDone(String utteranceId) {
        // TODO Auto-generated method stub
        Log.e("HelloTextToSpeech", "Text To Speech Done -> " + utteranceId);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSoundListener.onSoundComplete();
//                context.getIntent().putExtra(SoundListener.KEY_FLAG, false);
            }
        });
    }

    @Override
    public void onError(String utteranceId) {
        // TODO Auto-generated method stub
        Log.e("HelloTextToSpeech", "Text To Speech ERROR -> " + utteranceId);
    }

}

