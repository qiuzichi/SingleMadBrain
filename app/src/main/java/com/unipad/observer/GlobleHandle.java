package com.unipad.observer;

import android.os.Handler;
import android.os.Message;

public class GlobleHandle extends Handler{
    @Override
    public void handleMessage(Message msg) {
    	super.handleMessage(msg);
    	if (msg.obj instanceof BeenObser) {
    		BeenObser beenObser = (BeenObser) msg.obj;
    		beenObser.getObser().update(beenObser.getKey(), beenObser.getO());
    	}
    }
}
