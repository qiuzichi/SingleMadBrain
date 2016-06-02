package com.unipad.io.w;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.os.Handler;
import android.os.Message;

import com.unipad.io.IWrite;
import com.unipad.io.bean.Request;


/**
 * 客户端写消息线程
 *
 * @author way
 */
public class SocketOutputThread extends Thread implements IWrite {
    private boolean isStart = true;
    private static String tag = "socketOutputThread";
    private List<Request> sendMsgList;

    public SocketOutputThread() {

        sendMsgList = new CopyOnWriteArrayList<Request>();
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
        synchronized (this) {
            notify();
        }
    }

    // 使用socket发送消息
    public void sendMsg(byte[] msg) throws Exception {
        Request request = new Request( msg);
        addMsgToSendList(request);

    }

    // 使用socket发送消息
    public void addMsgToSendList(Request request) {

        synchronized (this) {
            this.sendMsgList.add(request);
            notify();
        }
    }

    @Override
    public void run() {
        while (isStart) {
            // 锁发送list
            synchronized (sendMsgList) {
                // 发送消息
                for (Request request : sendMsgList) {


                    try {
                        write(request.getBuffer());
                        sendMsgList.remove(request);
                        // 成功消息，通过hander回传

                    } catch (Exception e) {
                        e.printStackTrace();
                        CLog.e(tag, e.toString());
                        // 错误消息，通过hander回传


                        }
                    }
                }
            }

            synchronized (this) {
                try {
                    wait();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }// 发送完消息后，线程进入等待状态
            }


    }

    @Override
    public boolean write(byte[] data) {
        if (data == null) {
            CLog.e(tag, "sendMsg is null");
            return false;
        }

        try {
            TCPClient.instance().sendMsg(data);

        } catch (Exception e) {

        }

        return true;
    }

}
