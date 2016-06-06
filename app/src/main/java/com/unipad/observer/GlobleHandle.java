package com.unipad.observer;

import android.os.Handler;
import android.os.Message;

public class GlobleHandle extends Handler {

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:

            break;
            default:
                if (msg.obj instanceof BeenObser) {
                    BeenObser beenObser = (BeenObser) msg.obj;
                    beenObser.getObser().update(beenObser.getKey(), beenObser.getO());
                }
                break;
        }
    }
}
