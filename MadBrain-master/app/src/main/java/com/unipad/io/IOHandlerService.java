package com.unipad.io;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.unipad.io.mina.ClientKeepAliveMessageFactoryImp;
import com.unipad.io.mina.ClientSessionHandler;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by gongkan on 2016/6/14.
 */
public class IOHandlerService extends Service {

    private static final String HOSTNAME = "192.168.0.104";

    private static final int PORT = 7003;

    private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds

    /** 15�뷢��һ������� */
    private static final int HEARTBEATRATE = 15;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NioSocketConnector connector = new NioSocketConnector();

        // Configure the service.
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
        connector.setHandler(new ClientSessionHandler());
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName("GBK"))));
//        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory()));
        connector.getFilterChain().addLast("log", new LoggingFilter());
        ClientKeepAliveMessageFactoryImp heartBeatFactory = new ClientKeepAliveMessageFactoryImp();

        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
                IdleStatus.BOTH_IDLE);

        //�����Ƿ�forward����һ��filter
        heartBeat.setForwardEvent(true);
        //��������Ƶ��
        heartBeat.setRequestInterval(HEARTBEATRATE);
        connector.getFilterChain().addLast("keeplive", new KeepAliveFilter(new ClientKeepAliveMessageFactoryImp(), IdleStatus.READER_IDLE, KeepAliveRequestTimeoutHandler.DEAF_SPEAKER,10, 5));

        try {
            connector.connect(new InetSocketAddress(
                    HOSTNAME, PORT));
        } catch (RuntimeIoException e) {
            System.err.println("Failed to connect.");
            e.printStackTrace();

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
