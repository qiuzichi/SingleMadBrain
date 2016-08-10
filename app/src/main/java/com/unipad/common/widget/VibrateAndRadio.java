package com.unipad.common.widget;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

import com.unipad.brain.App;
import com.unipad.brain.R;

/**
 * Created by gongkan on 2016/8/9.
 */
public class VibrateAndRadio {

    private Vibrator vibrator;
    private static VibrateAndRadio instance;
    private int music;
    private SoundPool sp;

    private VibrateAndRadio(){
        vibrator = (Vibrator) App.getContext().getSystemService(App.getContext().VIBRATOR_SERVICE);
        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music = sp.load(App.getContext(),R.raw.key_press_standard, 1);
    }
    public static VibrateAndRadio instance(){
        if (instance == null ) {
                if (instance == null) {
                    instance = new VibrateAndRadio();
                }
        }
        return instance;
    }
    @SuppressWarnings("static-access")
    public void vSimple( int millisecond) {
        vibrator.vibrate(new long[]{10,100}, -1);
    }
    public void vSimple() {
        vSimple(100);
    }
    public void play() {
        sp.play(music, 1, 1, 0, 0, 1);
    }
    public void vibratorAndSpeak() {
        vSimple();
        play();
    }
    /**
     * 复杂的震动
     *
     * @param pattern 震动形式
     * @param repeate 震动的次数，-1不重复，非-1为从pattern的指定下标开始重复
     */
    @SuppressWarnings("static-access")
    public void vComplicated(long[] pattern, int repeate) {
        vibrator.vibrate(pattern, repeate);
    }

    /**
     * 停止震动
     */
    public void stop() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

}
