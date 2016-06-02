package com.unipad.io.bean;

import com.unipad.io.IPack;
import com.unipad.io.IWrite;

/**
 * Created by gongkan on 2016/5/31.
 */
public class Response implements IPack {

    private static int HEAD_LENGTH = 0;

    public static enum PackType {

    }

    private byte[] buffer;
    private boolean isNeedResponse = false;

    private PackType packType;

    public PackType getPackType() {
        return packType;
    }



    @Override
    public void parsePack(byte[] data) {
        buffer = new byte [data.length - HEAD_LENGTH];
        System.arraycopy(buffer,0,data,HEAD_LENGTH-1,buffer.length);

        if (isNeedResponse) {

        }

    }

    public boolean hasComplete() {
        return true;
    }

    public int readInt(int index, int length) {
        int temp = 0;
        for (int i = 0; i < length; i++) {
            temp = temp + ((buffer[index + i] & 0xff) << (8 * i));
        }
        return temp;
    }

    public String readString(int index, int length) {
        return new String(buffer, index, length);
    }

    public byte readByte(int index) {
        return buffer[index];
    }
}
