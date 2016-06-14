package com.unipad.io.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientSessionHandler extends IoHandlerAdapter{

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
		
		if(message.toString().indexOf("10007")!=-1){
			String signXML = "00000272<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10007</TRXCODE></HEAD><BODY><USERID>90F76AED95694D4EAC473132384E3957</USERID><SCHEDULEID>EEA36F389E47416895318B7DB8FCA84A</SCHEDULEID><SCORE>78</SCORE><MEMTIME>120</MEMTIME><CONTENT>abcdefjhijk</CONTENT></BODY></TRX>";
			session.write(signXML);
			super.messageReceived(session, message);			
		}
	}
	
	public static void main(String[] args) {
		String signXML2 = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10001</TRXCODE></HEAD><BODY><USERID>90F76AED95694D4EAC473132384E3957</USERID><SCHEDULEID>34874AE12C94478B8D4266590FDC358C</SCHEDULEID></BODY></TRX>";
		System.out.println(signXML2.length());
		String signXML = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10006</TRXCODE></HEAD><BODY><USERID>90F76AED95694D4EAC473132384E3957</USERID><SCHEDULEID>34874AE12C94478B8D4266590FDC358C</SCHEDULEID><SCORE>78</SCORE><MEMTIME>120</MEMTIME><CONTENT>abcdefjhijk</CONTENT></BODY></TRX>";
		System.out.println(signXML.length());
		
	}
	 

}
