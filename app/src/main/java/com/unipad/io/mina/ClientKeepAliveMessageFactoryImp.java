package com.unipad.io.mina;

import android.util.Log;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class ClientKeepAliveMessageFactoryImp implements KeepAliveMessageFactory{

	 /** 蹇冭烦鍖呭唴瀹�*/  
    private static final String HEARTBEATREQUEST = "0x11";  
    private static final String HEARTBEATRESPONSE = "0x12";
	
	@Override
	public boolean isRequest(IoSession session, Object message) {
		// TODO Auto-generated method stub
		if(message instanceof String && message.equals(HEARTBEATREQUEST)){
			Log.e("gongkan", "isRequest:" + HEARTBEATREQUEST);
			return true;
		}
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		// TODO Auto-generated method stub
		if(message instanceof String && message.equals(HEARTBEATRESPONSE)){
			Log.e("gongkan","isResponse:"+message.toString());
			System.out.println("isResponse:"+message.toString());
			return true;
		}
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		// TODO Auto-generated method stub
		Log.e("gongkan","getRequest:"+HEARTBEATREQUEST);
		System.out.println("getRequest:"+HEARTBEATREQUEST);
		return HEARTBEATREQUEST;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		// TODO Auto-generated method stub
		Log.e("gongkan","getResponse:"+request.toString());
		System.out.println("getResponse:"+request.toString());
		return null;
	}

}