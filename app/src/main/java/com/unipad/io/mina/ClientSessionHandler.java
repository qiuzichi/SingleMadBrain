package com.unipad.io.mina;

import com.unipad.utils.LogUtil;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientSessionHandler extends IoHandlerAdapter {

    private IDataHandler handler;
    private boolean isReconect = true;

    public ClientSessionHandler(IDataHandler handler){
        this.handler = handler;
    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionOpened(session);
//		String signXML = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10001</TRXCODE></HEAD><BODY><USERID>0887E43BA5CF47E2B4140BE3979317CB</USERID><SCHEDULEID>483A00A3A56B41E8B0E0C93BB8253165</SCHEDULEID></BODY></TRX>";
      //  String signXML = "00000203<?xml version=\"1.0\" encoding=\"GBK\"?><TRX><HEAD><TRXCODE>10001</TRXCODE></HEAD><BODY><USERID>0CC8A614B2CC48FFBE591E6142136076</USERID><SCHEDULEID>DFA3A8C92B2F43D692339B80499250E8</SCHEDULEID></BODY></TRX>";
      //  session.write(signXML);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        // TODO Auto-generated method stub
        System.out.println(message);
        String dataContent = message.toString();
        int length =Integer.valueOf(dataContent.substring(0, 8));
        String content = dataContent.substring(8,dataContent.length());
        if (length == content.length()) {
            Response response = new Response();
            //<?XML VERSION="1.0" ENCODING="GBK"?><TRX><HEAD><TRXCODE>10002</TRXCODE></HEAD><BODY><SCHEDULEID>0E523FC4E5864B29B16332FB3BD530BF</SCHEDULEID><QUESTIONID>2AB5D7C647ED4A768CAF9258A1A0EAC6</QUESTIONID><PROJECTID>00001</PROJECTID></BODY></TRX>
            response.parsePack(content.replace("<?XML VERSION=\"1.0\" ENCODING=\"GBK\"?>",""));
            if (handler != null) {
                handler.processPack(response);
            }
        }

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        while (isReconect) {
            try {
                Thread.sleep(15000);
                // 这里是异步操作 连接后立即返回
                LongTcpClient.instant().reConnect();
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

    /**
     * Created by gongkan on 2016/5/31.
     */
    public static interface IDataHandler {
        void processPack(IPack pack);
    }
}
