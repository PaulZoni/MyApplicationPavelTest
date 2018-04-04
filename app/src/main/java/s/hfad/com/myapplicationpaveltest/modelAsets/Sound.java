package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import s.hfad.com.myapplicationpaveltest.IViewPurse;

public class Sound implements SoundPool.OnLoadCompleteListener {
    private SoundPool mSoundPool;
    private static final int MAX_SOUNDS = 5;
    private AssetManager mAssets;
    private static final String SOUNDS_FOLDER = "sounds";
    private int soundId=0;
    private Context mContext;

    public Sound(Context context) {
        mContext=context;
        mAssets=context.getAssets();
        mSoundPool=new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener( this);
        //mSoundPool.load(context.o)
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load()throws IOException {
        String[] s=mAssets.list(SOUNDS_FOLDER);

        //AssetFileDescriptor afd=mAssets.openFd("00923.wav");
         soundId = mSoundPool.load(mContext.getAssets().openFd("00923.wav"), 0);

    }

    public void play(){
        mSoundPool.play(soundId,1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        mSoundPool.release();
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {

    }
}






