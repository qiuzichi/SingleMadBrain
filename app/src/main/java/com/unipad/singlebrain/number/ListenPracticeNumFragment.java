package com.unipad.singlebrain.number;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.unipad.utils.LogUtil;

import java.util.HashMap;
import java.util.Locale;

public class ListenPracticeNumFragment extends ListenToWriteNumFragment {
    private TextToSpeech tts;
    private StringBuilder answerData;
    @Override
    protected boolean isMatchMode() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化TextToSpeech对象
        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // 如果装载TTS引擎成功
                if (status == TextToSpeech.SUCCESS) {
                    // 设置使用美式英语朗读
                    tts.setLanguage(Locale.ENGLISH);
                    tts.setSpeechRate(0.9f);
                    tts.setPitch(1f);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 关闭TextToSpeech对象
        if (tts != null) {
            tts.shutdown();
        }
    }

    @Override
    public void startMemory() {
        super.startMemory();
        // 执行朗读
        HashMap<String, String> myHash = new HashMap<String, String>();
        myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "done");
        String[] splitspeech = answerData.toString().split("\\ ");
        for (int i = 0; i < splitspeech.length; i++) {
            if (i == 0) {
                tts.speak(splitspeech[i].trim(),TextToSpeech.QUEUE_FLUSH, myHash);
            }
            else {
                tts.speak(splitspeech[i].trim(), TextToSpeech.QUEUE_ADD,myHash);
            }
            LogUtil.e("qzc", "第"+i+"个size = " + splitspeech[i]);
            tts.playSilence(750,TextToSpeech.QUEUE_ADD,null);
        }
    }

    @Override
    public void initDataFinished() {
        super.initDataFinished();
        answerData = new StringBuilder("a b c ");
        for (int i = 0; i < service.lineNumbers.size(); i++) {
            answerData.append(service.lineNumbers.valueAt(i).replaceAll("\\d{1}(?!$)", "$0 ")).append(" ");
        }
        LogUtil.e("qzc", "size = " + service.lineNumbers.size() + "---tostring=" + answerData.toString());
    }
}
