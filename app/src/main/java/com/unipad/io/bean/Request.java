package com.unipad.io.bean;

import android.text.TextUtils;

import com.unipad.io.IPack;
import com.unipad.io.IWrite;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by gongkan on 2016/5/31.
 */
public class Request implements IWrite{

    private byte[] body;

    public Request (byte[] data){
        body = data;
    }

    public Request (){

    }
    @Override
    public boolean write(byte[] data) {
        return false;
    }



    public byte[] getBuffer() {
        return body;
    }

    public void setBuffer(byte[] buffer) {
        this.body = buffer;
    }
    public void writeInt(int data, int length) {
        int orginLength = body.length;
        body = Arrays.copyOf(body, orginLength + length);
        for (int i = 0; i < length; i++) {
            body[orginLength + i] = (byte) ((data >> 8 * i) & 0xFF);
        }
    }

    public void writeByte(byte data) {
        if (body == null) {
            body = new byte[] { data };
        } else {
            int orginLength = body.length;
            body = Arrays.copyOf(body, orginLength + 1);
            body[orginLength] = data;
        }
    }

    public void writeByte(byte[] data) {
        if (data != null && data.length != 0) {
            if (body == null) {
                body = data;
            } else {
                int orginLength = body.length;
                body = Arrays.copyOf(body, orginLength + data.length);
                System.arraycopy(data, 0, body, orginLength, data.length);
            }
        }
    }

    public void writeString(String value, Charset charset) {
        byte[] byteValue = value.getBytes(charset);
        int orginLength = body.length;
        body = Arrays.copyOf(body, orginLength + byteValue.length);
        System.arraycopy(byteValue, 0, body, orginLength, byteValue.length);
    }

    public void writeString(String value) {
        if (!TextUtils.isEmpty(value)) {
            byte[] byteValue = value.getBytes();
            int orginLength = body.length;
            body = Arrays.copyOf(body, orginLength + byteValue.length);
            System.arraycopy(byteValue, 0, body, orginLength, byteValue.length);
        }
    }

    public void writeString(String value, String charsetName) {
        if (!TextUtils.isEmpty(value)) {
            byte[] byteValue;
            try {
                byteValue = value.getBytes(charsetName);
                int orginLength = body.length;
                body = Arrays.copyOf(body, orginLength + byteValue.length);
                System.arraycopy(byteValue, 0, body, orginLength,
                        byteValue.length);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

}
