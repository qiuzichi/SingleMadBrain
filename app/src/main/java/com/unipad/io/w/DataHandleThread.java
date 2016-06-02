package com.unipad.io.w;

import com.unipad.io.IDataHandler;
import com.unipad.io.IPack;
import com.unipad.io.IWrite;
import com.unipad.io.bean.Response;

/**
 * Created by gongkan on 2016/6/1.
 */
public class DataHandleThread extends Thread implements IDataHandler{
    private byte[] data;

    private int length;
    public DataHandleThread(byte[]data,int length){
        this.data = new byte[length];
        System.arraycopy(this.data,0,data,0,length);
    }
    @Override
    public void run() {
        Response response = new Response();
        response.parsePack(this.data);
        processPack(response,null);
    }

    @Override
    public void processPack(IPack pack, IWrite writer) {

    }
}
