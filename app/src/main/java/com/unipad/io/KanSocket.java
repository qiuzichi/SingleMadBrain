package com.unipad.io;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.Socket;

/**
 * Created by gongkan on 2016/5/31.
 */
public class KanSocket extends AbsBaseDataHandler{

    public static final String HOST = "192.168.1.101";// "192.168.1.21";//
    public static final int PORT = 9800;

    WeakReference<Socket> mSocket;

    @Override
    public boolean open() {
        return false;
    }
    private void init() {
        Socket so = null;
        try {
            so = new Socket(HOST, PORT);
            mSocket = new WeakReference<Socket>(so);
            CheckThread checkThread = new CheckThread();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() {

    }
}
