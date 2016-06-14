package com.unipad.io.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class LongTcpClient {

	private static final String HOSTNAME = "192.168.0.104";

    private static final int PORT = 8091;

    private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds

	/** 15秒发送一次心跳包 */
	private static final int HEARTBEATRATE = 15;

	public static void  test() {
			
    		NioSocketConnector connector = new NioSocketConnector();
    		// Configure the service.
    		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
    		connector.setHandler(new ClientSessionHandler());
    		connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "GBK" ))));
//        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory()));
    		connector.getFilterChain().addLast("logger", new LoggingFilter());
		ClientKeepAliveMessageFactoryImp keepAlive = new ClientKeepAliveMessageFactoryImp();
		KeepAliveFilter heartBeat = new KeepAliveFilter(keepAlive,
				IdleStatus.BOTH_IDLE);
		heartBeat.setForwardEvent(true);
		heartBeat.setRequestInterval(HEARTBEATRATE);
		connector.getFilterChain().addLast("keeplive", heartBeat);
    		
    		try {
    			connector.connect(new InetSocketAddress(
    					HOSTNAME, PORT));
    		} catch (RuntimeIoException e) {
    			System.err.println("Failed to connect.");
    			e.printStackTrace();
    			
    		}
		}
    

    

}
