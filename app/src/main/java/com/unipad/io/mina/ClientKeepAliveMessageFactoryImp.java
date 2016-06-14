package com.unipad.io.mina;

import android.util.Log;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class ClientKeepAliveMessageFactoryImp implements KeepAliveMessageFactory{

	 /** 心跳包内容 */  
    private static final String HEARTBEATREQUEST = "0x11";  
    private static final String HEARTBEATRESPONSE = "0x12";
	
	@Override
	public boolean isRequest(IoSession session, Object message) {
		// TODO Auto-generated method stub
		Log.e("","isRequest:"+message.toString());
		if(message instanceof String && message.equals(HEARTBEATREQUEST)){

//			System.out.println("isRequest:"+HEARTBEATREQUEST);
			return true;
		}
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		Log.e(""," isResponse:"+message.toString());
		// TODO Auto-generated method stub
		if(message instanceof String && message.equals(HEARTBEATRESPONSE)){
			Log.e("","isResponse:"+ message.toString());
//			System.out.println("isResponse:"+HEARTBEATRESPONSE);
			return true;
		}
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		// TODO Auto-generated method stub
		Log.e(""," getRequest:"+HEARTBEATREQUEST);
//		System.out.println("getRequest:"+HEARTBEATREQUEST);
		return HEARTBEATREQUEST;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		Log.e("","getResponse:"+request.toString());
		// TODO Auto-generated method stub
		return request.toString();
	}

}