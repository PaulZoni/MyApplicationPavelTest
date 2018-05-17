package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import java.io.IOException;


public class Sound implements SoundPool.OnLoadCompleteListener {
    private static SoundPool mSoundPool;
    private static final int MAX_SOUNDS = 5;
    private static AssetManager mAssets;
    private static final String SOUNDS_FOLDER = "sounds";
    private static int soundId=0;
    private Context mContext;


    public Sound(Context context) {
        mContext=context;
        mAssets=context.getAssets();
        mSoundPool=new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(this);
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load()throws IOException {
         soundId = mSoundPool.load(mContext.getAssets().openFd("00923.wav"), 0);
    }

    public static void play(){
        mSoundPool.play(soundId,1.0f, 1.0f, 1, 0, 1.0f);
    }

    public static void release() {
        mSoundPool.release();
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {

    }
}



















