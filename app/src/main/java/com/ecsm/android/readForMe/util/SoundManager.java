package com.ecsm.android.readForMe.util;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.ecsm.android.readForMe.listener.SoundListener;

import java.util.ArrayList;
import java.util.Locale;


//TextToSpeech.OnInitListener - Interface definition of a callback to be invoked indicating the completion of the TextToSpeech engine initialization.
public class SoundManager implements TextToSpeech.OnInitListener {
    private Activity ctx;
    private TextToSpeech myTTS;

    private SoundUtteranceListener mSoundUtteranceListener;
    public static int MY_TTS_CHECK_CODE = 0;
    private SoundListener mSoundListener;

    public SoundManager(Activity context, @NonNull SoundListener xCom) {
        ctx = context;
        mSoundListener = xCom;
        mSoundUtteranceListener = new SoundUtteranceListener(ctx, mSoundListener);
    }

    @Override
    public void onInit(int initStatus) {
        Log.e("HelloTextToSpeech", "Init TTS");

        initializeTTS();
        if (initStatus == TextToSpeech.SUCCESS) {

            Locale currentLocale = ctx.getResources().getConfiguration().locale;
            Log.e("HelloTextToSpeech", "Voice name: " + myTTS.getDefaultVoice().getName());

            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE && currentLocale == Locale.US) {
                myTTS.setLanguage(Locale.US);
            } else if (myTTS.isLanguageAvailable(Locale.UK) == TextToSpeech.LANG_AVAILABLE && currentLocale == Locale.UK) {
                myTTS.setLanguage(Locale.UK);
            } else if (myTTS.isLanguageAvailable(Locale.ENGLISH) == TextToSpeech.LANG_AVAILABLE && currentLocale == Locale.ENGLISH) {
                myTTS.setLanguage(Locale.ENGLISH);
            } else {
                //Initializing text to voice
                Intent checkTTSIntent = new Intent();
                checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                ((Activity) ctx).startActivityForResult(checkTTSIntent, MY_TTS_CHECK_CODE);
            }
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(ctx, "Text To Speech init failed...", Toast.LENGTH_LONG).show();
        }
    }

    public void initializeTTS() {
        if (myTTS == null) {
            myTTS = new TextToSpeech(ctx, this);
            myTTS.setSpeechRate(1.0f);
            myTTS.setPitch(1.0f);
            myTTS.setOnUtteranceProgressListener(mSoundUtteranceListener);
        }
    }

    public void onDestroy() {
        if (myTTS != null) {
            myTTS.stop();
            myTTS.shutdown();
        }
    }

    /*
        QUEUE_ADD - Queue mode where the new entry is added at the end of the playback queue.
        QUEUE_FLUSH - Queue mode where all entries in the playback queue (media to be played and text to be synthesized) are dropped and replaced by the new entry.
    */
    public void speak(String text, String utteranceId) {
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    public void speakAll(ArrayList<String> list, String utteranceId) {
        for (String txt : list) {
            String temputt = utteranceId + "_" + list.indexOf(txt);
            speak(txt, temputt);
        }
    }

    public void stopSpeaking() {
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                ctx.getIntent().putExtra(SoundListener.KEY_FLAG,true);

                mSoundListener.onSoundStop();

            }
        });

        myTTS.stop();
    }


}
