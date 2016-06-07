package com.unipad.io.w;

import com.unipad.io.IDataHandler;
import com.unipad.io.IPack;
import com.unipad.io.IWrite;
import com.unipad.io.bean.Response;

/**
 * Created by gongkan on 2016/6/1.
 */
public class DataHandleThread implements Runnable  {

    private IDataHandler dataHandler;

    private byte[] data;

    private int length;

    public DataHandleThread(byte[] data,int length,IDataHandler handler) {
        this.data = data;
        this.length = length;
        this.dataHandler = handler;
        System.arraycopy(data,0,this.data,0,length);
    }

    @Override
    public void run() {
        Response response = new Response();
        response.parsePack(this.data);
        dataHandler.processPack(response, null);
    }

}
