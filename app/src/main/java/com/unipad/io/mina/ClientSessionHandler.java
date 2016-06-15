package com.unipad.io.mina;

import android.widget.Toast;

import com.unipad.brain.App;
import com.unipad.io.bean.Response;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;

public class ClientSessionHandler extends IoHandlerAdapter{


	private boolean isReconect = true;

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
//		String signXML = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10001</TRXCODE></HEAD><BODY><USERID>0887E43BA5CF47E2B4140BE3979317CB</USERID><SCHEDULEID>483A00A3A56B41E8B0E0C93BB8253165</SCHEDULEID></BODY></TRX>";
		String signXML = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10001</TRXCODE></HEAD><BODY><USERID>0CC8A614B2CC48FFBE591E6142136076</USERID><SCHEDULEID>DFA3A8C92B2F43D692339B80499250E8</SCHEDULEID></BODY></TRX>";
		session.write(signXML);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("sign...");
		System.out.println(message);
		Response response = new Response();
		response.parsePack(message.toString());

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		while(isReconect) {
			try {
				Thread.sleep(3000);
				// 这里是异步操作 连接后立即返回

			} catch (Exception e) {
			}
		}
	}
	//	public static void main(String[] args) {
//		String signXML2 = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10001</TRXCODE></HEAD><BODY><USERID>90F76AED95694D4EAC473132384E3957</USERID><SCHEDULEID>34874AE12C94478B8D4266590FDC358C</SCHEDULEID></BODY></TRX>";
//		System.out.println(signXML2.length());
//		String signXML = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10006</TRXCODE></HEAD><BODY><USERID>90F76AED95694D4EAC473132384E3957</USERID><SCHEDULEID>34874AE12C94478B8D4266590FDC358C</SCHEDULEID><SCORE>78</SCORE><MEMTIME>120</MEMTIME><CONTENT>abcdefjhijk</CONTENT></BODY></TRX>";
//		System.out.println(signXML.length());
//		
//	}
//
	public void setIsReconect(boolean isReconect) {
		this.isReconect = isReconect;
	}

}
